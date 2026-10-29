# MoinMetrics bauen und lokal starten

## Voraussetzungen

- JDK 21 (nicht nur eine Java-Laufzeit).
- Internetzugang für den ersten Download von Maven und Abhängigkeiten.
- Docker mit laufendem Docker-Daemon für die lokale PostgreSQL-Datenbank.

Alle Befehle im Verzeichnis mit `pom.xml`, `mvnw` und `mvnw.cmd` ausführen.
Eine separate Maven-Installation ist dank Maven Wrapper nicht erforderlich.

Mit `java -version` die Java-Version prüfen. `JAVA_HOME` muss auf das
Installationsverzeichnis des JDK zeigen, nicht auf dessen `bin`-Verzeichnis.
Falls nötig, für das aktuelle Terminal setzen (Beispielpfade anpassen):

Windows / PowerShell:

```powershell
$env:JAVA_HOME = 'C:\Pfad\zum\jdk-21'
.\mvnw.cmd -version
```

Linux / macOS (den tatsächlichen JDK-Pfad verwenden):

```bash
export JAVA_HOME="/pfad/zum/jdk-21"
chmod +x mvnw
./mvnw -version
```

## PostgreSQL mit Docker einrichten

Dieser einmalige Setup-Befehl funktioniert in PowerShell und Bash. Er erstellt
eine lokale Entwicklungsdatenbank passend zu `src/main/resources/application.properties`:

```sh
docker run --name moinmetrics-db -e POSTGRES_DB=moinmetrics -e POSTGRES_USER=moinmetrics -e POSTGRES_PASSWORD=moinmetrics_dev -p 127.0.0.1:5432:5432 -v moinmetrics-pgdata:/var/lib/postgresql/data -d postgres:17
```

Die Daten bleiben im Docker-Volume `moinmetrics-pgdata` erhalten. Die Zugangsdaten
sind für die lokale Entwicklung gedacht. PostgreSQL ist nur über den lokalen
Rechner erreichbar. Port 5432 muss frei sein.

Bereitschaft prüfen; bei einem frisch gestarteten Container gegebenenfalls wiederholen:

```sh
docker exec moinmetrics-db pg_isready -U moinmetrics -d moinmetrics
```

Für spätere Sitzungen den vorhandenen Container starten, statt `docker run`
erneut auszuführen:

```sh
docker start moinmetrics-db
```

Weitere Befehle:

```sh
docker logs moinmetrics-db
docker stop moinmetrics-db
```

Die Variablen `POSTGRES_DB`, `POSTGRES_USER` und `POSTGRES_PASSWORD` wirken nur
bei der ersten Initialisierung eines leeren Datenverzeichnisses. Ein erneuter
Start mit geändertem Passwort ändert keine vorhandenen Datenbankzugänge.
Siehe die [Dokumentation des offiziellen PostgreSQL-Images](https://hub.docker.com/_/postgres).

## WAR bauen

Die Datenbank muss für den vollständigen Build laufen: Der vorhandene
`@SpringBootTest` startet den Anwendungskontext einschließlich Datenbankanbindung.

Windows / PowerShell:

```powershell
.\mvnw.cmd clean verify
```

Linux / macOS:

```bash
./mvnw clean verify
```

Das erzeugt die WAR-Datei unter `target/MoinMetrics-0.0.1-SNAPSHOT.war`
(der Versionsanteil folgt der `pom.xml`). `verify` führt auch die Tests aus.

Für einen reinen Paket-Build ohne Testausführung und ohne laufende Datenbank:

```powershell
# Windows / PowerShell
.\mvnw.cmd clean package -DskipTests
```

```bash
# Linux / macOS
./mvnw clean package -DskipTests
```

Ein Build mit `-DskipTests` ersetzt keine erfolgreiche Testausführung.

## Anwendung starten

Bei laufender Datenbank lässt sich die erzeugte WAR mit JDK 21 direkt starten:

```sh
java -jar target/MoinMetrics-0.0.1-SNAPSHOT.war
```

Danach im Browser `http://localhost:8080/` öffnen. Mit `Ctrl+C` beenden.
Alternativ während der Entwicklung den Maven Wrapper mit `spring-boot:run` starten.

Für die Auslieferung an einen externen Server die WAR in dessen `webapps`-Verzeichnis
kopieren. Spring Boot 4 benötigt einen Servlet-6.1-kompatiblen Container, beispielsweise
Tomcat 11, und eine passende Java-Laufzeit (für dieses Projekt Java 21).
Bei unverändertem WAR-Namen lautet der Kontextpfad üblicherweise
`/MoinMetrics-0.0.1-SNAPSHOT/`.
Die Datenbank muss vom Server aus erreichbar sein: `localhost` bezeichnet dort
den Server selbst. Abweichende Verbindungsdaten können über
`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` und
`SPRING_DATASOURCE_PASSWORD` gesetzt werden.
Siehe [Spring-Boot-Systemanforderungen](https://docs.spring.io/spring-boot/system-requirements.html)
und [WAR-Auslieferung](https://docs.spring.io/spring-boot/how-to/deployment/traditional-deployment.html).

## Zertifikatsfehler beim Download

Bei `PKIX path building failed` oder `unable to find valid certification path`
kann das verwendete JDK die HTTPS-Zertifikatskette nicht prüfen. Das kann etwa
an einer unternehmensinternen Zertifizierungsstelle oder einem HTTPS-Proxy liegen.

Unter Windows kann Maven den Windows-Zertifikatsspeicher verwenden, wenn Windows
der Verbindung bereits vertraut. In PowerShell vor dem Build setzen:

```powershell
$env:MAVEN_OPTS = "$env:MAVEN_OPTS -Djavax.net.ssl.trustStoreType=Windows-ROOT -Djavax.net.ssl.trustStore=NONE"
.\mvnw.cmd clean verify
```

Das gilt für das aktuelle Terminal; die Zertifikatsprüfung bleibt aktiv.
Diese Einstellung ist Windows-spezifisch und gehört nicht in die gemeinsame
`pom.xml`. In IntelliJ müssen dieselben JVM-Optionen bei Bedarf separat unter
den Maven-Runner-Einstellungen gesetzt werden; das Maven-JDK sollte ebenfalls
auf JDK 21 eingestellt sein.

Auf anderen Betriebssystemen beziehungsweise falls auch Windows der Verbindung
nicht vertraut: JDK und Systemzertifikate aktualisieren oder das verifizierte
CA-Zertifikat der eigenen Organisation durch die IT für das verwendete JDK
bereitstellen lassen. Java verwendet standardmäßig einen eigenen Zertifikatsspeicher.
Die HTTPS-Prüfung nicht deaktivieren und keine ungeprüften Zertifikate importieren.

Weitere Informationen: [Windows-Zertifikatsspeicher mit Java verwenden](https://support.azul.com/hc/en-us/articles/16981081133588-Using-https-TLS-SSL-certificates-provided-by-the-Operating-System)
und [Java-Zertifikatsspeicher verwalten](https://docs.oracle.com/en/java/javase/21/docs/specs/man/keytool.html).
