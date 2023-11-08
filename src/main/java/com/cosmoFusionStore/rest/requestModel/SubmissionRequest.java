package com.cosmoFusionStore.rest.requestModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionRequest {
    private String employeeId;
    private String staffId;
    private boolean didEmployeeRespond;
    private Date submittedTime;
    private String timeZone;
    private int clientId;
    private int implementationPartnerId;
    private int vendorId;
    private boolean clientResponse;
    private boolean implentationPartnerResponse;
    private boolean vendorResponse;
    private int numberOfInterviewRounds;
    private int numberOfInterviewRoundsCracked;
    private String jobId;
    private String jobDescription;
    private String position;
}
