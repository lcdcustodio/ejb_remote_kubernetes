FROM quay.io/wildfly/wildfly
ADD server-side/target/*.jar /opt/jboss/wildfly/standalone/deployments/
RUN /opt/jboss/wildfly/bin/add-user.sh admin123 admin123 --silent
RUN /opt/jboss/wildfly/bin/add-user.sh -a system -p system --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]