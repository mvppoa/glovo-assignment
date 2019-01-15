package com.glovoapp.backender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

/**
 * This class activates the new configuration service which supports converting String to Collection types.
 */
@Configuration
public class ConversionConfig {

    @Bean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }
}
