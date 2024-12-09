# FROM quay.io/wildfly/wildfly
# ADD standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
# ADD mysql-connector-java-8.0.30.jar /opt/jboss/wildfly/modules/system/layers/base/com/mysql/main/
# ADD module.xml /opt/jboss/wildfly/modules/system/layers/base/com/mysql/main/
# # RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent
# # CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
# CMD ["/opt/jboss/wildfly/bin/standalone.sh"]

# FROM quay.io/wildfly/wildfly:26.1.2.Final-jdk11

FROM ibm-semeru-runtimes:open-11-jre-centos7

ENV JAVA_OPTS "$JAVA_OPTS --add-modules java.se --add-exports java.base/jdk.internal.ref=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/sun.nio.ch=ALL-UNNAMED --add-opens java.management/sun.management=ALL-UNNAMED --add-opens jdk.management/com.sun.management.internal=ALL-UNNAMED"

WORKDIR /opt/jboss

RUN groupadd -r jboss -g 1000 && useradd -u 1000 -r -g jboss -m -d /opt/jboss -s /sbin/nologin -c "JBoss user" jboss && \
  chmod 755 /opt/jboss

ENV WILDFLY_VERSION 26.1.2.Final
ENV WILDFLY_SHA1 3dda0f3795c00cedf8b14c83f8c341244e7cad44
ENV JBOSS_HOME /opt/jboss/wildfly

USER root

RUN cd $HOME \
  && curl -L -O https://github.com/wildfly/wildfly/releases/download/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz \
  && sha1sum wildfly-$WILDFLY_VERSION.tar.gz | grep $WILDFLY_SHA1 \
  && tar xf wildfly-$WILDFLY_VERSION.tar.gz \
  && mv $HOME/wildfly-$WILDFLY_VERSION $JBOSS_HOME \
  && rm wildfly-$WILDFLY_VERSION.tar.gz \
  && mkdir ${JBOSS_HOME}/bin/log \
  && chown -R jboss:0 ${JBOSS_HOME} \
  && chmod -R g+rw ${JBOSS_HOME}

ENV LAUNCH_JBOSS_IN_BACKGROUND true

USER jboss

EXPOSE 8080

# Appserver
ENV WILDFLY_USER admin
ENV WILDFLY_PASS adminPassword

# Database
ENV DB_NAME hicumes
ENV DB_USER hicumes
ENV DB_PASS hc_pwd
ENV DB_URI mysql:3306

ENV MYSQL_VERSION 8.0.30
ENV JBOSS_CLI /opt/jboss/wildfly/bin/jboss-cli.sh
ENV DEPLOYMENT_DIR /opt/jboss/wildfly/standalone/deployments/

ENV WILDFLY_LOG_LEVEL INFO

ENV WILDFLY_MAX_BACKUP_INDEX 5
ENV WILDFLY_ROTATE_SIZE 100m

#ENV JAVA_OPTS

# Setting up WildFly Admin Console
RUN echo "=> Adding WildFly administrator"
RUN $JBOSS_HOME/bin/add-user.sh -u $WILDFLY_USER -p $WILDFLY_PASS --silent

