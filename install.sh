./saiku-server/target/dist/saiku-server/stop-saiku.sh
./mvnw clean
rm -R ./saiku-server/target
./install_packages.sh ./lib
./mvnw dependency:purge-local-repository -DmanualInclude="org.pentaho.reporting.library:libformula,org.pentaho.reporting.library:libbase"
./mvnw install -U
