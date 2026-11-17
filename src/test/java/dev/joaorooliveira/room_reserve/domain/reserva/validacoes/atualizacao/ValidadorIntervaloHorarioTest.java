package dev.joaorooliveira.room_reserve.domain.reserva.validacoes.atualizacao;

import dev.joaorooliveira.room_reserve.infra.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidadorIntervaloHorarioTest {

    @Test
    void devePermitirQuandoHoraInicioForAnteriorAoFim() {

        ValidadorIntervaloHorario validador = new ValidadorIntervaloHorario();

        LocalTime horaInicio = LocalTime.of(14, 0);
        LocalTime horaFim = LocalTime.of(16, 0);

        assertDoesNotThrow(() -> validador.validar(
                null,
                null,
                null,
                horaInicio,
                horaFim
        ));
    }

    @Test
    void deveImpedirQuandoHoraInicioForIgualAoFim() {

        ValidadorIntervaloHorario validador = new ValidadorIntervaloHorario();

        LocalTime horaInicio = LocalTime.of(14, 0);
        LocalTime horaFim = LocalTime.of(14, 0);

        assertThrows(RegraNegocioException.class, () -> validador.validar(
                null,
                null,
                null,
                horaInicio,
                horaFim
        ));
    }

    @Test
    void deveImpedirQuandoHoraInicioForPosteriorAoFim() {

        ValidadorIntervaloHorario validador = new ValidadorIntervaloHorario();

        LocalTime horaInicio = LocalTime.of(16, 0);
        LocalTime horaFim = LocalTime.of(14, 0);

        assertThrows(RegraNegocioException.class, () -> validador.validar(
                null,
                null,
                null,
                horaInicio,
                horaFim
        ));
    }

}