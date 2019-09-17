from dataclasses import dataclass, asdict
from datetime import datetime
from enum import Enum

_subclasses = {}


class DatumType(Enum):
    TEMPERATURE = 'temperature'


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

    def __init__(self, value, time: str, sensor_id: str, type: DatumType):
        self.value = value
        self.time = datetime.fromisoformat(time)
        self.sensor_id = sensor_id
        self.type = type

    def __eq__(self, other):
        return isinstance(other, Datum) and other.value == self.value

    def __init_subclass__(cls, typ=None, **kwargs):
        super().__init_subclass__(**kwargs)
        _subclasses[typ.value] = cls

    def to_dict(self):
        ret = asdict(self)
        ret['time'] = ret['time'].isoformat()
        ret['type'] = ret['type'].value
        return ret

    @staticmethod
    def mongo_filter_by_dates(rang: TimeRange):
        return {'time': rang.to_mongo_query()}

    @classmethod
    def from_dict(cls, data):
        data.pop('_id', None)
        typ = data.pop('type')
        klass = _subclasses.get(typ, cls)
        return klass(**data)


@dataclass()
class Temperature(Datum, typ=DatumType.TEMPERATURE):
    def __init__(self, value, time, sensor_id):
        super().__init__(value, time, sensor_id, DatumType.TEMPERATURE)
