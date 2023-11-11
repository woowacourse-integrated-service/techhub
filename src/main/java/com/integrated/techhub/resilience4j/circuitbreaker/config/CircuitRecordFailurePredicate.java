package com.integrated.techhub.resilience4j.circuitbreaker.config;

import com.integrated.techhub.common.exception.TechHubException;

import java.util.function.Predicate;

public class CircuitRecordFailurePredicate implements Predicate<Throwable> {

    @Override
    public boolean test(Throwable throwable) {
        if (throwable instanceof TechHubException) {
            return false;
        }
        return true;
    }
}
