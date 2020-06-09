FROM maven:3.3-jdk-8 AS build
COPY src /usr/src/app/
COPY pom.xml /usr/src/app/
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests
FROM openjdk:8-jre-alpine
WORKDIR /usr/app
COPY --from=build /usr/src/app/target/app.jar /usr/app/app.jar
COPY --from=build /usr/src/app/main/resources/application.yml /usr/app/application.yml
RUN apk add tzdata
RUN ln -snf /usr/share/zoneinfo/Asia/Jakarta /etc/localtime
ARG DS_URL="jdbc:mysql://mysql-db-standalone:3306/cariparkir_location?useSSL=false"
ENV DS_URL=${DS_URL}
ARG DS_USER="root"
ENV DS_USER=${DS_USER}
ARG DS_PWD="P@ssw0rd"
ENV DS_PWD=${DS_PWD}

ARG LOG_PATH="/logs"
ENV LOG_PATH=${LOG_PATH}
ARG LOG_FILE=${LOG_PATH}."/app.log"
ENV LOG_FILE=${LOG_FILE}
ARG GRPC_PORT=6569
ENV GRPC_PORT=${GRPC_PORT}

ARG PAYMENT_HOST=127.0.0.1
ENV PAYMENT_HOST=${PAYMENT_HOST}
ARG PAYMENT_PORT=6578
ENV PAYMENT_PORT=${PAYMENT_PORT}


CMD ["/usr/bin/java","-Dgrpc.port=${GRPC_PORT}","-Dspring.datasource.url=${DS_URL}","-Dgrpc.payment.server.host=${PAYMENT_HOST}","-Dgrpc.payment.server.port=${PAYMENT_PORT}","-Dspring.datasource.username=${DS_USER}","-Dspring.datasource.password=${DS_PWD}","-Dlogging.path=${LOG_PATH}","-Dlogging.file=${LOG_FILE}","-jar","/usr/app/app.jar"]
EXPOSE 6569
