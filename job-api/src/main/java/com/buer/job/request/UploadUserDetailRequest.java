package com.buer.job.request;

import javax.validation.constraints.NotBlank;

public class UploadUserDetailRequest {
    @NotBlank
    public String school;
    @NotBlank
    public String graduationTime;
}
