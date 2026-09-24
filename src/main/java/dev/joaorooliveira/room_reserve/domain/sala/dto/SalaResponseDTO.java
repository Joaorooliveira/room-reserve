package dev.joaorooliveira.room_reserve.domain.sala.dto;

import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import dev.joaorooliveira.room_reserve.domain.sala.enums.StatusTipo;

public record SalaResponseDTO(

        Long id,
        String nome,
        Integer capacidade,
        String localizacao,
        StatusTipo status
) {

    public static SalaResponseDTO fromEntity(Sala sala){
        return new SalaResponseDTO(
                sala.getId(),
                sala.getNome(),
                sala.getCapacidade(),
                sala.getLocalizacao(),
                sala.getStatus()
        );
    }

}
