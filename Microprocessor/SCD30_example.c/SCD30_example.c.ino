#include <i2c_t3.h>
#include "Adafruit_TCS34725.h"

//Click here to get the library: http://librarymanager/All#SparkFun_SCD30
//#include <Wire.h>
#include "SparkFun_SCD30_Arduino_Library_Teensy.h"
//
//uint8_t SCD30_I2C_ADDR = 0x61;
//long SCD30_CONT_READ_MODE = 0x0010;

int led = 13;
int SDA_PIN = 18;
int SCL_PIN = 19;
SCD30 airSensor;

Adafruit_TCS34725 tcs = Adafruit_TCS34725();

void setup() {
  // put your setup code here, to run once:
  delay(2000);
  Wire.begin(I2C_MASTER, 0x00, I2C_PINS_18_19, I2C_PULLUP_EXT, 50000);
  delay(2000);

  // confirm we can ACK over to the I2C sensor
  Wire.beginTransmission(0x61);
  Wire.sendTransmission();
  Wire.finish();
  
  Serial.begin(9600);
  switch(Wire.status())
    {
    case I2C_WAITING:  
        Serial.println("Addr: 0x61 ACK");
        break;
    case I2C_ADDR_NAK: 
        Serial.println("Addr: 0x61 NACK"); 
        break;
    default:
        Serial.println(Wire.status());
        break;
    }
  Serial.println("SCD30 Example");
//  airSensor.begin();
//  pinMode(led, OUTPUT);
//  digitalWrite(led, HIGH);
//  airSensor.setAltitudeCompensation(241); //Set altitude of the sensor in m
//  airSensor.setMeasurementInterval(2);
  if (tcs.begin(0x29, Wire)) {
    Serial.println("Found sensor");
  } else {
    Serial.println("No TCS34725 found ... check your connections");
    while (1);
  }
}

void loop() {
  if (airSensor.dataAvailable())
  {   
    Serial.print("co2(ppm):");
    Serial.print(airSensor.getCO2());

    Serial.print(" temp(C):");
    Serial.print(airSensor.getTemperature(), 1);

    Serial.print(" humidity(%):");
    Serial.print(airSensor.getHumidity(), 1);

    Serial.println();
  }
  else
    Serial.println("No data");

  delay(1000);
  
}
