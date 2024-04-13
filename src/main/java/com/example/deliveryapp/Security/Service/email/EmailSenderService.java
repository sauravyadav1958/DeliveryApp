package com.example.deliveryapp.Security.Service.email;

public interface EmailSenderService {

    void send(String to, String text);

    String buildEmail(String name, String link);
}