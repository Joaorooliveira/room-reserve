package dev.joaorooliveira.room_reserve.domain.reserva.validacoes.criacao;

import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import dev.joaorooliveira.room_reserve.domain.sala.enums.StatusTipo;
import dev.joaorooliveira.room_reserve.infra.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ValidadorSalaAtiva implements ValidadorSalvarReserva {

    @Override
    public void validar(Sala sala, LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        if (sala.getStatus() != StatusTipo.ATIVA) {
            throw new RegraNegocioException(
                    "A sala não está ativa para reserva."
            );
        }
    }
}
