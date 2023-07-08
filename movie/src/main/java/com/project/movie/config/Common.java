package com.project.movie.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Common {
    @Value("${common.rate.low}")
    public static double LOW_RATING;

    @Value("${common.rate.high}")
    public static double HIGH_RATING;
}
