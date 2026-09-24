package dev.joaorooliveira.room_reserve.domain.sala.dto;

import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import dev.joaorooliveira.room_reserve.domain.sala.enums.StatusTipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record SalaRequestDTO(

        @NotBlank(message = "Nome nao pode ser nulo nem vazio")
        @Size(max = 100,message = "Nome so pode ter ate 100 caracteres")
        String nome,

        @NotNull(message = "Capacidade nao pode ser nula")
        @Positive(message = "Capacidade da sala deve ser maior que zero")
        Integer capacidade,

        @NotBlank(message = "Localizacao nao pode ser nula nem vazia")
        @Size(max = 150,message = "Localizacao fora do tamanho padrao")
        String localizacao

) {

    public Sala toEntity(){
        Sala sala = new Sala();
        preencher(sala);
        return sala;
    }

    private void preencher(Sala sala) {
        sala.setNome(this.nome);
        sala.setCapacidade(this.capacidade);
        sala.setLocalizacao(this.localizacao);
        sala.setStatus(StatusTipo.ATIVA);
    }

}
