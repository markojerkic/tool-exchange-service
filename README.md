
# Upute za korištenje

## Baza podataka

Dodan je [Docker compose file](./docker-compose.yml). Prije pokretanja aplikacije potrebno je pokrenuti tu skriptu sa naredbom:
```
docker-compose up -d --force-recreate
```

Nakon toga možete porenuti SpringBoot aplikaciju preko [ToolExchangeAppliacation](src/main/java/com/example/toolexchangeservice/ToolExchangeServiceApplication.java) klase.
To možete preko nekog IDE-a kao InteliJ, ili se može preko komandne linije:
```
./mvnw clean package
./mvnw spring-boot:run
```
