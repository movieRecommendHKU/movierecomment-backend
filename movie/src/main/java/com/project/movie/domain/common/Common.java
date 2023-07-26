package com.project.movie.domain.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Common {

    public static double HIGH_RATING;
    public static double LOW_RATING;

    @Value("${common.rate.high}")
    public void setHighRating(double highRating) {
        HIGH_RATING = highRating;
    }

    @Value("${common.rate.low}")
    public void setLowRating(double lowRating) {
        LOW_RATING = lowRating;
    }
}
