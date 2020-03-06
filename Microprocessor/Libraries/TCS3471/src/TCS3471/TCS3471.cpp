/*

Arduino library for TCS3471
Written by Keaton Cross
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
#include <i2c_t3.h>

#include "TCS3471.h"

TCS3471::TCS3471()
{
}

void TCS3471::begin(uint8_t address, i2c_t3 &wirePort = Wire) {
	_address = address;
	_i2cPort = &wirePort;
	_i2cPort->begin();
	_i2cPort->beginTransmission(_address);
	_i2cPort->write(EN_REG_ADDR);
	_i2cPort->write(EN_REG_PON);
	delay(3);
	_i2cPort->write(EN_REG_AEN);
	_i2cPort->endTransmission(true);
}

TCS3471_Result TCS3471::readResult() {
    TCS3471_Result result;
	_i2cPort->beginTransmission(_address);
	_i2cPort->write(CDATA);
	_i2cPort->endTransmission(true);
	uint16_t* cdata[2];
	uint16_t* rdata[2];
	uint16_t* gdata[2];
	uint16_t* bdata[2];
	this->readData(cdata);
	result.clear = *cdata;
    this->readData(rdata);
	result.red = *rdata;
	this->readData(gdata);
	result.green = *gdata;
    this->readData(bdata);
    result.blue = *bdata;
	return result;
}

bool TCS3471::_readData(uint16_t* data)
{
	uint8_t	buf[2];

	_i2cPort->requestFrom(_address, (uint8_t)2);

	int counter = 0;
	while (_i2cPort->available() < 2)
	{
		counter++;
		delay(10);
		if (counter > 250)
			return false;
	}

	_i2cPort->readBytes(buf, 2);
	*data = (buf[0] << 8) | buf[1];

	return true;
}