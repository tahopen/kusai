# Kusai OLAP

This project is **Kusai**, a fork of the Saiku OLAP (Online Analytical Processing) application, distributed and maintained by Tahopen. It includes updated features and improvements for modern analytical needs.

## How to Run

Follow these steps to set up and run Kusai:

### 1\. Set JDK 8 Environment Variables

Ensure that the `JAVA_HOME` and `PATH` environment variables are configured for JDK 8. Verify the setup by running:

    $ java -version

The output should confirm Java version 1.8.x, similar to the example below:

    openjdk version "1.8.0_432"
    OpenJDK Runtime Environment (Temurin)(build 1.8.0_432-b06)
    OpenJDK 64-Bit Server VM (Temurin)(build 25.432-b06, mixed mode)

### 2\. Install Dependencies

Run the installation script to set up the required dependencies:

    $ ./install.sh

### 3\. Start the Server

Start the Kusai server, which will be available at [http://localhost:8080](http://localhost:8080), by executing:

    $ ./start.sh

### 4\. Default Login Credentials

After starting the server, you can log in using the following default credentials:

- **Username:** `admin`
- **Password:** `admin`

### Troubleshooting UI Changes

When making UI updates, follow these steps to ensure changes are properly reflected:

1. Clean Maven project:

```bash
mvn clean install
```

# Kusai OLAP

... (Existing content)

### 2a. Configure Maven Settings

Copy the provided [./settings.xml](settings.xml) to your Maven configuration directory:

- Linux/MacOS: `~/.m2/settings.xml`
- Windows: `C:\Users\YourUsername\.m2\settings.xml`

If you don't have a `.m2` directory or `settings.xml` file, you can use this script to set it up:

```bash:setup-maven.sh
mkdir -p ~/.m2 && cp settings.xml ~/.m2/settings.xml
```

---

For more information or troubleshooting, refer to the official Tahopen documentation or contact the support team.
