FROM java-with-redis-nodejs:v1
COPY ./backend/target/keros-debt-1.0.0-SNAPSHOT.jar /home/
COPY ./frontend/ /home
WORKDIR /home
COPY server-entrypoint.sh /usr/local/bin/
ENTRYPOINT ["java","-jar","keros-debt-1.0.0-SNAPSHOT.jar"]