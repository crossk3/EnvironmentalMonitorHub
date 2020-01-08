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

void ADXL355::begin(uint8_t address, i2c_t3 &wirePort = Wire, uint8_t range = RANGE_2G) {
	_address = address;
	_i2cPort = &wirePort;
	_i2cPort->begin();
	this->setRange(range);
	_i2cPort->beginTransmission(_address);
	_i2cPort->write(POWER_CTL);
	_i2cPort->write(MEASURE_MODE);
	_i2cPort->endTransmission(true);
}

ADXL355_Result ADXL355::readResult() {
    ADXL355_Result result;
	_i2cPort->beginTransmission(_address);
	_i2cPort->write(XDATA3);
	_i2cPort->endTransmission(true);
	this->_readData(&result.xdata);
	this->_readData(&result.ydata);
	this->_readData(&result.zdata);
	return result;
}
bool ADXL355::setRange(uint8_t range){
    _i2cPort->beginTransmission(_address);
    _i2cPort->write(RANGE);
    _i2cPort->write(range | B10000000);
    _i2cPort->endTransmission(true);
    return true;
}

bool ADXL355::_readData(uint32_t* data)
{
    uint32_t twosComp;
	uint8_t buf[3];
	_i2cPort->requestFrom(_address, (uint8_t)3);

	int counter = 0;
	while (_i2cPort->available() < 3)
	{
		counter++;
		delay(10);
		if (counter > 250)
			return false;
	}

	_i2cPort->readBytes(buf, 3);
	*data = (buf[0] << 12) | (buf[1] << 4) | (buf[2] >> 4);
    // we're getting real results, but we need to do calibrations and stuff now
    //two's complement is apparently needed too.

	return true;
}


