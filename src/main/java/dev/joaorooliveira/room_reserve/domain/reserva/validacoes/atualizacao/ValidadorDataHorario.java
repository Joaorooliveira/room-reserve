package dev.joaorooliveira.room_reserve.domain.reserva.validacoes.atualizacao;

import dev.joaorooliveira.room_reserve.domain.reserva.Reserva;
import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import dev.joaorooliveira.room_reserve.infra.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ValidadorDataHorario implements ValidadorAtualizarReserva {
    @Override
    public void validar(Reserva reserva, Sala sala, LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        LocalDate hoje = LocalDate.now();
        LocalTime agora = LocalTime.now();

        if (data.isBefore(hoje)) {
            throw new RegraNegocioException("A data da reserva não pode estar no passado.");
        }

        if (data.isEqual(hoje) && !horaInicio.isAfter(agora)) {
            throw new RegraNegocioException("O horário de início da reserva deve ser futuro.");
        }
    }
}
