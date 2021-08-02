.PHONY: run test up build test help default

default: build

help:
	@echo 'Management commands for se-file-server:'
	@echo
	@echo 'Usage:'
	@echo '    make build           Compile the project.'
	@echo '    make test            Run the project.'
	@echo '    make run             Test the project.'
	@echo '    make up              Start the service.'
	@echo '    make clean      	    Clean project.'
	@echo

build:
	docker-compose build --force-rm --parallel

up:
	docker-compose up app
run:
	docker-compose run --rm --service-ports app

test:
	docker-compose run --rm --service-ports test

down:
	docker-compose down --remove-orphans

clean: down
	docker-compose rm

	rm -rf github.com
