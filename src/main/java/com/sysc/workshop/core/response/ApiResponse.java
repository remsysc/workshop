package com.sysc.workshop.core.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//response back to front end / client
public class ApiResponse {
    private String message;
    private  Object data;


}
