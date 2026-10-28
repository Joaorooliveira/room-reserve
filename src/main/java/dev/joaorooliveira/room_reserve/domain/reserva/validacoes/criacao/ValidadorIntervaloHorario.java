package dev.joaorooliveira.room_reserve.domain.reserva.validacoes.criacao;

import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import dev.joaorooliveira.room_reserve.infra.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ValidadorIntervaloHorario implements ValidadorSalvarReserva {

    @Override
    public void validar(Sala sala, LocalDate data, LocalTime horaInicio, LocalTime horaFim) {

        if (!horaInicio.isBefore(horaFim)) {
            throw new RegraNegocioException(
                    "O horário de início deve ser anterior ao horário de fim."
            );
        }
    }
}