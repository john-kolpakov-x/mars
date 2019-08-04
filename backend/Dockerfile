FROM tomcat:9.0.22-jdk11-openjdk

RUN rm -rf /usr/local/tomcat/webapps/
RUN mkdir -p /usr/local/tomcat/webapps/ROOT
COPY build/for_docker/ROOT /usr/local/tomcat/webapps/ROOT
CMD ["catalina.sh", "run"]
