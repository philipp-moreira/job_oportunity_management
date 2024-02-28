package br.com.rocketseat.job_oportunity_management.exceptions;

import java.util.ArrayList;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    MessageSource messageSource;

    public ExceptionHandlerController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler()
    public ResponseEntity<ArrayList<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errorList = new ArrayList<ErrorMessageDTO>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    var field = error.getField();
                    var message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
                    var errorParsed = new ErrorMessageDTO(field, message);
                    errorList.add(errorParsed);
                });
        return ResponseEntity.badRequest().body(errorList);
    }
}
