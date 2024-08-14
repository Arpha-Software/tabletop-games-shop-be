package org.arpha.exception.handler;

import lombok.Data;

@Data
public class ConstraintErrorBody {
    private String field;
    private String message;

    public ConstraintErrorBody(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
