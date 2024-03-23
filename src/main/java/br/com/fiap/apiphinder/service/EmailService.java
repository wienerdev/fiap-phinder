package br.com.fiap.apiphinder.service;

public interface EmailService {

    void sendWorkRecordEmail(String token) throws Exception;
}
