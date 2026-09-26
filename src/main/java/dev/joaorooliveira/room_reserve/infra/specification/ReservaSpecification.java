package dev.joaorooliveira.room_reserve.infra.specification;

import dev.joaorooliveira.room_reserve.domain.reserva.Reserva;
import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaFiltroRequestDTO;
import dev.joaorooliveira.room_reserve.domain.reserva.enums.StatusReservaTipo;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ReservaSpecification {

    public static Specification<Reserva> comFiltros(ReservaFiltroRequestDTO filtro) {
        return Specification
                .where(dataIgual(filtro.data()))
                .and(statusIgual(filtro.status()))
                .and(funcionarioIgual(filtro.funcionarioId()))
                .and(salaIgual(filtro.salaId()));
    }

    private static Specification<Reserva> dataIgual(LocalDate data) {
        return (root, query, cb) -> {
            if (data == null) {
                return null;
            }

            return cb.equal(root.get("data"), data);
        };
    }

    private static Specification<Reserva> statusIgual(StatusReservaTipo status) {
        return (root, query, cb) -> {
            if (status == null) {
                return null;
            }

            return cb.equal(root.get("status"), status);
        };
    }

    private static Specification<Reserva> funcionarioIgual(Long funcionarioId) {
        return (root, query, cb) -> {
            if (funcionarioId == null) {
                return null;
            }

            return cb.equal(
                    root.get("funcionario").get("id"),
                    funcionarioId
            );
        };
    }

    private static Specification<Reserva> salaIgual(Long salaId) {
        return (root, query, cb) -> {
            if (salaId == null) {
                return null;
            }

            return cb.equal(
                    root.get("sala").get("id"),
                    salaId
            );
        };
    }
}