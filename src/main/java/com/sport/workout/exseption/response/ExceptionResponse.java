package com.sport.workout.exseption.response;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ExceptionResponse {

    private String message;
    private String status;
    private String path;

}
