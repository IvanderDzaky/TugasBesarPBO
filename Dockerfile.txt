FROM payara/server-full:latest
COPY HotelReservation/dist/HotelReservation.war $DEPLOY_DIR
