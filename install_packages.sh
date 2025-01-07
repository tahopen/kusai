#!/bin/bash

# Get the local Maven repository location from Maven settings (default to ~/.m2/repository if not set)
MAVEN_REPO_LOCAL=$(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)

# If MAVEN_REPO_LOCAL is empty, default to the standard ~/.m2/repository
if [ -z "$MAVEN_REPO_LOCAL" ]; then
    MAVEN_REPO_LOCAL="$HOME/.m2/repository"
fi

# Check if a specific JAR file or directory is provided
if [ -z "$1" ]; then
    JAR_DIR="./"  # Default to current directory
elif [ -f "$1" ]; then
    JAR_FILE="$1"  # Single JAR file provided
else
    JAR_DIR="$1"  # Directory provided
fi

# Function to extract metadata from MANIFEST.MF
extract_manifest_metadata() {
    local file="$1"
    manifest=$(unzip -p "$file" META-INF/MANIFEST.MF 2>/dev/null)

    groupId=$(echo "$manifest" | grep -i "^Implementation-Vendor-Id:" | head -n 1 | cut -d':' -f2 | xargs)
    artifactId=$(echo "$manifest" | grep -i "^Implementation-Title:" | head -n 1 | cut -d':' -f2 | xargs)
    version=$(echo "$manifest" | grep -i "^Implementation-Version:" | head -n 1 | cut -d':' -f2 | xargs)
}

# Function to process a single JAR file
process_file() {
    local file="$1"
    local extension="${file##*.}"

    if [ "$extension" == "jar" ]; then
        # Extract metadata from pom.properties
        pom_properties=$(unzip -p "$file" META-INF/maven/*/*/pom.properties 2>/dev/null)

        # Parse groupId, artifactId, and version
        groupId=$(echo "$pom_properties" | grep "^groupId=" | head -n 1 | cut -d'=' -f2 | xargs)
        artifactId=$(echo "$pom_properties" | grep "^artifactId=" | head -n 1 | cut -d'=' -f2 | xargs)
        version=$(echo "$pom_properties" | grep "^version=" | head -n 1 | cut -d'=' -f2 | xargs)

        # If pom.properties metadata is incomplete, fall back to MANIFEST.MF
        if [ -z "$groupId" ] || [ -z "$artifactId" ] || [ -z "$version" ]; then
            echo "Falling back to MANIFEST.MF for metadata: $file"
            extract_manifest_metadata "$file"
        fi

        # Validate metadata
        if [ -z "$groupId" ] || [ -z "$artifactId" ] || [ -z "$version" ]; then
            echo "Invalid metadata in file: $file. Skipping..."
            return
        fi

        # Check if the JAR file is already in the Maven local repository
        repo_dir="$MAVEN_REPO_LOCAL/$(echo "$groupId" | tr '.' '/')/$artifactId/$version/"
        if [ -f "$repo_dir/$artifactId-$version.jar" ]; then
            echo "Dependency already exists: $groupId:$artifactId:$version"
            return  # Skip installation if file already exists
        fi

        # Run the Maven install command in the background
        ./mvnw install:install-file \
            -Dfile="$file" \
            -DgroupId="$groupId" \
            -DartifactId="$artifactId" \
            -Dversion="$version" \
            -Dpackaging=jar \
            -DskipTests \
            -o &
    fi
}

if [ -n "$JAR_FILE" ]; then
    # Process a single file
    process_file "$JAR_FILE"
else
    # Find all JAR files in the directory and process them
    find "$JAR_DIR" -maxdepth 1 -name "*.jar" | while read -r file; do
        process_file "$file"
    done
fi

# Wait for all background processes to finish
wait
