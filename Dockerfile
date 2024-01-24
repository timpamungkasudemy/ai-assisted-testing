# Build command
#
# 1. gradle clean bootJar
# 2. docker build --label ai-assistant-testing --tag timpamungkas/ai-assistant-testing:1.0.0 . 
# 3. docker push timpamungkas/ai-assistant-testing:1.0.0
# or just merge all:
# .\gradlew clean bootJar && docker build --label ai-assistant-testing --tag timpamungkas/ai-assistant-testing:1.0.0 . && docker push timpamungkas/ai-assistant-testing:1.0.0

# Start with a base image containing Java runtime
FROM amazoncorretto:21

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_SOURCE_FILE=build/libs/ai-assistant-testing-1.0.0.jar
ARG JAR_DESTINATION_FILE=ai-assistant-testing.jar

# Add the application's jar to the container
ADD ${JAR_SOURCE_FILE} ${JAR_DESTINATION_FILE}

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ai-assistant-testing.jar"]