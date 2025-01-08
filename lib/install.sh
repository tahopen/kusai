#!/bin/bash

# Directory containing JAR files (current directory)
JAR_DIR="./"

# Maximum number of parallel jobs
MAX_JOBS=5

# Semaphore function to control parallelism
function semaphore() {
    while [ "$(jobs -r | wc -l)" -ge "$MAX_JOBS" ]; do
        sleep 0.5
    done
}

# Function to process a single JAR file
process_jar() {
    local jar="$1"
    echo "Processing $jar..."

    # Extract metadata from the JAR file
    pom_properties=$(unzip -p "$jar" META-INF/maven/*/*/pom.properties 2>/dev/null)

    # Parse groupId, artifactId, and version
    groupId=$(echo "$pom_properties" | grep "^groupId=" | head -n 1 | cut -d'=' -f2 | xargs)
    artifactId=$(echo "$pom_properties" | grep "^artifactId=" | head -n 1 | cut -d'=' -f2 | xargs)
    version=$(echo "$pom_properties" | grep "^version=" | head -n 1 | cut -d'=' -f2 | xargs)

    # Validate metadata
    if [ -z "$groupId" ] || [ -z "$artifactId" ] || [ -z "$version" ]; then
        echo "Warning: Invalid metadata for $jar. Skipping..."
        return
    fi

    echo "Installing $artifactId (groupId=$groupId, version=$version)..."

    # Install the JAR into the local Maven repository
    mvn install:install-file \
        -Dfile="$jar" \
        -DgroupId="$groupId" \
        -DartifactId="$artifactId" \
        -Dversion="$version" \
        -Dpackaging=jar

    if [ $? -eq 0 ]; then
        echo "Successfully installed $artifactId"
    else
        echo "Failed to install $artifactId. Check your Maven setup."
    fi
}

# Export the function for parallel execution
export -f process_jar

# Find all JAR files and process them in parallel with controlled concurrency
find "$JAR_DIR" -maxdepth 1 -name "*.jar" | while read -r jar; do
    semaphore   # Wait if max parallel jobs are running
    process_jar "$jar" &   # Run the job in the background
done

# Wait for all parallel jobs to finish
wait

echo "All JAR files processed."
