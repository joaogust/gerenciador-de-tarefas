package br.edu.ifsp.kanban.controller.dto.response;

import br.edu.ifsp.kanban.model.canonical.BlocoCanonical;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaResponseDto {
    private Integer idTarefa;
    private String texto;
    private boolean estado;
/*
    public TarefaResponseDto() { }

    public TarefaResponseDto(Integer idTarefa, String texto, boolean estado) {
        this.idTarefa = idTarefa;
        this.texto = texto;
        this.estado = estado;
    }

    public Integer getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(Integer idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

 */
}
