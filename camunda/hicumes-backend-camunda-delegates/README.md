# hicumes-camunda-delegate-examples

**Requires:**

Has a dependency to https://mvnrepository.com/artifact/org.json/json/20160212

**Basis:**

Java delegate-examples for Camunda Service Tasks. https://docs.camunda.org/manual/7.10/user-guide/process-engine/delegation-code/

**Config:**

-

**Build:**

mvn package

**Deploy:**

* move target/hicumes-camunda-delegate-examples-x.x.x.jar into $YOUR_CAMUNDA_DIR/server/apache-tomcat-x.x.x/lib
* place json-20160212.jar from https://mvnrepository.com/artifact/org.json/json/20160212 into $YOUR_CAMUNDA_DIR/server/apache-tomcat-x.x.x/lib
* restart Camunda server


For a detailled description of the JMS, message event and task listener part
see https://gitlab.sysint.iisys.de/Systemintegration/HiCuMES/camunda-jms-test