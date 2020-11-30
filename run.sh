elasticsearch_data_path='./docker/volumes/elasticsearch-data'
[ ! -d "$elasticsearch_data_path" ] && mkdir $elasticsearch_data_path;
chown -R 1000:1000 $elasticsearch_data_path;
cd od-example-elastic/;
./mvnw clean install package -DskipTests;
docker build -t od-example-elastic .;
cd ..;
docker-compose -f ./docker/docker-compose.yml up;
