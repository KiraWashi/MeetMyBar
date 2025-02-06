clean:
	./gradlew clean

build:
	./gradlew build

run:
	./gradlew bootRun -Penv=PROD

swagger:
	./gradlew generateSwagger

# -------------------------
# Commandes Docker
# -------------------------

up:
	docker-compose --env-file .env up

down:
	docker-compose down

restart: down up

logs:
	docker-compose logs -f

