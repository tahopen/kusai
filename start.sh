mvn install:install-file \
    -Dfile=./lib/jdk.tools-1.6.jar \
    -DgroupId=jdk.tools \
    -DartifactId=jdk.tools \
    -Dversion=1.6 \
    -Dpackaging=jar

mvn install

mvn install:install-file \
    -Dfile=./lib/saiku-query-0.4-SNAPSHOT.jar \
    -DgroupId=org.saiku \
    -DartifactId=saiku-query \
    -Dversion=0.4-SNAPSHOT \
    -Dpackaging=jar

mvn dependency:purge-local-repository

mvn install

./saiku-server/target/dist/saiku-server/set-java.sh
./saiku-server/target/dist/saiku-server/start-saiku.sh
