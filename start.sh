#!/bin/bash

# Configuration for Java and Tomcat
export CATALINA_OPTS="-Xms512m -Xmx1024m -XX:MaxPermSize=128m -Dfile.encoding=UTF-8 -Dorg.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=true -Djava.awt.headless=true"

# Saiku server directory
SAIKU_SERVER_DIR="./saiku-server/target/dist/saiku-server"

# Function to set Java home and environment
set_java_env() {
    if [ -z "$JAVA_HOME" ]; then
        echo "JAVA_HOME is not set. Please set it to your Java installation directory."
        exit 1
    fi
    export PATH="$JAVA_HOME/bin:$PATH"
    echo "Using Java from: $JAVA_HOME"
}

# Function to start the Saiku server
start_saiku() {
    echo "Starting Saiku server..."
    if [ ! -f "$SAIKU_SERVER_DIR/start-saiku.sh" ]; then
        echo "Error: Saiku server start script not found."
        exit 1
    fi
    cd "$SAIKU_SERVER_DIR" || exit
    ./start-saiku.sh &
    echo "Saiku server started."
}

# Function to stop the Saiku server
stop_saiku() {
    echo "Stopping Saiku server..."
    if [ ! -f "$SAIKU_SERVER_DIR/stop-saiku.sh" ]; then
        echo "Error: Saiku server stop script not found."
        exit 1
    fi
    cd "$SAIKU_SERVER_DIR" || exit
    ./stop-saiku.sh
    echo "Saiku server stopped."
}

# Function to wait for Saiku server readiness
wait_for_server() {
    echo "Waiting for Saiku server to be ready on port 8080..."
    for i in {1..30}; do
        if exec 3<>/dev/tcp/localhost/8080 2>/dev/null; then
            exec 3<&-  # Close the connection
            exec 3>&-
            echo "Saiku server is ready."
            return 0
        fi
        sleep 2
    done
    echo "Error: Saiku server did not start within the timeout period."
    exit 1
}

# Function to open the browser
open_browser() {
    echo "Opening browser to http://localhost:8080"
    if command -v xdg-open >/dev/null; then
        xdg-open http://localhost:8080
    elif command -v open >/dev/null; then
        open http://localhost:8080
    elif command -v start >/dev/null; then
        start http://localhost:8080
    else
        echo "Please open the following URL manually: http://localhost:8080"
    fi
}

# Main execution logic
action=${1:-start}  # Default to "start" if no argument is provided

case "$action" in
    start)
        set_java_env
        start_saiku
        wait_for_server
        open_browser
        ;;
    stop)
        stop_saiku
        ;;
    restart)
        stop_saiku
        sleep 2
        set_java_env
        start_saiku
        wait_for_server
        open_browser
        ;;
    *)
        echo "Usage: $0 {start|stop|restart}"
        exit 1
        ;;
esac
