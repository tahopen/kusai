./saiku-server/target/dist/saiku-server/set-java.sh
./saiku-server/target/dist/saiku-server/stop-saiku.sh
./saiku-server/target/dist/saiku-server/start-saiku.sh
export CATALINA_OPTS="-Xms512m -Xmx1024m -XX:MaxPermSize=128m -Dfile.encoding=UTF-8 -Dorg.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=true -Djava.awt.headless=true"
