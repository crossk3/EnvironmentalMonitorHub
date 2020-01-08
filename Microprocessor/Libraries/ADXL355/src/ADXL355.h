/*

Arduino library for ADXL355 Accelerometer

---

The MIT License (MIT)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

*/

#ifndef __ADXL335_H__
#define __ADXL335_H__

#define XDATA3 0x08
#define XDATA2 0x09
#define XDATA1 0x0A
#define YDATA3 0x0B
#define YDATA2 0x0C
#define YDATA1 0x0D
#define ZDATA3 0x0E
#define ZDATA2 0x0F
#define ZDATA1 0x10
#define FIFO_DATA 0x11

#define OFFSET_X_H 0x1E
#define OFFSET_X_L 0x1F
#define OFFSET_Y_H 0x20
#define OFFSET_Y_L 0x21
#define OFFSET_Z_H 0x22
#define OFFSET_Z_L 0x23

#define ACT_EN 0x24
#define ACT_THRESH_H 0x25
#define ACT_THRESH_L 0x26
#define ACT_COUNT 0x27

#define FILTER 0x28

#define RANGE 0x2C
#define POWER_CTL 0x2D

#define RANGE_2G 0x01
#define RANGE_4G 0x02
#define RANGE_8G 0x03
#define MEASURE_MODE 0x06 // accelerometer only mode
#define READ_BYTE 0x01
#define WRITE_BYTE 0x00

//#define EN_REG_ADDR 0x00;

//#define CMD_AUTO_INCR (0x1 << 5); //set CMD register to AUTO INCREMENT mode
//#define CMD_REPEATED_READ (0x0 << 5); // set CMD register to REPEATED mode
//#define CMD_SPECIAL_FUNC (0x3 << 5);

//#define ADD_MASK 0x1F;
//#define AVALID_MASK 0x1;

#include <Arduino.h>

//typedef enum {
//	RESULT		= 0x00,
//	CONFIG		= 0x01,
//	LOW_LIMIT	= 0x02,
//	HIGH_LIMIT	= 0x03,
//} ADXL355_Mode;
//
//
//typedef enum {
//	NO_ERROR = 0,
//	TIMEOUT_ERROR = -100,
//
//	// Wire I2C translated error codes
//	WIRE_I2C_DATA_TOO_LOG = -10,
//	WIRE_I2C_RECEIVED_NACK_ON_ADDRESS = -20,
//	WIRE_I2C_RECEIVED_NACK_ON_DATA = -30,
//	WIRE_I2C_UNKNOW_ERROR = -40
//} OPT3001_ErrorCode;
//
//typedef union {
//	uint16_t rawData;
//	struct {
//		uint16_t Result : 12;
//		uint8_t Exponent : 4;
//	};
//} OPT3001_ER;
//
//
typedef union {
	struct {
		uint8_t FaultCount : 2;
	};
	uint16_t rawData;
} ADXL355_Config;
//
struct ADXL355_Result {
	uint32_t xdata;
	uint32_t ydata;
	uint32_t zdata;
};

class ADXL355 {
public:
	ADXL355();

	void begin(uint8_t address, i2c_t3 &wirePort = Wire, uint8_t range = RANGE_2G);
	bool setRange(uint8_t range);

	ADXL355_Result readResult();

private:
	uint8_t _address;
	i2c_t3 *_i2cPort;
	bool _readData(uint32_t* data);
};

//class ADXL335
//{
//private:
//    void pinsInit();
//    float scale;
//public:
//    void begin();
//    void getXYZ(int16_t *x,int16_t *y,int16_t *z);
//    void getAcceleration(float *ax,float *ay,float *az);
//};



#endif 

