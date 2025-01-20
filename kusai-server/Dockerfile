FROM openjdk:8
MAINTAINER Meteorite BI <info@meteorite.bi>


ADD target/kusai-server-foodmart-* /


EXPOSE 8080


RUN unzip /kusai-server-foodmart-* && rm /kusai-server-foodmart-*.zip
CMD /kusai-server/start-saiku.sh && tail -f /kusai-server/tomcat/logs/catalina.out

