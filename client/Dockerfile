#java -jar .\target\EJB3DemoClient-1-jar-with-dependencies.jar
#java -jar target/ejb-remote-client-jar-with-dependencies.jar
#cd client
#mvn clean compile assembly:single
#docker build -t ejb_remote_client .
#kubectl exec -it  pod-client -- bash
#docker run -d ejb_remote_client
#docker exec -it <container_id> /bin/bash
#docker network inspect bridge
#------------------
FROM centos:7
MAINTAINER Devopscube
RUN yum -y update && \
    yum -y install java-1.8.0-openjdk && \
    yum -y install httpd && \
    yum clean all

ARG JAR_FILE=target/*.jar
ARG BASH_FILE=deploy/*.sh


RUN mkdir -p /app
WORKDIR /app

COPY ${JAR_FILE} /app/app.jar
COPY ${BASH_FILE} /app/script.sh
RUN chmod +x /app/script.sh
ENTRYPOINT ["/app/script.sh"]
CMD ["true", "batman", "superman"]