package dev.joaorooliveira.room_reserve.domain.reserva.dto;

import dev.joaorooliveira.room_reserve.domain.reserva.enums.StatusReservaTipo;

import java.time.LocalDate;

public record ReservaFiltroRequestDTO(

        LocalDate data,
        StatusReservaTipo status,
        Long funcionarioId,
        Long salaId

) {
}