ADD --chown=jboss:root wildfly-plugins/* /opt/modules/plugins/

# Configure Wildfly server
RUN echo "=> Starting WildFly server" && \
  bash -c '$JBOSS_HOME/bin/standalone.sh &' && \
  echo "=> Waiting for the server to boot" && \
  bash -c 'until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do echo `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null`; sleep 1; done' && \
  ${JBOSS_CLI} --connect --command="/subsystem=logging/console-handler=CONSOLE:write-attribute(name=level,value=${WILDFLY_LOG_LEVEL})" && \
  #${JBOSS_CLI} --connect --command="/subsystem=logging/root-logger=ROOT:write-attribute(name=level,value=${WILDFLY_LOG_LEVEL})" && \
  echo "=> Remove ROOT Logger" && \
  ${JBOSS_CLI} --connect --command="/subsystem=logging/root-logger=ROOT:remove-handler(name="FILE")" && \
  #${JBOSS_CLI} --connect --command="/subsystem=logging/root-logger=ROOT:remove-handler(name="CONSOLE")" && \
  echo "=> Add Rotation Logger" && \
  ${JBOSS_CLI} --connect --command="/subsystem=logging/size-rotating-file-handler=ROT_LOGGER:add(file={path=>"server_rot.log", "relative-to"=>"jboss.server.log.dir"})" && \
  ${JBOSS_CLI} --connect --command="/subsystem=logging/size-rotating-file-handler=ROT_LOGGER:write-attribute(name=level, value=${WILDFLY_LOG_LEVEL}) " && \
  echo "=> Configure rotate size" && \
  ${JBOSS_CLI} --connect --command="/subsystem=logging/size-rotating-file-handler=ROT_LOGGER:write-attribute(name=rotate-size, value=${WILDFLY_ROTATE_SIZE})" && \ 
  echo "=> Configure max backup index" && \
  ${JBOSS_CLI} --connect --command="/subsystem=logging/size-rotating-file-handler=ROT_LOGGER:write-attribute(name=max-backup-index, value=${WILDFLY_MAX_BACKUP_INDEX})" && \
  echo "=> Downloading MySQL driver" && \
  curl --location --output /tmp/mysql-connector-java-${MYSQL_VERSION}.jar --url http://search.maven.org/remotecontent?filepath=mysql/mysql-connector-java/${MYSQL_VERSION}/mysql-connector-java-${MYSQL_VERSION}.jar && \
  echo "=> Adding MySQL module" && \
  $JBOSS_CLI --connect --command="module add --name=com.mysql --resources=/tmp/mysql-connector-java-${MYSQL_VERSION}.jar --dependencies=javax.api,javax.transaction.api" && \
  echo "=> Adding MySQL driver" && \
  #/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql.driver,driver-class-name=com.mysql.jdbc.Driver)
  $JBOSS_CLI --connect --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-xa-datasource-class-name=com.mysql.cj.jdbc.MysqlXADataSource)" && \
  # $JBOSS_CLI --connect --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-xa-datasource-class-name=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource)" && \
  echo "=> Creating a new datasource" && \
  $JBOSS_CLI --connect --command="data-source add \
  --name=HicumesMFTMySQLDatasource \
  --jndi-name=java:/HicumesMFTMySQLDatasource \
  --jta=true \
  --user-name=${DB_USER} \
  --password=${DB_PASS} \
  --driver-name=mysql \
  --driver-class="com.mysql.cj.jdbc.Driver" \
  --connection-url=jdbc:mysql://${DB_URI}/${DB_NAME} \
  --validate-on-match=true \
  --valid-connection-checker-class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker" \
  --exception-sorter-class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter" \
  --background-validation=false" \
  && \
  echo "=> Creating a new datasource" && \
  $JBOSS_CLI --connect --command="data-source add \
  --name=HicumesMappingMySQLDatasource \
  --jndi-name=java:/HicumesMappingMySQLDatasource \
  --jta=true \
  --user-name=${DB_USER} \
  --password=${DB_PASS} \
  --driver-name=mysql \
  --driver-class="com.mysql.cj.jdbc.Driver" \
  --connection-url=jdbc:mysql://${DB_URI}/${DB_NAME} \
  --validate-on-match=true \
  --valid-connection-checker-class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker" \
  --exception-sorter-class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter" \
  --background-validation=false" && \
  echo "=> Setting System-Property" && \
  $JBOSS_CLI --connect --command="/system-property=hicumes.hazelcast.server.name:add(value=hicumes_demo)" && \
  echo "=> Shutting down WildFly and Cleaning up" && \
  $JBOSS_CLI --connect --command=":shutdown" && \
  rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* && \
  rm -f /tmp/*.jar

ADD deployments/* /opt/jboss/wildfly/standalone/deployments/

# Expose http and admin ports
EXPOSE 8080 9990

#echo "=> Restarting WildFly"
# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interfaces
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
