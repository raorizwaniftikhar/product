#!/bin/sh

echo "The application will start in ${CONTAINER_SLEEP}s..." && sleep ${CONTAINER_SLEEP}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "com.java.spring.BrandApplication"  "$@"
