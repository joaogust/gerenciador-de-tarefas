package br.edu.ifsp.kanban.controller.dto.request;

import lombok.*;

@Data
public class LoginRequestDto {
    private String email;
    private String senha;
}
