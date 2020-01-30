#include <i2c_t3.h>

//Click here to get the library: http://librarymanager/All#SparkFun_SCD30
//#include <Wire.h>
#include "SparkFun_SCD30_Arduino_Library_Teensy.h"
#include "ClosedCube_OPT3001.h"
#include "ADXL355.h"
#include "SparkFunCCS811_Teensy.h"
#include <SoftwareSerial.h> 
#include <SparkFunESP8266WiFi.h>
#include <string.h>
#include <Arduino.h>
#include <TimeLib.h>
#include <WiFiUdp.h>
#include <time.h>
//
//uint8_t SCD30_I2C_ADDR = 0x61;
//long SCD30_CONT_READ_MODE = 0x0010;
#define CCS811_ADDR 0x5B //Default I2C Address


//////////////////////////////
// WiFi Network Definitions //
//////////////////////////////
// Replace these two character strings with the name and
// password of your WiFi network.
const char mySSID[] = "TP-LINK_365440";
const char myPSK[] = "";

// This is needed to sync the clock over the network, but because we're doing a local network without internet access,
// there's no connection. We need to think about this.
WiFiUDP Udp;
unsigned int localPort = 8888;  // local port to listen for UDP packets
static const char ntpServerName[] = "us.pool.ntp.org";
const int timeZone = -5;     // Central North American Time
time_t getNtpTime();
void sendNTPpacket(IPAddress &address);

//////////////////
// HTTP Strings //
//////////////////
const char destServer[] = "192.168.0.101";


ESP8266Client client;


// how many minutes to delay between samples
float sample_delay = (1/15);
// start off with sending a sample
int total_delay = int(sample_delay * 60 * 1000);

void scrolling_append(char* s, char c){
    strcpy(s, s+1);
    append(s, c);
}

void append(char* s, char c) {
        int len = strlen(s);
        s[len] = c;
        s[len+1] = '\0';
}

void append_word(char* s, char word[]){
  for(int i = 0; i<strlen(word); i++){
    append(s, word[i]);
  }
}

time_t getTeensy3Time()
{
  return Teensy3Clock.get();
}

int led = 13;
int SDA_PIN = 18;
int SCL_PIN = 19;
SCD30 airSensor;
ClosedCube_OPT3001 luxSensor;
ADXL355 accelerometer;
CCS811 ccs811(CCS811_ADDR);

void setup() {
  setSyncProvider(getTeensy3Time); 
  Serial.begin(9600);
  delay(2000);
  Wire.begin(I2C_MASTER, 0x00, I2C_PINS_18_19, I2C_PULLUP_EXT, 50000);
  delay(2000);
  if (esp8266.begin()) // Initialize the ESP8266 and check it's return status
    Serial.println("ESP8266 ready to go!"); // Communication and setup successful
  else
    Serial.println("Unable to communicate with the ESP8266 :(");
  // initializeESP8266() verifies communication with the WiFi
  // shield, and sets it up.
  initializeESP8266();

  // connectESP8266() connects to the defined WiFi network.
  connectESP8266();
  displayConnectInfo();
  
  pinMode(led, OUTPUT);
  digitalWrite(led, HIGH);
  
  configureLuxSensor(0x45);
  configureAccelerometer(0x1D, RANGE_8G);
  configureSCD30();
  configureCCS811();
}

void configureLuxSensor(uint8_t addr){
  luxSensor.begin(addr);
  OPT3001_Config newConfig;
  newConfig.RangeNumber = B1100;  
  newConfig.ConvertionTime = B0;
  newConfig.Latch = B1;
  newConfig.ModeOfConversionOperation = B11;
  OPT3001_ErrorCode errorConfig = luxSensor.writeConfig(newConfig);
  if (errorConfig != NO_ERROR){
    Serial.print("OPT3001: [ERROR] Code #");
    Serial.println(errorConfig);
  }
}

float readLuxSensor(){
  OPT3001 result = luxSensor.readResult();
  if (result.error == NO_ERROR){
    return result.lux;
  }
  else {
    Serial.print("OPT3001: [ERROR] Code #");
    Serial.println(result.error);
    return 0;
  }
}

void configureAccelerometer(uint8_t addr, uint8_t range){
  accelerometer.begin(addr, Wire, range);
}

