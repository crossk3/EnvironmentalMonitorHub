import json
from datetime import datetime

from flask import Flask, request, jsonify

from datastructures import Datum, TimeRange
from storage import DatabaseClient

storage_client = DatabaseClient()

server = Flask(__name__)


@server.route('/', methods=['GET'])
def landing_page():
    with open('html/landing_page.html', 'r') as f:
        html = f.read()
    return html


@server.route('/sensors', methods=['POST'])
def register_sensor():
    """
    Registers a sensor.
    Unused
    :return:
    """
    pass


@server.route('/data/<data_type>', methods=['POST'])
def upload_data_handler(data_type):
    """
    Handles the upload of data of type data_type. Not sure how I'm going to handle this yet.
    :param data_type:
    :return:
    """
    print(request.get_data())
    body = request.json
    data = []
    for datum in body:
        datum['type'] = data_type
        data.append(Datum.from_dict(datum))
    storage_client.insert(*data)
    return ''


@server.route('/data/<data_type>', methods=['GET'])
def data_handler(data_type):
    """
    Handles retrieving temperature data. Can filter by times,
    so you can request temperature data from A to B, for example.
    :return:
    """
    query = dict(request.args)
    query['type'] = data_type
    time_start = query.pop('start', None)
    if time_start:
        time_start = datetime.fromisoformat(time_start)
    time_end = query.pop('end', None)
    if time_end:
        time_end = datetime.fromisoformat(time_end)
    r = TimeRange(time_start, time_end) if time_start or time_end else None
    return jsonify([d.to_dict() for d in storage_client.find(query, r)])


@server.route('/data', methods=['POST'])
def bulk_data_handler():
    body = request.json
    # time = body['time']
    time = datetime.now().isoformat()
    sensor_id = body['sensor_id']
    data = [Datum.from_dict({**datum, 'time': time, 'sensor_id': sensor_id}) for datum in body['data']]
    storage_client.insert(*data)
    return ''


@server.route('/data', methods=['GET'])
def timeseries_handler():
    """
    Handles retrieving data by time, i.e. can query for all types of data at a given moment
    :return:
    """
    return jsonify([d.to_dict() for d in storage_client.find()])
