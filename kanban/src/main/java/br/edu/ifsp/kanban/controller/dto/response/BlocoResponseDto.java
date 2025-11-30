package br.edu.ifsp.kanban.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlocoResponseDto {
    private Long id;
    private String nome;
    private String estado;
    private List<TarefaResponseDto> tarefas;

    /*
    public BlocoResponseDto() { }

    public BlocoResponseDto(Long id, String nome, String estado, List<TarefaResponseDto> tarefas) {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
        this.tarefas = tarefas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<TarefaResponseDto> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<TarefaResponseDto> tarefas) {
        this.tarefas = tarefas;
    }
     */
}
