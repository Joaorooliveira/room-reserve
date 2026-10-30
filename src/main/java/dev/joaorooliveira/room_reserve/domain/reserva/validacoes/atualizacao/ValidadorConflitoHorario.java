package dev.joaorooliveira.room_reserve.domain.reserva.validacoes.atualizacao;

import dev.joaorooliveira.room_reserve.domain.reserva.Reserva;
import dev.joaorooliveira.room_reserve.domain.reserva.ReservaRepository;
import dev.joaorooliveira.room_reserve.domain.reserva.enums.StatusReservaTipo;
import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import dev.joaorooliveira.room_reserve.infra.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ValidadorConflitoHorario implements ValidadorAtualizarReserva {

    private final ReservaRepository reservaRepository;

    public ValidadorConflitoHorario(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public void validar(
            Reserva reserva,
            Sala sala,
            LocalDate data,
            LocalTime horaInicio,
            LocalTime horaFim
    ) {

        boolean existeConflito =
                reservaRepository.existsBySalaIdAndDataAndStatusAndHoraFimAfterAndHoraInicioBeforeAndIdNot(
                        sala.getId(),
                        data,
                        StatusReservaTipo.AGENDADA,
                        horaInicio,
                        horaFim,
                        reserva.getId()
                );

        if (existeConflito) {
            throw new RegraNegocioException(
                    "A sala já possui outra reserva agendada nesse horário."
            );
        }
    }
}