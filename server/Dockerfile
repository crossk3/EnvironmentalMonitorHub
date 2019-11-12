FROM mongo
RUN ["apt-get", "update"]
RUN ["apt-get", "install", "-y", "python3.7", "python3-pip"]
RUN ["python3.7", "-m", "pip", "install", "pymongo[srv]", "Flask"]
ENV FLASK_APP="/server.py"
ENV LC_ALL=C.UTF-8
ENV LANG=C.UTF-8
ENV MONGO_INITDB_ROOT_USERNAME=root
ENV MONGO_INITDB_ROOT_PASSWORD=example
EXPOSE 5000
ADD generate_data.py server.py storage.py datastructures.py /
COPY startup.sh /usr/local/bin/
RUN ln -s /usr/local/bin/docker-entrypoint.sh /
ENTRYPOINT ["startup.sh"]