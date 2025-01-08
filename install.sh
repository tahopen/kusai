#!/bin/bash

# Function to run a command and handle failures
run_command() {
    echo "Running: $1"
    eval "$1"
    if [ $? -ne 0 ]; then
        echo "Command failed: $1"
    fi
}

# Commands to execute
commands=(
    "./saiku-server/target/dist/saiku-server/stop-saiku.sh"
    "./mvnw clean"
    "rm -R ./saiku-server/target"
    "./install_packages.sh"
    "./mvnw dependency:purge-local-repository -DmanualInclude='org.pentaho.reporting.library:libformula,org.pentaho.reporting.library:libbase'"
    "./mvnw install:install-file -Dfile=./lib/salesforce-partner-24.0.jar -DgroupId=pentaho -DartifactId=salesforce-partner -Dversion=24.0 -Dpackaging=jar"
    "./mvnw install:install-file -Dfile=./lib/mondrian-4.7.0.0-12.jar -DgroupId=pentaho -DartifactId=mondrian -Dversion=4.7.0.0-12 -Dpackaging=jar"
    "./mvnw install -U"
)

# Loop through the commands and execute them
for cmd in "${commands[@]}"; do
    run_command "$cmd"
done

echo "All commands executed. Check logs for any failures."
