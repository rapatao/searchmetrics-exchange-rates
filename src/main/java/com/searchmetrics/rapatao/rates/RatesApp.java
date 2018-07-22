package com.searchmetrics.rapatao.rates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Optional;

@SpringBootApplication
@EnableScheduling
public class RatesApp {

    public static void main(String[] args) {
        SpringApplication.run(RatesApp.class, args);
    }

    /**
     * Retrieve the name and the version of the running application.
     *
     * @return the application name and version
     */
    public static String getRuntimeAppName() {
        final Optional<String> version = Optional.ofNullable(RatesApp.class.getPackage().getImplementationVersion());

        return "Exchange Rates APP / " + version.orElse("development");
    }

}
