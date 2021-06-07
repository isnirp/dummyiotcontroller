FROM maven:alpine

RUN mkdir -p /app/src

WORKDIR /app/src

COPY . .

RUN mvn clean package

EXPOSE 1883

CMD java -jar target/DummyApp-jar-with-dependencies.jar