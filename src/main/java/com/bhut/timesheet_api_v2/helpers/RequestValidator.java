package com.bhut.timesheet_api_v2.helpers;

import com.bhut.timesheet_api_v2.exceptions.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RequestValidator {

    private final Validator validator;

    public RequestValidator(final Validator validator) {
        this.validator = validator;
    }

    public <T> void validate(final T request) {
        this.validate("Object field", request);
    }

    public <T> void validate(final String fieldName, final T request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException(String.format("%s is required.", fieldName));
        } else {
            Set<ConstraintViolation<T>> violations = this.validator.validate(request, new Class[0]);
            if (Objects.nonNull(violations) && !violations.isEmpty()) {
                throw new BadRequestException((String) violations.stream().map(ConstraintViolation::getMessage).sorted().collect(Collectors.joining(", ")));
            }
        }
    }
}
