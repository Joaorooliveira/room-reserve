package dev.joaorooliveira.room_reserve.domain.sala.dto;

import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record SalaAtualizarDTO(

        @Size(max = 100,message = "Nome so pode ter ate 100 caracteres")
        String nome,

        @Positive(message = "Capacidade da sala deve ser maior que zero")
        Integer capacidade,

        @Size(max = 150,message = "Localizacao fora do tamanho padrao")
        String localizacao

) {

    public void preencher(Sala sala) {
        if(this.nome!= null){
            sala.setNome(this.nome);
        }
        if(this.capacidade != null){
            sala.setCapacidade(this.capacidade);
        }
        if(this.localizacao != null){
            sala.setLocalizacao(this.localizacao);
        }

    }

}
