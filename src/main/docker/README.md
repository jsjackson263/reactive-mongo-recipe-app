# Docker
This directory contains Docker related content

## Contents of Docker-Compose/sf-guru folder on jobuntu:
### springbootwebapp 
- contains a spring-boot app & a Dockerfile used to create an image for it.
docker build -t spring-boot-docker .
docker run -d -p 10300:10300 --name spring-boot-docker spring-boot-docker 

### archive
- assignment: look for the error in the Dockerfile 

### rabbit-mq
docker run -d --hostname rabbitmq-guru --name rabbitmq-guru -p 15672:15672 -p 5671:5671 -p 5672:5672 rabbitmq:3-management 


### Create mysql server container:
docker run -d -p 3306:3306 --name mysql-guru -v /home/jsanyang/dockerdata/mysql:/var/lib/mysql  -e  MYSQL_ALLOW_EMPTY_PASSWORD=yes mysql


To find all socket files on your system run:

sudo find / -type s


### Create mysql client container:
***How to run a MySQL client instance to connect to the MySQL server:

found the following open socker for mysql:
/var/lib/docker/overlay2/e864defcab0215166e2aedfc2ef1022e55cafefc9dd042176fef3dd66d79e852/merged/run/mysqld/mysqld.sock


docker run -it --link mysql-guru:mysql-guru --rm mysql sh -c 'exec mysql -h localhost -P 3306 -u root -p ""'  ***fail

use the identified open socket to bind-mount a volume when creating the client:

docker run -it --link mysql-guru:mysql-guru -v /var/lib/docker/overlay2/e864defcab0215166e2aedfc2ef1022e55cafefc9dd042176fef3dd66d79e852/merged/run/mysqld/mysqld.sock:/var/run/mysqld/mysqld.sock --rm mysql sh -c 'exec mysql -h localhost -P 3306 -u root -p ""'


### Running SQL commands
Once you're in, run sql commands as normal (see scripts folder):

e.g.: show all users in mysql:

SELECT User FROM mysql.user



