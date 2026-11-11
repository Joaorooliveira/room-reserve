package dev.joaorooliveira.room_reserve.domain.reserva.validacoes.criacao;

import dev.joaorooliveira.room_reserve.domain.sala.Sala;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ValidadorSalvarReserva {
    void validar(Sala sala, LocalDate data, LocalTime horaInicio, LocalTime horaFim);
}
