FROM payara/micro:latest
COPY HotelReservation/dist/HotelReservation.war /opt/payara/deployments/ROOT.war
