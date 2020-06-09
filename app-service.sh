#!/bin/sh
SERVICE_NAME=BANGKU-MEMBER
HOME_DIR=/home/bangku/member
CONFIG_LOCATION=${HOME_DIR}/config/
CLASS_PATH=${HOME_DIR}/classes
MAIN_CLASS=GrpcApplication
LIB_PATH=${HOME_DIR}/lib/*
LOG_DIR=/home/bangku/logs
LOG_PATH=${LOG_DIR}/${SERVICE_NAME}.out
APP_PID=$(ps -elf | grep $SERVICE_NAME | grep java | awk '{print $4}')
case $1 in
    start)
        echo "Starting $SERVICE_NAME ..."
        if [ ! -n "$APP_PID" ]; then
            nohup java -Dlogging.path=${LOG_DIR} -Dapp.name=$SERVICE_NAME -Dspring.config.location=file:${CONFIG_LOCATION} -cp ${CLASS_PATH}:${LIB_PATH} ${MAIN_CLASS} > $LOG_PATH &
            sleep 10
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is already running ..."
        fi
    ;;
    stop)
	echo $APP_PID
        if [ -n "$APP_PID" ]; then
            echo "$SERVICE_NAME stoping ..."
            kill -9 $APP_PID;
            sleep 2
            echo "$SERVICE_NAME stopped ..."
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
    restart)
	echo $APP_PID
        if [ -n "$APP_PID" ]; then

            echo "$SERVICE_NAME stopping ...";
            kill -9 $APP_PID;
            sleep 2
            echo "$SERVICE_NAME stopped ...";

            echo "$SERVICE_NAME starting ..."
            nohup java -Dlogging.path=${LOG_DIR} -Dapp.name=$SERVICE_NAME -Dspring.config.location=file:${CONFIG_LOCATION} -cp ${CLASS_PATH}:${LIB_PATH} ${MAIN_CLASS} > $LOG_PATH &
            sleep 10
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME starting ..."
            nohup java -Dlogging.path=${LOG_DIR} -Dapp.name=$SERVICE_NAME -Dspring.config.location=file:${CONFIG_LOCATION} -cp ${CLASS_PATH}:${LIB_PATH} ${MAIN_CLASS} > $LOG_PATH &
            sleep 10
            echo "$SERVICE_NAME started ..."
        fi
    ;;
esac

