package com.cosmoFusionStore.validate;

import com.cosmoFusionStore.rest.requestModel.UpdateRegistrationStatusRequest;
import com.cosmoFusionStore.serviceexception.BadRequestException;
import com.cosmoFusionStore.rest.requestModel.RegistrationRequest;
import com.cosmoFusionStore.enums.RolesEnum;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegisterRequestValidation {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public void validateRegisterRequest(RegistrationRequest registrationRequest) {
        if(registrationRequest == null) {
            throw new BadRequestException("registrationRequest object should not be null");
        } else if(registrationRequest.getFirstName() == null || registrationRequest.getFirstName().isBlank()) {
            notNullNorEmptyErrorMsg("firstName", false);
        } else if(registrationRequest.getLastName() == null || registrationRequest.getLastName().isBlank()) {
            notNullNorEmptyErrorMsg("lastName", false);
        } else if(registrationRequest.getEmail() == null || registrationRequest.getEmail().isBlank()) {
            notNullNorEmptyErrorMsg("email", false);
        } else if(!validateEmailPattern(registrationRequest.getEmail())) {
            throw new BadRequestException("email is not in a valid format.");
        } else if(registrationRequest.getPassword() == null || registrationRequest.getPassword().isBlank()) {
            notNullNorEmptyErrorMsg("password", false);
        } else if(registrationRequest.getPhoneNumber() == null || registrationRequest.getPhoneNumber().isBlank()) {
            notNullNorEmptyErrorMsg("phoneNumber", false);
        } else if(registrationRequest.getRegisterType() == null || registrationRequest.getRegisterType().isBlank()) {
            notNullNorEmptyErrorMsg("designation", false);
        } else if(RolesEnum.getRoleById(registrationRequest.getRegisterType()) == null) {
            throw new BadRequestException("registerType is invalid.");
        }
    }

    public void validatePathParams(String key, String value) {
        if(value == null && value.isBlank()) notNullNorEmptyErrorMsg(key, true);
    }

    public void validateUpdateRegistrationRequest(UpdateRegistrationStatusRequest updateRegistrationStatusRequest) {
        if(updateRegistrationStatusRequest.getRegistrationStatus() == null || updateRegistrationStatusRequest.getRegistrationStatus().isBlank()) {
            notNullNorEmptyErrorMsg("registrationStatus", false);
        } else if(updateRegistrationStatusRequest.getUserId() == null || updateRegistrationStatusRequest.getUserId().isBlank()) {
            notNullNorEmptyErrorMsg("userId", false);
        }
    }

    private void notNullNorEmptyErrorMsg(String msg, boolean isPathVariable) {
        if (isPathVariable) throw new BadRequestException(msg + " should not be null or empty in Path Param");
        throw new BadRequestException(msg + " should not be null or empty");
    }

    private boolean validateEmailPattern(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