void configureSCD30(){
  airSensor.begin();
  airSensor.setAltitudeCompensation(241); //Set altitude of the sensor in m
  airSensor.setMeasurementInterval(2);
}



void configureCCS811(){
  CCS811Core::status returnCode = ccs811.begin(Wire);
  if (returnCode != CCS811Core::SENSOR_SUCCESS)
    Serial.println("CCS811.begin() returned with an error.");
}

void loop() {
  while(!airSensor.dataAvailable())
  {
    delay(50);
    total_delay += 50;
  }
  
  int lux = readLuxSensor();
  // currently just taking a basic average, we also need to think about this
  ADXL355_Result accel;
  int xavg = 0;
  int yavg = 0;
  int zavg = 0;
  int num_samples = 1000;
  for(int i = 1; i <= num_samples; i++){
    accel = accelerometer.readResult();
    xavg += accel.xdata;
    yavg += accel.ydata;
    zavg += accel.zdata;
    delay(2000/num_samples);
    total_delay += (2000/num_samples);
  }
  Serial.print("xdata: ");
  Serial.println(xavg/num_samples);
  Serial.print("ydata: ");
  Serial.println(yavg/num_samples);
  Serial.print("zdata: ");
  Serial.println(zavg/num_samples);
  while (!ccs811.dataAvailable())
  {
    delay(2);
    total_delay += 2;
  }
  //If so, have the sensor read and calculate the results.
  //Get them later
  ccs811.readAlgorithmResults();
  if(total_delay >= sample_delay*60*1000)
  {
    Serial.println("Uploading Sample");
    char datum[256];
    generate_data_string(airSensor.getTemperature(), lux, airSensor.getHumidity(), ccs811.getTVOC(), airSensor.getCO2(), datum);
    strcpy(datum, strstr(datum, "{\"sensor"));
    request_post(client, destServer, 8080, "/data", datum);
    Serial.print("Sent! Waiting for ");
    Serial.print(sample_delay*59*1000);
    Serial.println(" ms!");
    delay(sample_delay*59*1000);
    total_delay = (sample_delay*59*1000);
  }
  Serial.println(sample_delay);
}


char* generate_data_string(float temperature, int lux, float humidity, uint16_t tvoc, uint16_t co2, char datum[]){
  char sensor_id[2] = "1";
  // FUCKING FIXME
  char timestamp[32] = "2019-11-29T13:21:01";
  
  String tvoc_str = "{\"type\": \"tvoc\", \"value\": ";
  tvoc_str += tvoc;
  tvoc_str += "},";
  char tvoc_d[50];
  tvoc_str.toCharArray(tvoc_d, 50);

  String lux_str = "{\"type\": \"lux\", \"value\": ";
  lux_str += lux;
  lux_str += "},";
  char lux_d[50];
  lux_str.toCharArray(lux_d, 50);

  String co2_str = "{\"type\": \"co2\", \"value\": ";
  co2_str += co2;
  co2_str += "},";
  char co2_d[50];
  co2_str.toCharArray(co2_d, 50);

  String humidity_str = "{\"type\": \"humidity\", \"value\": ";
  humidity_str += humidity;
  humidity_str += "}";
  char humidity_d[50];
  humidity_str.toCharArray(humidity_d, 50);
  
//  get_iso_timestamp(timestamp);
// FUCKING FIXME
  append_word(datum, "{\"sensor_id\": ");
  append_word(datum, sensor_id);
  append_word(datum, ", \"time\": \"");
  append_word(datum, timestamp);
  append_word(datum, "\", \"data\": [");

  append_word(datum, "{\"type\": \"temperature\", \"value\": ");
  char temp[8];
  dtostrf(temperature, 4, 1, temp) ;
  append_word(datum, temp);
  append_word(datum, "},");

  append_word(datum, tvoc_d);

  append_word(datum, co2_d);

  append_word(datum, lux_d);

  append_word(datum, humidity_d);

  append_word(datum, "]}");
  return datum;
}

char* get_iso_timestamp(char buf[]){
  time_t now = Teensy3Clock.get();
  strftime(buf, 256, "%FT%T", now);
  return buf;
}


