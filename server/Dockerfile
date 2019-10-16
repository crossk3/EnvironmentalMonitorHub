FROM mongo
ADD . /
ENV FLASK_APP="server.py"
RUN ["apt-get", "update"]
RUN ["apt-get", "install", "-y", "python3.7"]
