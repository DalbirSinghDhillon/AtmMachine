FROM openjdk:11
ADD target/AtmMachine.jar AtmMachine.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","AtmMachine.jar"]