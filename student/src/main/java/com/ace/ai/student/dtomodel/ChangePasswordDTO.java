package com.ace.ai.student.dtomodel;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@Data
public class ChangePasswordDTO {
    @NotEmpty(message="Old Password Must not be empty!")
    private String oldPassword;
    @NotEmpty(message="New Password Must not be empty!")
    @Size (min=8 ,message=" Password's length must be at least 8")
    private String newPassword;
    @NotEmpty(message="Confirm Password Must not be empty!")
    private String confirmPassword;

}
