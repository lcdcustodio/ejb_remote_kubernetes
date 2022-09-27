# mvn clean install
# docker build -t ejb_demo_server .
# docker run -p 8080:8080 -p 9990:9990 ejb_demo_server
# docker run -p 9990:9990 -p 8080:8080 -d ejb_demo_server
FROM quay.io/wildfly/wildfly
ADD target/*.jar /opt/jboss/wildfly/standalone/deployments/
RUN /opt/jboss/wildfly/bin/add-user.sh admin123 admin123 --silent
RUN /opt/jboss/wildfly/bin/add-user.sh -a system -p system --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]