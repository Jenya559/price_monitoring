package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.handler;


import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ResponseErrorDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ShopNotFoundException.class)
    protected ResponseEntity<ResponseErrorDTO> handleShopNotFoundException(RuntimeException ex) {
        ResponseErrorDTO response = new ResponseErrorDTO(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    protected ResponseEntity<ResponseErrorDTO> handleCategoryNotFoundException(RuntimeException ex) {
        ResponseErrorDTO response = new ResponseErrorDTO(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<ResponseErrorDTO> handleProductNotFoundException(RuntimeException ex) {
        ResponseErrorDTO response = new ResponseErrorDTO(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(ProductShopDuplicateException.class)
    protected ResponseEntity<ResponseErrorDTO> handleProductShopDuplicateException(RuntimeException ex) {
        ResponseErrorDTO response = new ResponseErrorDTO(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(MonitoringNotFoundException.class)
    protected ResponseEntity<ResponseErrorDTO> handleMonitoringNotFoundException(RuntimeException ex) {
        ResponseErrorDTO response = new ResponseErrorDTO(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(ProductDublicateException.class)
    protected ResponseEntity<ResponseErrorDTO> handleProductDuplicateException(RuntimeException ex) {
        ResponseErrorDTO response = new ResponseErrorDTO(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(ShopDublicateEcxeption.class)
    protected ResponseEntity<ResponseErrorDTO> handleShopDublicateEcxeption(RuntimeException ex) {
        ResponseErrorDTO response = new ResponseErrorDTO(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(CategoryDublicateException.class)
    protected ResponseEntity<ResponseErrorDTO> handleCategoryDublicateException(RuntimeException ex) {
        ResponseErrorDTO response = new ResponseErrorDTO(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
