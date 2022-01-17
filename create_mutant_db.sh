export MYSQL_ROOT_PASSWORD=magneto
export MYSQL_DATABASE=mutant
export MYSQL_USER=mutant
export MYSQL_PASSWORD=mutantpass
docker run -p 3326:3306 --env MYSQL_ROOT_PASSWORD --env MYSQL_DATABASE --env MYSQL_USER --env MYSQL_PASSWORD -d mysql:latest