void request_post(ESP8266Client client, String server, int port, String resource, String data){
  String http_request = "POST";
  http_request += " " + resource + " HTTP/1.0\n";
  http_request += "Host: " + server + "\n";
  http_request += "Content-Type: application/json\n";
  http_request += "Content-Length: " + (String)data.length() + (String)" \n";
  http_request += "Connection: close\n\n";
  http_request += data;
  http_request += "\n\n";
  int retVal = client.connect(server, port);
  client.print(http_request);
  char response[1024];
  char CLOSED[7] = "CLOSED";
  read_until(client, CLOSED, response);
  client.stop();
  char status_char[4];
  strcpy(status_char, strstr(response, "HTTP/1.0") + 9); //set length so this is ok
  status_char[3] = '\0';
  int status_code;
  sscanf(status_char, "%d", &status_code);
  if(status_code < 300 && status_code >= 200){
    Serial.println(status_code);
    return;
  } else {
    Serial.println(response);
    return;
  }
}

char* read_until(ESP8266Client client, char sentinel[], char response[]){
  int i = 0;
  char collected[strlen(sentinel)] = "CLOSED";
  while(true){
      if (client.available()){
        char c = client.read();
        response[i++] = c;
        scrolling_append(collected, c);
        if(strcmp(collected, sentinel) == 0){
          response[i++] = '\0';
          return response;
        }
    }
  }
}


void initializeESP8266()
{
  // esp8266.begin() verifies that the ESP8266 is operational
  // and sets it up for the rest of the sketch.
  // It returns either true or false -- indicating whether
  // communication was successul or not.
  // true
  if (esp8266.begin()) // Initialize the ESP8266 and check it's return status
    Serial.println("ESP8266 ready to go!"); // Communication and setup successful
else
    Serial.println("Unable to communicate with the ESP8266 :(");
  int test = esp8266.begin();
  if (test != true)
  {
    Serial.println(F("Error talking to ESP8266."));
  }
  Serial.println(F("ESP8266 Shield Present"));
}

void connectESP8266()
{
  // The ESP8266 can be set to one of three modes:
  //  1 - ESP8266_MODE_STA - Station only
  //  2 - ESP8266_MODE_AP - Access point only
  //  3 - ESP8266_MODE_STAAP - Station/AP combo
  // Use esp8266.getMode() to check which mode it's in:
  int retVal = esp8266.getMode();
  if (retVal != ESP8266_MODE_STA)
  { // If it's not in station mode.
    // Use esp8266.setMode([mode]) to set it to a specified
    // mode.
    retVal = esp8266.setMode(ESP8266_MODE_STA);
    if (retVal < 0)
    {
      Serial.println(F("Error setting mode."));
    }
  }
  Serial.println(F("Mode set to station"));

  // esp8266.status() indicates the ESP8266's WiFi connect
  // status.
  // A return value of 1 indicates the device is already
  // connected. 0 indicates disconnected. (Negative values
  // equate to communication errors.)
  retVal = esp8266.status();
  if (retVal <= 0)
  {
    Serial.print(F("Connecting to "));
    Serial.println(mySSID);
    // esp8266.connect([ssid], [psk]) connects the ESP8266
    // to a network.
    // On success the connect function returns a value >0
    // On fail, the function will either return:
    //  -1: TIMEOUT - The library has a set 30s timeout
    //  -3: FAIL - Couldn't connect to network.
    retVal = esp8266.connect(mySSID, myPSK);
    if (retVal < 0)
    {
      Serial.println(F("Error connecting"));
    }
  }
}

void displayConnectInfo()
{
  char connectedSSID[24];
  memset(connectedSSID, 0, 24);
  // esp8266.getAP() can be used to check which AP the
  // ESP8266 is connected to. It returns an error code.
  // The connected AP is returned by reference as a parameter.
  int retVal = esp8266.getAP(connectedSSID);
  if (retVal > 0)
  {
    Serial.print(F("Connected to: "));
    Serial.println(connectedSSID);
  }

  // esp8266.localIP returns an IPAddress variable with the
  // ESP8266's current local IP address.
  IPAddress myIP = esp8266.localIP();
  Serial.print(F("My IP: ")); Serial.println(myIP);
}
