package dev.joaorooliveira.room_reserve.domain.reserva.validacoes.atualizacao;

import dev.joaorooliveira.room_reserve.domain.reserva.Reserva;
import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import dev.joaorooliveira.room_reserve.infra.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ValidadorIntervaloHorario implements ValidadorAtualizarReserva {

    @Override
    public void validar(Reserva reserva, Sala sala, LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        if (horaInicio.isAfter(horaFim) || horaInicio.equals(horaFim)) {
            throw new RegraNegocioException("O horário de início deve ser anterior ao horário de término.");
        }
    }
}
