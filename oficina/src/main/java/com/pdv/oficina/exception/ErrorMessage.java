package com.pdv.oficina.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ErrorMessage {

    private Date currentDate;
    private String message;
    private String details;

    public ErrorMessage(Date currentDate, String message, String details) {
        this.currentDate = currentDate;
        this.message = message;
        this.details = details;
    }
}
