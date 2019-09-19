# Indoor Environmental Monitoring System #
## Overview ##
Our project is an Indoor environment monitor. The project's goal is to monitor, record,
and display time series data for a given indoor environment. The sensors used in 
this project will include a thermometer, humidity sensor, pressure sensor, 
light detector, carbon dioxide, accelerometer, microphone and an airborne particle 
detector. This data will be collected, recorded and displayed for the client on a central
hub and a basic Android application as well as a REST interface for external integration.
## Implementation Details ##
We'll be using multiple microcontrollers to handle data collection and pre-processing.
Our main microcontroller will handle the data collection and communication with the hub.
Any secondary microcontrollers will be used to preprocess data in the event of a sampling rate
far higher than the communication rate, such as acoustic data and the accelerometer. This data
will be sent over WiFi/HTTP over to the server which will store the data in MongoDB. MongoDB has
built in support for time series data, as well as a non-relational structure and low read/write overhead
which makes it ideal for IoT style applications such as this. This data is then exposed over a REST
API, for easy querying from our Android application or any other service the user wants to integrate
our system with.
## Resources ##
Anticipated cost is around $400 CAD. The bulk of this will be in the microcontroller, we expect to 
spend around $100 CAD on the microcontrollers, with most of the rest being sensors and other hardware.
The sensors, depending on their sensitivities and sampling rates, could be as expensive as $50 CAD,
however the average will be far less than that. The balance will be tied up in packaging and 
other smaller items.