package dev.joaorooliveira.room_reserve.domain.reserva.validacoes.criacao;

import dev.joaorooliveira.room_reserve.domain.reserva.ReservaRepository;
import dev.joaorooliveira.room_reserve.domain.reserva.enums.StatusReservaTipo;
import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import dev.joaorooliveira.room_reserve.infra.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ValidadorConflitoHorario implements ValidadorSalvarReserva {

    private final ReservaRepository reservaRepository;

    public ValidadorConflitoHorario(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public void validar(
            Sala sala, LocalDate data, LocalTime horaInicio, LocalTime horaFim) {

        boolean existeConflito =
                reservaRepository.existsBySalaIdAndDataAndStatusAndHoraFimAfterAndHoraInicioBefore(
                        sala.getId(),
                        data,
                        StatusReservaTipo.AGENDADA,
                        horaInicio,
                        horaFim
                );

        if (existeConflito) {
            throw new RegraNegocioException(
                    "A sala já possui uma reserva agendada nesse horário."
            );
        }
    }
}