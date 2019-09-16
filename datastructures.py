from dataclasses import dataclass, asdict
from datetime import datetime


@dataclass()
class Datum:
    value: float
    time: datetime
    sensor_id: int

    def __init__(self, value, time: str):
        self.value = value
        self.time = datetime.fromisoformat(time)

    def __eq__(self, other):
        return isinstance(other, Datum) and other.value == self.value

    def to_dict(self):
        return asdict(self)


@dataclass()
class Temperature(Datum):
    pass


