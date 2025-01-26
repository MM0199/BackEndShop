package com.tutorial.dreamshops.response;

import lombok.AllArgsConstructor;
import lombok.Data;


public class ApiResponse {
    private final String message;
    private final Object data;

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
