FROM openjdk:11
ADD ./target/od-example-elastic-1.war /opt/od-example-elastic/od-example-elastic-1.war
WORKDIR /opt/od-example-elastic
ENV ELASTICSEARCH_HOST=localhost
ENTRYPOINT ["java","-jar", "od-example-elastic-1.war"]