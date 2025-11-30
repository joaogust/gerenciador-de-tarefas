package br.edu.ifsp.kanban.controller.resource;

import br.edu.ifsp.kanban.controller.dto.request.UsuarioRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.UsuarioResponseDto;
import br.edu.ifsp.kanban.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public UsuarioResponseDto criar(@RequestBody UsuarioRequestDto dto) {
        return usuarioService.criar(dto);
    }

    @GetMapping("/{id}")
    public UsuarioResponseDto getById(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id);
    }

    // NOVO ENDPOINT para buscar usuário pelo email
    @GetMapping("/email/{email}")
    public UsuarioResponseDto getByEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email);
    }

    @PutMapping("/{id}")
    public UsuarioResponseDto atualizar(@PathVariable Integer id,
                                        @RequestBody UsuarioRequestDto dto) {
        return usuarioService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        usuarioService.deletar(id);
    }
}
