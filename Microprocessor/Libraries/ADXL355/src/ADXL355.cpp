/*

Arduino library for ADXL355
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

#include "ADXL355.h"

ADXL355::ADXL355()
{
}

void ADXL355::begin(uint8_t address, i2c_t3 &wirePort = Wire) {
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

ADXL355_Result ADXL355::readResult() {
    ADXL355_Result result;
	_i2cPort->beginTransmission(_address);
	_i2cPort->write(XDATA3);
	_i2cPort->endTransmission(true);
	uint8_t* xdata[3];
	uint8_t* ydata[3];
	uint8_t* zdata[3];
	this->readData(xdata);
	result.xdata = ((uint32_t)xdata[0] << 12) | ((uint32_t)xdata[1] << 4) | ((uint32_t)xdata[2] >> 4);
    this->readData(ydata);
    result.ydata = ((uint32_t)ydata[0] << 12) | ((uint32_t)ydata[1] << 4) | ((uint32_t)ydata[2] >> 4);
	this->readData(zdata);
    result.zdata = ((uint32_t)zdata[0] << 12) | ((uint32_t)zdata[1] << 4) | ((uint32_t)zdata[2] >> 4);
	return result;
}

bool ADXL355::_readData(uint16_t* data)
{
	_i2cPort->requestFrom(_address, (uint8_t)3);

	int counter = 0;
	while (_i2cPort->available() < 3)
	{
		counter++;
		delay(10);
		if (counter > 250)
			return false;
	}

	_i2cPort->readBytes(*data, 3);

	return true;
}