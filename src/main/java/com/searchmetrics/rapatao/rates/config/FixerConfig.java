package com.searchmetrics.rapatao.rates.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties that are used to define the integration variables with the Fixer endpoint.
 *
 * @see <a href="https://fixer.io/">Fixer</a>
 */
@Configuration
@ConfigurationProperties("fixer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixerConfig {

    private String endpoint;
    private String apiKey;
    private String currencies;
    private String cron;

}
