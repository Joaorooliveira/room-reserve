package dev.joaorooliveira.room_reserve.domain.reserva.validacoes.atualizacao;

import dev.joaorooliveira.room_reserve.domain.reserva.Reserva;
import dev.joaorooliveira.room_reserve.domain.reserva.ReservaRepository;
import dev.joaorooliveira.room_reserve.domain.reserva.enums.StatusReservaTipo;
import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import dev.joaorooliveira.room_reserve.infra.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidadorConflitoHorarioTest {

    @Mock
    private ReservaRepository reservaRepository;

    private ValidadorConflitoHorario validador;

    @BeforeEach
    void prepararValidador() {
        validador = new ValidadorConflitoHorario(reservaRepository);
    }

    @Test
    void devePermitirQuandoNaoExistirConflitoHorario() {

        Reserva reserva = new Reserva();
        reserva.setId(10L);

        Sala sala = new Sala();
        sala.setId(1L);

        LocalDate data = LocalDate.of(2026, 11, 20);
        LocalTime horaInicio = LocalTime.of(14, 0);
        LocalTime horaFim = LocalTime.of(16, 0);

        when(reservaRepository
                .existsBySalaIdAndDataAndStatusAndHoraFimAfterAndHoraInicioBeforeAndIdNot(
                        1L,
                        data,
                        StatusReservaTipo.AGENDADA,
                        horaInicio,
                        horaFim,
                        10L
                )).thenReturn(false);

        assertDoesNotThrow(() ->
                validador.validar(reserva, sala, data, horaInicio, horaFim)
        );
    }

    @Test
    void deveImpedirQuandoExistirConflitoHorario() {

        Reserva reserva = new Reserva();
        reserva.setId(10L);

        Sala sala = new Sala();
        sala.setId(1L);

        LocalDate data = LocalDate.of(2026, 11, 20);
        LocalTime horaInicio = LocalTime.of(14, 0);
        LocalTime horaFim = LocalTime.of(16, 0);

        when(reservaRepository
                .existsBySalaIdAndDataAndStatusAndHoraFimAfterAndHoraInicioBeforeAndIdNot(
                        1L,
                        data,
                        StatusReservaTipo.AGENDADA,
                        horaInicio,
                        horaFim,
                        10L
                )).thenReturn(true);

        assertThrows(RegraNegocioException.class, () ->
                validador.validar(reserva, sala, data, horaInicio, horaFim)
        );
    }
}