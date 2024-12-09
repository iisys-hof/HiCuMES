FROM ibm-semeru-runtimes:open-11-jre-centos7

ENV CATALINA_HOME /usr/local/tomcat
#ENV JAVA_OPTS "$JAVA_OPTS --add-modules java.se --add-exports java.base/jdk.internal.ref=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/sun.nio.ch=ALL-UNNAMED --add-opens java.management/sun.management=ALL-UNNAMED --add-opens jdk.management/com.sun.management.internal=ALL-UNNAMED"


RUN mkdir -p ${CATALINA_HOME}

ADD files/ ${CATALINA_HOME}/

#RUN echo 'hicumes.hazelcast.server.name=hicumes_dev' >> $CATALINA_HOME/conf/catalina.properties

EXPOSE 9080

WORKDIR ${CATALINA_HOME}

CMD ["bin/catalina.sh", "run"]
