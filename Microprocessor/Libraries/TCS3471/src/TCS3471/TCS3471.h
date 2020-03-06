/*

Arduino library for TCS3471 Colour Temperature Sensor

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

#ifndef TCS3471
#define TCS3471

#define CDATA 0x14;
#define CDATAH 0x15;
#define RDATA 0x16;
#define RDATAH 0x17;
#define GDATA 0x18;
#define GDATAH 0x19;
#define BDATA 0x1A;
#define BDATAH 0x1B;

#define AGAIN_1 0x0;
#define AGAIN_4 0x1;
#define AGAIN_16 0x2;
#define AGAIN_60 0x3;

#define EN_REG_PON 0x1;
#define EN_REG_AEN 0x2;

#define EN_REG_ADDR 0x00;

#define CMD_BASE 0x1 << 7;

#define CMD_AUTO_INCR (0x1 << 5); //set CMD register to AUTO INCREMENT mode
#define CMD_REPEATED_READ (0x0 << 5); // set CMD register to REPEATED mode
#define CMD_SPECIAL_FUNC (0x3 << 5);

#define ADD_MASK 0x1F;
#define AVALID_MASK 0x1;

#include <Arduino.h>

//typedef enum {
//	RESULT		= 0x00,
//	CONFIG		= 0x01,
//	LOW_LIMIT	= 0x02,
//	HIGH_LIMIT	= 0x03,
//} TCS3471_Mode;
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
//typedef union {
//	struct {
//		uint8_t FaultCount : 2;
//		uint8_t MaskExponent : 1;
//		uint8_t Polarity : 1;
//		uint8_t Latch : 1;
//		uint8_t FlagLow : 1;
//		uint8_t FlagHigh : 1;
//		uint8_t ConversionReady : 1;
//		uint8_t OverflowFlag : 1;
//		uint8_t ModeOfConversionOperation : 2;
//		uint8_t ConvertionTime : 1;
//		uint8_t RangeNumber : 4;
//	};
//	uint16_t rawData;
//} OPT3001_Config;
//
struct TCS3471_Result {
	uint16_t clear;
	uint16_t red;
	uint16_t green;
	uint16_t blue;
};

class TCS3471 {
public:
	TCS3471();

	void begin(uint8_t address, i2c_t3 &wirePort = Wire);

	TCS3471_Result readResult();

private:
	uint8_t _address;
	i2c_t3 *_i2cPort;
	bool TCS3471::_readData(uint16_t* data);
};


#endif 