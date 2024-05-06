package com.santechture.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.santechture.api.constant.ApplicationConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralResponse implements Serializable {

    private Integer code;
    private String message;
    private Integer total;
    private Object data;

    public GeneralResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public GeneralResponse(int code, String message, Integer total, Object data) {
        this.code = code;
        this.message = message;
        this.total = total;
        this.data = data;
    }

    public ResponseEntity<GeneralResponse> response(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        return new ResponseEntity<>(this, status != null ? status : HttpStatus.OK);
    }

    public ResponseEntity<GeneralResponse> response(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.total = 1;
        this.data = data;
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

    public ResponseEntity<GeneralResponse> response(int code, String message, Object data, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.total = 1;
        this.data = data;
        return new ResponseEntity<>(this, status);
    }

    public ResponseEntity<GeneralResponse> create(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.total = 1;
        this.data = data;
        return new ResponseEntity<>(this, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<GeneralResponse> response(int total, int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.total = total;
        this.data = data;
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

    public ResponseEntity<GeneralResponse> response(int total, int code, String message, Object data, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.total = total;
        this.data = data;
        return new ResponseEntity<>(this, status);
    }

    public ResponseEntity<GeneralResponse> response(Object data) {
        this.code = 200;
        this.message = ApplicationConstants.OPERATION_SUCCESS;
        this.total = 1;
        this.data = data;
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

    public ResponseEntity<GeneralResponse> response(Object data, HttpStatus status) {
        this.code = 200;
        this.message = ApplicationConstants.OPERATION_SUCCESS;
        this.total = 1;
        this.data = data;
        return new ResponseEntity<>(this, status);
    }

    public ResponseEntity<GeneralResponse> responseWithTotal(int total, Object data) {
        this.code = 200;
        this.message = ApplicationConstants.OPERATION_SUCCESS;
        this.total = total;
        this.data = data;
        return new ResponseEntity<>(this, HttpStatus.OK);
    }
}