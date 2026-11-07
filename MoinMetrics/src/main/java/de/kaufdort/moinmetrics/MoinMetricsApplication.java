package de.kaufdort.moinmetrics;

import java.io.IOException;
import java.util.Locale;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MoinMetricsApplication extends SpringBootServletInitializer {

    /**
     * Konfiguriert die Anwendung beim Start in einem externen Servlet-Container.
     *
     * @param application Builder für die Spring-Boot-Anwendung
     * @return konfigurierter Application-Builder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MoinMetricsApplication.class);
    }

    public static void main(String[] args) {
        var context = SpringApplication.run(MoinMetricsApplication.class, args);
        var environment = context.getEnvironment();
        if (environment.getProperty("app.open-browser", Boolean.class, true)) {
            String port = environment.getProperty("local.server.port", "8080");
            String contextPath = environment.getProperty("server.servlet.context-path", "");
            openBrowser("http://localhost:" + port + contextPath + "/");
        }
    }

    private static void openBrowser(String url) {
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        try {
            if (os.startsWith("windows")) {
                new ProcessBuilder("rundll32", "url.dll,FileProtocolHandler", url).start();
            } else if (os.contains("mac")) {
                new ProcessBuilder("open", url).start();
            } else {
                new ProcessBuilder("xdg-open", url).start();
            }
        } catch (IOException exception) {
            LoggerFactory.getLogger(MoinMetricsApplication.class)
                    .warn("Browser konnte nicht automatisch geöffnet werden. Bitte {} öffnen.", url, exception);
        }
    }

}
