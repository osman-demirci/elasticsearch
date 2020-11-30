```shell
[ ! -d ./elasticsearch-data ] && mkdir ./elasticsearch-data
chown -R 1000:1000 ./elasticsearch-data
```

#Run Application
```shell
./run.sh
```

#Endpoint Testig
HAL Browser
http://localhost:8080/explorer/index.html#uri=/flights/studyCase

Curl
```shell
curl -X GET http://localhost:8080/flights/studyCase
```

