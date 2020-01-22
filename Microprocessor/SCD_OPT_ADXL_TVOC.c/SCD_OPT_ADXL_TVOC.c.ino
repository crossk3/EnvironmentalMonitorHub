#include <i2c_t3.h>

//Click here to get the library: http://librarymanager/All#SparkFun_SCD30
//#include <Wire.h>
#include "SparkFun_SCD30_Arduino_Library_Teensy.h"
#include "ClosedCube_OPT3001.h"
#include "ADXL355.h"
#include "SparkFunCCS811_Teensy.h"
//
//uint8_t SCD30_I2C_ADDR = 0x61;
//long SCD30_CONT_READ_MODE = 0x0010;
#define CCS811_ADDR 0x5B //Default I2C Address

int led = 13;
int SDA_PIN = 18;
int SCL_PIN = 19;
SCD30 airSensor;
ClosedCube_OPT3001 luxSensor;
ADXL355 accelerometer;
CCS811 ccs811(CCS811_ADDR);

void setup() {
  // put your setup code here, to run once:
  delay(2000);
  Wire.begin(I2C_MASTER, 0x00, I2C_PINS_18_19, I2C_PULLUP_EXT, 50000);
  delay(2000);
  
  Serial.begin(9600);
  
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
  if (airSensor.dataAvailable())
  {   
    Serial.print("co2(ppm):");
    Serial.println(airSensor.getCO2());

    Serial.print("temp(C):");
    Serial.println(airSensor.getTemperature(), 1);

    Serial.print("humidity(%):");
    Serial.println(airSensor.getHumidity(), 1);
  }else{
    Serial.println("No SCD Data");
  }
  int lux = readLuxSensor();
  Serial.print("Lux: ");
  Serial.println(lux);
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
  }
  Serial.print("xdata: ");
  Serial.println(xavg/num_samples);
  Serial.print("ydata: ");
  Serial.println(yavg/num_samples);
  Serial.print("zdata: ");
  Serial.println(zavg/num_samples);
  if (ccs811.dataAvailable())
  {
    //If so, have the sensor read and calculate the results.
    //Get them later
    ccs811.readAlgorithmResults();
    Serial.print("tVOC[");
    //Returns calculated TVOC reading
    Serial.print(ccs811.getTVOC());
    Serial.println("]");
    Serial.println();
    Serial.println();
  }
}
