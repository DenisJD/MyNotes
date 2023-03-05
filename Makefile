setup:
	gradle wrapper --gradle-version 7.4

clean:
	./gradlew clean

build:
	./gradlew clean build

start:
	./gradlew bootRun --args='--spring_profiles_active=dev'

start-prod:
	./gradlew bootRun --args='--spring_profiles_active=prod'

start-dist:
	./build/install/app/bin/app

install:
	./gradlew installDist

lint:
	./gradlew checkstyleMain checkstyleTest

test:
	./gradlew test

generate-migrations:
	gradle diffChangeLog

report:
	./gradlew jacocoTestReport


.PHONY: build