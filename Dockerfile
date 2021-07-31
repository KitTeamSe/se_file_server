# SE API SERVER DOCKERFILE

# Start with a base image containing Java runtime
FROM openjdk:11 as build

# Add Author info
LABEL maintainer="se@kumoh.ac.kr"

# Add a volume to
VOLUME /var/se-file-server

# Make port 8075 available to the world outside this container
EXPOSE 8075

# The application's jar file
ARG JAR_FILE=build/libs/fileserver-0.0.1-SNAPSHOT.jar
ARG CONFIG=src/main/resources/application.yml

# Add the application's jar to the container
ADD ${JAR_FILE} run-se-file-server.jar
ADD ${CONFIG} application.yml

COPY . .

# Add Timezone
ENV TZ=Asia/Seoul

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/run-se-file-server.jar"]
