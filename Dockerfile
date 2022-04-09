FROM adoptopenjdk/openjdk11:alpine-jre
ENTRYPOINT ["mvn","spring-boot:run"]