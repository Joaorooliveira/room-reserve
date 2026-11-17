package dev.joaorooliveira.room_reserve.domain.reserva.validacoes.atualizacao;

import dev.joaorooliveira.room_reserve.infra.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidadorDataHorarioTest {

    private ValidadorDataHorario validador;

    @BeforeEach
    void prepararValidador() {
        validador = new ValidadorDataHorario();
    }

    @Test
    void deveImpedirQuandoDataForNoPassado() {

        LocalDate data = LocalDate.now().minusDays(1);

        assertThrows(RegraNegocioException.class, () -> validador.validar(
                null,
                null,
                data,
                null,
                null
        ));
    }

    @Test
    void deveImpedirQuandoDataForHojeEHoraInicioForNoPassado() {

        LocalDate data = LocalDate.now();
        LocalTime horaInicio = LocalTime.MIN;

        assertThrows(RegraNegocioException.class, () -> validador.validar(
                null,
                null,
                data,
                horaInicio,
                null
        ));
    }

    @Test
    void devePermitirQuandoDataForHojeEHoraInicioForFuturo() {

        LocalDate data = LocalDate.now();
        LocalTime horaInicio = LocalTime.MAX;

        assertDoesNotThrow(() -> validador.validar(
                null,
                null,
                data,
                horaInicio,
                null
        ));
    }

    @Test
    void devePermitirQuandoDataForNoFuturo() {

        LocalDate data = LocalDate.now().plusDays(1);
        LocalTime horaInicio = LocalTime.of(8, 0);

        assertDoesNotThrow(() -> validador.validar(
                null,
                null,
                data,
                horaInicio,
                null
        ));
    }
}