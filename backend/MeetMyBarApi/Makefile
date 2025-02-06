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
	docker-compose up -d

down:
	docker-compose down

restart: down up

logs:
	docker-compose logs -f

clean:
	docker-compose down -v && docker system prune -f && docker volume prune -f

db-connect:
	docker exec -it mariadb_container mariadb -u root -p
