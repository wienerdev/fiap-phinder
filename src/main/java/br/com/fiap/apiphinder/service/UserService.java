package br.com.fiap.apiphinder.service;

import br.com.fiap.apiphinder.controller.dto.SignupRequest;

public interface UserService {

    void signup(SignupRequest request) throws Exception;
}
