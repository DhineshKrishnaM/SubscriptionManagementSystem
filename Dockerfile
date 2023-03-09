FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} test_sms.jar
ENTRYPOINT ["java","-jar","/test_sms.jar"]
