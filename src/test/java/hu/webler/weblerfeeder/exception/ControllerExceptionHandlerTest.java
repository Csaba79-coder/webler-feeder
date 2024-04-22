package hu.webler.weblerfeeder.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("Controller Exception Handling test")
public class ControllerExceptionHandlerTest {

    private final ControllerExceptionHandler controllerExceptionHandler = new ControllerExceptionHandler();

    @Test
    @DisplayName("Given no such element exception and when handling it then returns not found response")
    public void givenNoSuchElementException_whenHandling_thenReturnsNotFoundResponse() {
        //Given
        NoSuchElementException exception = new NoSuchElementException("Element not found");

        // When
        ResponseEntity<Object> responseEntity = controllerExceptionHandler.handleNoSuchElementException(exception);

        // Then
        then(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(responseEntity.getBody()).isEqualTo("{ERROR_CODE_001=Element not found}");
    }

    @Test
    @DisplayName("Given input mismatch exception and when handling it then returns not found response")
    public void givenInputMismatchException_whenHandling_thenReturnsNotFoundResponse() {
        // Given
        InputMismatchException exception = new InputMismatchException("Input mismatch");

        // When
        ResponseEntity<Object> responseEntity = controllerExceptionHandler.handleInputMismatchException(exception);

        // Then
        then(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(responseEntity.getBody()).isEqualTo("{ERROR_CODE_002=Input mismatch}");
    }

    @Test
    @DisplayName("Given invalid input exception and when handling it then returns not found response")
    public void givenInvalidInputException_whenHandling_thenReturnsNotFoundResponse() {
        // Given
        InvalidInputException exception = new InvalidInputException("Invalid input");

        // When
        ResponseEntity<Object> responseEntity = controllerExceptionHandler.handleInvalidInputException(exception);

        // Then
        then(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(responseEntity.getBody()).isEqualTo("{ERROR_CODE_003=Invalid input}");
    }

    @Test
    @DisplayName("Given entity already exists exception and when handling it then returns not bad request")
    public void givenEntityAlreadyExistsException_whenHandling_thenReturnsBadRequestResponse() {
        // Given
        EntityAlreadyExistsException exception = new EntityAlreadyExistsException("Entity already exists");

        // When
        ResponseEntity<Object> responseEntity = controllerExceptionHandler.handleEntityAlreadyExistsException(exception);

        // Then
        then(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        then(responseEntity.getBody()).isEqualTo("{ERROR_CODE_004=Entity already exists}");
    }
}
