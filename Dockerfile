FROM adoptopenjdk/openjdk11
WORKDIR /app
COPY . .
EXPOSE 8080
RUN ./mvnw install
CMD ["./mvnw", "spring-boot:run"]