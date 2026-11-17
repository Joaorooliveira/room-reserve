package dev.joaorooliveira.room_reserve.domain.reserva.validacoes.atualizacao;

import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import dev.joaorooliveira.room_reserve.domain.sala.enums.StatusTipo;
import dev.joaorooliveira.room_reserve.infra.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidadorSalaAtivaTest {

    private ValidadorSalaAtiva validador;

    @BeforeEach
    void prepararValidador() {
        validador = new ValidadorSalaAtiva();
    }

    @Test
    void devePermitirQuandoSalaEstiverAtiva() {

        Sala sala = new Sala();
        sala.setStatus(StatusTipo.ATIVA);

        assertDoesNotThrow(() -> validador.validar(
                null,
                sala,
                null,
                null,
                null
        ));
    }

    @Test
    void deveImpedirQuandoSalaEstiverInativa() {

        Sala sala = new Sala();
        sala.setStatus(StatusTipo.INATIVA);

        assertThrows(RegraNegocioException.class, () -> validador.validar(
                null,
                sala,
                null,
                null,
                null
        ));
    }
}