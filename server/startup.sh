#!/bin/bash
bash docker-entrypoint.sh mongod --auth & disown
sleep 5
python3.7 -m flask run --host=0.0.0.0
