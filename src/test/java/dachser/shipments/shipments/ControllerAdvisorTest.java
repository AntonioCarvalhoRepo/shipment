package dachser.shipments.shipments;

import dachser.shipments.shipments.exception.ControllerAdvisor;
import dachser.shipments.shipments.exception.ShipmentNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerAdvisorTest {

    private final ControllerAdvisor advisor = new ControllerAdvisor();

    @Test
    void handleShipmentNotFound_ReturnsNotFound() {
        ShipmentNotFoundException ex = new ShipmentNotFoundException("Not found");
        ResponseEntity<String> response = advisor.handleShipmentNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not found", response.getBody());
    }

    @Test
    void handleMethodArgumentNotValid_ReturnsBadRequestWithErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "must not be null");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Object> response = advisor.handleMethodArgumentNotValid(
                ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, mock(WebRequest.class));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertNotNull(body);
        assertEquals("must not be null", body.get("field"));
    }

    @Test
    void handleHttpMessageNotReadable_ReturnsBadRequestWithError() {
        Throwable cause = new RuntimeException("JSON parse error");
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Malformed", cause, null);

        ResponseEntity<Object> response = advisor.handleHttpMessageNotReadable(
                ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, mock(WebRequest.class));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertNotNull(body);
        assertEquals("Malformed JSON request", body.get("error"));
        assertTrue(((String) body.get("message")).contains("JSON parse error"));
    }
}
