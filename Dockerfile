FROM payara/server-web:latest
ENV JAVA_OPTS="-Xms64m -Xmx128m"
COPY HotelReservation/dist/HotelReservation.war $DEPLOY_DIR/ROOT.war
