import random
import argparse
import storage
from datetime import datetime, timedelta
from datastructures import Datum, DatumType

parser = argparse.ArgumentParser()
parser.add_argument('num_points', type=int)
parser.add_argument('average_value', type=float)
parser.add_argument('num_sensors', type=int)
parser.add_argument('sample_delay', type=int, help='Time in seconds between samples')
parser.add_argument('sensor_type', type=str, help='Type of sensor')
args = parser.parse_args()

assert args.sensor_type in ['temperature']

# 5 sensors for test data
test_sensors = [str(i) for i in range(args.num_sensors)]

# sample period in seconds
rate = args.sample_delay  # 1 minute

client = storage.DatabaseClient()

now = datetime.now()

mu, sigma = args.average_value, args.average_value/10.

time_delta = lambda x: timedelta(seconds=x*rate)

typ = DatumType._value2member_map_[args.sensor_type]

client.insert(*[
    Datum(random.normalvariate(21, 2), (now+time_delta(i)).isoformat(), random.choice(test_sensors), typ)
    for i in range(args.num_points)])
