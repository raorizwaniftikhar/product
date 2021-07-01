# Products API

## Development
Install Mongodb Database in your Machin or Start moongodb container using this command
    docker pull mongo
For example, to start a mongodb database in a docker container, run:

    docker-compose -f src/main/docker/mongodb.yml up -d
    
    then check container status:
     
     docker ps
 and make sure its running on port  0.0.0.0:27017->27017/tcp  


To start your application , run:

    ./mvnw

## Using Docker to simplify development 

You can use Docker to improve your development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:
    
    docker pull adoptopenjdk:11-jre-hotspot
    
    ./mvnw -Pprod verify jib:dockerBuild

Then run:

    docker-compose -f src/main/docker/app.yml up -d# Product API fron Elistaic PATH
