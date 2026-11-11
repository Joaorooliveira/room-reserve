package dev.joaorooliveira.room_reserve.domain.reserva.validacoes.atualizacao;

import dev.joaorooliveira.room_reserve.domain.reserva.Reserva;
import dev.joaorooliveira.room_reserve.domain.sala.Sala;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ValidadorAtualizarReserva {
    void validar(Reserva reserva, Sala sala, LocalDate data, LocalTime horaInicio, LocalTime horaFim);
}
