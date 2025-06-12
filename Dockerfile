FROM payara/server-web:latest
ENV JAVA_OPTS="-Xms128m -Xmx384m"
COPY HotelReservation/dist/HotelReservation.war $DEPLOY_DIR/ROOT.war
