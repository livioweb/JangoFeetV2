#FROM java:8
## Install maven to build project
#RUN wget --no-verbose -O /tmp/apache-maven.tar.gz http://archive.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
#RUN echo "516923b3955b6035ba6b0a5b031fbd8b /tmp/apache-maven.tar.gz" | md5sum -c
#RUN tar xzf /tmp/apache-maven.tar.gz -C /opt/
#RUN ln -s /opt/apache-maven-3.3.9 /opt/maven
##RUN ln -s /opt/maven/bin/mvn /usr/local/bin
#RUN rm -f /tmp/apache-maven.tar.gz
#ENV MAVEN_HOME /opt/maven
#ENV PATH $MAVEN_HOME/bin:$PATH

#WORKDIR /

# Add POM and source
#COPY pom.xml pom.xml
#COPY src src

# Build the app
#RUN ["mvn", "clean", "package"]

# Run the app
#RUN bash -c 'touch target/myappapi-0.0.0-SNAPSHOT.jar'
#ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom","-jar","target/myappapi-0.0.0-SNAPSHOT.jar"]

FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} myappapi-0.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","myappapi-0.0.0-SNAPSHOT.jar"]
