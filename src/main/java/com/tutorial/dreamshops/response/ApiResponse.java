package com.tutorial.dreamshops.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ApiResponse {
    private final String message;
    private final Object data;
}
