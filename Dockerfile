#FROM gradle:8.10.2-jdk23
FROM okteto/gradle:8.2.1-jdk17

WORKDIR /app

COPY . .

CMD ["./gradlew", "assembleDebug"]
