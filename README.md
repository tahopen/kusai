<h1>Kusai OLAP</h1>

<h2>Installing the Pentaho Plugin</h2>

<p>Before installing the plugin, ensure your Pentaho server has properly configured datasources, as Kusai relies on these to read and display the OLAP schemas.</p>

<p>Installation steps:</p>
<ol>
    <li>Configure your datasources in Pentaho server</li>
    <li>Stop Pentaho Server:
        <pre>
/opt/pentaho/pentaho-server/stop-pentaho.sh</pre>
    </li>
    <li>Copy the plugin to Pentaho system directory:
        <pre>
cp -R ./saiku-bi-platform-plugin-p7.1/target/dist/saiku /opt/pentaho/pentaho-server/pentaho-solutions/system</pre>
    </li>
    <li>Start Pentaho Server:
        <pre>
/opt/pentaho/pentaho-server/start-pentaho.sh</pre>
    </li>
</ol>

<p>Once installed, Kusai will automatically detect and use the datasources configured in your Pentaho environment.</p>

<h2>Running Kusai Standalone Server</h2>

<h3>1. Set JDK 8 Environment Variables</h3>
<p>Configure JAVA_HOME and PATH for JDK 8. Verify with:</p>
<pre>java -version</pre>
<p>Expected output:</p>
<pre>
openjdk version "1.8.0_432"
OpenJDK Runtime Environment (Temurin)(build 1.8.0_432-b06)
OpenJDK 64-Bit Server VM (Temurin)(build 25.432-b06, mixed mode)</pre>

<h3>2. Configure Maven Settings</h3>
<p>Place the settings.xml in your Maven configuration directory:</p>
<ul>
    <li>Linux/MacOS: ~/.m2/settings.xml</li>
    <li>Windows: C:\Users\YourUsername\.m2\settings.xml</li>
</ul>
<p>Quick setup script:</p>
<pre>mkdir -p ~/.m2 && cp settings.xml ~/.m2/settings.xml</pre>

<h3>3. Install Dependencies</h3>
<pre>./install</pre>

<h3>4. Launch Server</h3>
<p>Start Kusai at http://localhost:8080:</p>
<pre>./start</pre>

<h3>5. Access the Application</h3>
<p>Default credentials:</p>
<ul>
    <li><strong>Username:</strong> admin</li>
    <li><strong>Password:</strong> admin</li>
</ul>

<h3>UI Development</h3>
<p>When making UI changes, rebuild the project:</p>
<pre>./mvnw clean install</pre>

<p>For additional support or documentation, contact the Tahopen support team.</p>
