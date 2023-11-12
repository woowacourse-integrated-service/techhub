package com.integrated.techhub.pr.domain.type;

import com.integrated.techhub.pr.exception.SortByNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SortBy {

    TITLE,
    STEP,
    ;

    public static void validateValue(final String sortBy) {
        Arrays.stream(SortBy.values())
                .filter(sort -> sort.name().equals(sortBy))
                .findAny()
                .orElseThrow(() -> new SortByNotFoundException(sortBy));
    }
}
