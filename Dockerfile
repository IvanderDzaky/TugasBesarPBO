FROM payara/server-full:latest
ENV JAVA_OPTS="-Xms128m -Xmx256m"
COPY HotelReservation/dist/HotelReservation.war $DEPLOY_DIR/ROOT.war
