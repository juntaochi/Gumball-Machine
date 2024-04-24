FROM eclipse-temurin:17
WORKDIR /home
COPY ./target/gumball-machine-0.0.1-SNAPSHOT.jar gumball-machine.jar
ENTRYPOINT ["java", "-jar", "gumball-machine.jar"]
