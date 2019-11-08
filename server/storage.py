from pymongo import MongoClient
from pymongo.collection import Collection

from datastructures import Datum, TimeRange


class DatabaseClient:
    collection: Collection

    def __init__(self):
        self.client = MongoClient('localhost', 27017, username='root', password='example')

        self.db = self.client.envHubStorage
        self.collection = self.db.data

    def insert(self, *datums: Datum) -> None:
        if len(datums) == 1:
            self.collection.insert_one(datums[0].to_dict())
        else:
            self.collection.insert_many([d.to_dict() for d in datums])

    def find(self, filters=None, rang: TimeRange = None):
        if filters is None:
            filters = {}
        if rang:
            filters.update(Datum.mongo_filter_by_dates(rang))
        return [Datum.from_dict(d) for d in self.collection.find(filters)]
