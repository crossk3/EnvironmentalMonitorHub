import json

from flask import Flask, request
from pymongo import MongoClient

server = Flask(__name__)


@server.route('/')
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
    pass


@server.route('/data/temperature', methods=['GET'])
def temperature_handler():
    """
    Handles retrieving temperature data. Can filter by times,
    so you can request temperature data from A to B, for example.
    :return:
    """
    query = request.args
    # TODO: handle filters? pass directly to Mongo?
    # TODO: Query only by timeseries?


@server.route('/time/<isotime>', methods=['GET'])
def timeseries_handler(isotime):
    """
    Handles retrieving data by time, i.e. can query for all types of data at a given moment
    :param isotime:
    :return:
    """
    pass
