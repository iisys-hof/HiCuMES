FROM ibm-semeru-runtimes:open-11-jre-centos7
RUN mkdir /opt/testdata
RUN mkdir /opt/testdata/Deployment
COPY testData.jar /opt/app/testData.jar
COPY Deployment/ /opt/testdata/Deployment/
COPY deploy.sh /opt/app/deploy.sh
CMD ["sh","/opt/app/deploy.sh"]