package br.edu.ifsp.kanban.controller.resource;
/*
import br.edu.ifsp.kanban.controller.dto.request.LoginRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.TokenResponseDto;
import br.edu.ifsp.kanban.controller.dto.response.UsuarioResponseDto;
import br.edu.ifsp.kanban.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody LoginRequestDto dto) {
        return authService.login(dto);
    }


    @GetMapping("/me")
    public UsuarioResponseDto getMe(@AuthenticationPrincipal String email) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setEmail(email);
        return dto;
        //return authService.getMe(email);
    }

    @GetMapping("/me")
    public Map<String, String> getMe(@AuthenticationPrincipal String email) {
        Map<String, String> res = new HashMap<>();
        res.put("email", email);
        return res;
    }

    @GetMapping("/teste")
    public String getTeste() {
        return "Teste";
    }

}
*/