from collections import namedtuple
from dataclasses import dataclass, asdict
from datetime import datetime
from enum import Enum

_subclasses = {}


class DatumType(Enum):
    TEMPERATURE = 'temperature'
    CO2 = 'co2'
    HUMIDITY = 'humidity'
    LUX = 'lux'
    ACCELERATION = 'acceleration'
    TVOC = 'tvoc'


class DatumUnits(Enum):
    PartsPerMillion = 'ppm'
    PartsPerBillion = 'ppb'
    Lux = 'lux'
    microGs = 'μG'
    milliGs = 'mG'
    Gs = 'G'
    degreesC = '°C'
    percent = '%'


class TimeRange:
    def __init__(self, start: datetime, end: datetime):
        self.start = start
        self.end = end

    def to_mongo_query(self):
        q = {}
        if self.start:
            q['$gte'] = self.start.isoformat()
        if self.end:
            q['$lt'] = self.end.isoformat()
        return q


@dataclass()
class Datum:
    value: str
    time: datetime
    sensor_id: str
    type: DatumType
    units: DatumUnits

    def __init__(self, value, time: str, sensor_id: str, type: DatumType, units: DatumUnits):
        self.value = value
        self.time = datetime.fromisoformat(time)
        self.sensor_id = sensor_id
        self.type = type
        self.units = units

    def __eq__(self, other):
        return isinstance(other, Datum) and other.value == self.value

    def __init_subclass__(cls, typ=None, **kwargs):
        super().__init_subclass__(**kwargs)
        _subclasses[typ.value] = cls

    def to_dict(self):
        ret = asdict(self)
        ret['time'] = ret['time'].isoformat()
        ret['type'] = ret['type'].value
        ret['units'] = ret['units'].value
        return ret

    @staticmethod
    def mongo_filter_by_dates(rang: TimeRange):
        return {'time': rang.to_mongo_query()}

    @classmethod
    def from_dict(cls, data):
        data.pop('_id', None)
        typ = data.pop('type')
        units = data.pop('units', None)
        klass = _subclasses.get(typ, cls)
        return klass(**data)


@dataclass()
class Temperature(Datum, typ=DatumType.TEMPERATURE):
    def __init__(self, value, time, sensor_id):
        super().__init__(
            value,
            time,
            sensor_id,
            DatumType.TEMPERATURE,
            DatumUnits.degreesC)


@dataclass()
class CO2(Datum, typ=DatumType.CO2):
    def __init__(self, value, time, sensor_id):
        super().__init__(value, time, sensor_id, DatumType.CO2, DatumUnits.PartsPerMillion)


@dataclass()
class Humidity(Datum, typ=DatumType.HUMIDITY):
    def __init__(self, value, time, sensor_id):
        super().__init__(value, time, sensor_id, DatumType.HUMIDITY, DatumUnits.percent)


@dataclass()
class Lux(Datum, typ=DatumType.LUX):
    def __init__(self, value, time, sensor_id):
        super().__init__(value, time, sensor_id, DatumType.LUX, DatumUnits.Lux)


@dataclass()
class TVOC(Datum, typ=DatumType.TVOC):
    def __init__(self, value, time, sensor_id):
        super().__init__(value, time, sensor_id, DatumType.TVOC, DatumUnits.PartsPerBillion)


AccelerationData = namedtuple('AccelerationData', ['xdata', 'ydata', 'zdata'])


@dataclass()
class Acceleration(Datum, typ=DatumType.ACCELERATION):
    value: AccelerationData

    def __init__(self, xdata, ydata, zdata, time, sensor_id):
        value = AccelerationData(xdata, ydata, zdata)
        super().__init__('', time, sensor_id, DatumType.ACCELERATION, DatumUnits.microGs)
        self.value = value

    def to_dict(self):
        ret = asdict(self)
        ret['time'] = ret['time'].isoformat()
        ret['type'] = ret['type'].value
        ret['units'] = ret['units'].value
        ret['xdata'], ret['ydata'], ret['zdata'] = ret.pop('value')
        return ret
