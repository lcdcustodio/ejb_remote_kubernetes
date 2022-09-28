FROM centos:7
MAINTAINER Devopscube
RUN yum -y update && \
    yum -y install java-1.8.0-openjdk && \
    yum -y install httpd && \
    yum clean all

ARG JAR_FILE=client/target/*.jar
ARG BASH_FILE=client/deploy/*.sh


RUN mkdir -p /app
WORKDIR /app

COPY ${JAR_FILE} /app/app.jar
COPY ${BASH_FILE} /app/script.sh
RUN cat /app/script.sh | tr -d '\015' >> /app/script_2.sh
RUN chmod +x /app/script_2.sh
ENTRYPOINT ["/app/script_2.sh"]
CMD ["true", "batman", "superman"]