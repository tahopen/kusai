./saiku-server/target/dist/saiku-server/stop-saiku.sh
./mvnw clean
./mvnw install:install-file -Dfile=./lib/jdk.tools-1.6.jar -DgroupId=jdk.tools -DartifactId=jdk.tools -Dversion=1.6 -Dpackaging=jar
./mvnw install:install-file -Dfile=./lib/mondrian-4.7.0.0-12.jar -DgroupId=pentaho -DartifactId=mondrian -Dversion=4.7.0.0-12 -Dpackaging=jar
./mvnw install:install-file -Dfile=./lib/saiku-query-0.4-SNAPSHOT.jar -DgroupId=org.saiku -DartifactId=saiku-query -Dversion=0.4-SNAPSHOT -Dpackaging=jar
./mvnw install
