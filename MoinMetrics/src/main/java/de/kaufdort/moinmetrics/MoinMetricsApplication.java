package de.kaufdort.moinmetrics;

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
        SpringApplication.run(MoinMetricsApplication.class, args);
    }

}
