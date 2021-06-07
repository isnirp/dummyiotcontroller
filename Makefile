all:
	mvn clean package
run:
	java -jar target/DummyApp-jar-with-dependencies.jar
dockerize:
	docker build . -t dummyiot