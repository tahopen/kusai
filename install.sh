./mvnw install:install-file -Dfile=./lib/jdk.tools-1.6.jar -DgroupId=jdk.tools -DartifactId=jdk.tools -Dversion=1.6 -Dpackaging=jar
./mvnw install:install-file -Dfile=./lib/mondrian-4.7.0.0-12.jar -DgroupId=pentaho -DartifactId=mondrian -Dversion=4.7.0.0-12 -Dpackaging=jar
./mvnw install:install-file -Dfile=./lib/saiku-query-0.4-SNAPSHOT.jar -DgroupId=org.saiku -DartifactId=saiku-query -Dversion=0.4-SNAPSHOT -Dpackaging=jar
./mvnw install:install-file -Dfile=./lib/salesforce-partner-24.0.jar -DgroupId=pentaho -DartifactId=salesforce-partner -Dversion=24.0 -Dpackaging=jar
./mvnw install:install-file -Dfile=./lib/eigenbase-properties-1.1.0.10924.jar -DgroupId=eigenbase -DartifactId=eigenbase-properties -Dversion=1.1.0.10924 -Dpackaging=jar
./mvnw install:install-file -Dfile=./lib/eigenbase-resgen-1.3.0.11873.jar -DgroupId=eigenbase -DartifactId=eigenbase-resgen -Dversion=1.3.0.11873 -Dpackaging=jar
./mvnw install:install-file -Dfile=./lib/eigenbase-xom-1.3.0.11999.jar -DgroupId=eigenbase -DartifactId=eigenbase-xom -Dversion=1.3.0.11999 -Dpackaging=jar
./mvnw install:install-file -Dfile=./lib/cpf-core-7.1.0.0-12.jar -DgroupId=pentaho -DartifactId=cpf-core -Dversion=7.1.0.0-12 -Dpackaging=jar
./mvnw install:install-file -Dfile=./lib/cpf-pentaho-7.1.0.0-12.jar -DgroupId=pentaho -DartifactId=cpf-pentaho -Dversion=7.1.0.0-12 -Dpackaging=jar
./mvnw install:install-file -Dfile=./lib/pentaho-platform-repository-7.1.0.0-12.jar -DgroupId=pentaho -DartifactId=pentaho-platform-repository -Dversion=7.1.0.0-12 -Dpackaging=jar
./mvnw install:install-file -Dfile=./lib/pentaho-platform-api-5.0.0.jar -DgroupId=pentaho -DartifactId=pentaho-platform-api -Dversion=5.0.0 -Dpackaging=jar
./mvnw install:install-file -Dfile=./lib/pentaho-platform-core-5.0.0.jar -DgroupId=pentaho -DartifactId=pentaho-platform-core -Dversion=5.0.0 -Dpackaging=jar
./mvnw install:install-file -Dfile=./lib/pentaho-platform-extensions-5.0.0.jar -DgroupId=pentaho -DartifactId=pentaho-platform-extensions -Dversion=5.0.0 -Dpackaging=jar
./mvnw install -U
