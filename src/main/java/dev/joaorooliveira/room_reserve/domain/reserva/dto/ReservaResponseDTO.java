package dev.joaorooliveira.room_reserve.domain.reserva.dto;

import dev.joaorooliveira.room_reserve.domain.reserva.Reserva;
import dev.joaorooliveira.room_reserve.domain.reserva.enums.StatusReservaTipo;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaResponseDTO(

        Long id,
        LocalDate data,
        LocalTime horaInicio,
        LocalTime horaFim,
        StatusReservaTipo status,
        Long idFuncionario,
        String nomeFuncionario,
        Long idSala,
        String nomeSala
) {

    public static ReservaResponseDTO fromEntity(Reserva reserva){
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getData(),
                reserva.getHoraInicio(),
                reserva.getHoraFim(),
                reserva.getStatus(),
                reserva.getFuncionario().getId(),
                reserva.getFuncionario().getNome(),
                reserva.getSala().getId(),
                reserva.getSala().getNome()
        );
    }

}
