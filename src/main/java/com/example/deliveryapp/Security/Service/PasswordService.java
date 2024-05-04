package com.example.deliveryapp.Security.Service;

import com.example.deliveryapp.Security.Model.ChangePasswordRequest;
import com.example.deliveryapp.Security.Model.ResetPasswordRequest;
import javax.servlet.http.HttpServletRequest;

public interface PasswordService {

  String changePassword(ChangePasswordRequest changePasswordRequest,
      HttpServletRequest httpServletRequest);

  String resetPasswordLink(String emailId);

  String confirmResetPasswordLink(String token, ResetPasswordRequest resetPasswordRequest);
}
