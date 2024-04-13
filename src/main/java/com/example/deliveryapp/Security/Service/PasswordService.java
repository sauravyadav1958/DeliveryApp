package com.example.deliveryapp.Security.Service;

import com.example.deliveryapp.Security.Model.ChangePasswordRequest;
import com.example.deliveryapp.Security.Model.ResetPasswordRequest;

public interface PasswordService {

  String changePassword(ChangePasswordRequest changePasswordRequest);

  String resetPasswordLink(String emailId);

  String confirmResetPasswordLink(String token, ResetPasswordRequest resetPasswordRequest);
}
