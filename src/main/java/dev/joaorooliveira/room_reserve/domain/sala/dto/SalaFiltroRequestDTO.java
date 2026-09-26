package dev.joaorooliveira.room_reserve.domain.sala.dto;

import dev.joaorooliveira.room_reserve.domain.sala.enums.StatusTipo;

public record SalaFiltroRequestDTO(
        String nome,
        Integer capacidadeMinima,
        StatusTipo status

) {
}
