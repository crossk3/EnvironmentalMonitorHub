import json
from typing import List, Dict

from pymongo import MongoClient
import tempfile

# TODO: Move away from JSON
JSON_FILE = tempfile.NamedTemporaryFile(delete=False)


class DatabaseClient:
    def __init__(self):
        pass

    def insert(self, datum: dict):
        data = json.load(JSON_FILE)
        data.append(datum)
        json.dump(data, JSON_FILE)

    def get(self, filters: Dict[str, callable]):
        data = json.load(JSON_FILE)
        returned = []
        for datum in data:
            for key, condition in filters.items():
                if condition(datum.get(key, None)):
                    returned.append(datum)

        return returned
