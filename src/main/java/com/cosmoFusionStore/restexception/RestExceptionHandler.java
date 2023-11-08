package com.cosmoFusionStore.restexception;

import com.cosmoFusionStore.rest.errorModel.BadRequestErrorResponse;
import com.cosmoFusionStore.rest.errorModel.ErrorResponse;
import com.cosmoFusionStore.rest.errorModel.UserDaoErrorResponse;
import com.cosmoFusionStore.serviceexception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BadRequestErrorResponse> restBadRequestExceptionHandler(BadRequestException exception) {
        BadRequestErrorResponse badRequestErrorResponse = new BadRequestErrorResponse();

        badRequestErrorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        badRequestErrorResponse.setErrorMessage(exception.getMessage());

        return new ResponseEntity<>(badRequestErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UserDaoErrorResponse> restUserDaoExceptionHandler(SignupDataAcquirerException signupDataAcquirerException) {
        UserDaoErrorResponse userDaoErrorResponse = new UserDaoErrorResponse();

        userDaoErrorResponse.setHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        userDaoErrorResponse.setErrorMessage(signupDataAcquirerException.getMessage());

        return new ResponseEntity<>(userDaoErrorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> userDAOExceptionHandler(UserDAOException userDAOException) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        errorResponse.setErrorMessage(userDAOException.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> userAccessDeniedErrorHandler(UnAuthorizedException unAuthorizedException) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setHttpStatus(HttpStatus.FORBIDDEN.value());
        errorResponse.setErrorMessage(unAuthorizedException.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> DatabasePersistenceErrorHandler(DatabasePersistenceException databasePersistenceException) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        errorResponse.setErrorMessage(databasePersistenceException.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> DatabaseRetrievalErrorHandler(DatabaseRetrievalException databaseRetrievalException) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        errorResponse.setErrorMessage(databaseRetrievalException.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
