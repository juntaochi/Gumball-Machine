FROM eclipse-temurin:17
WORKDIR /home
COPY ./target/Gumball-Machine-0.0.1-SNAPSHOT.jar Gumball-Machine.jar
ENTRYPOINT ["java", "-jar", "Gumball-Machine.jar"]