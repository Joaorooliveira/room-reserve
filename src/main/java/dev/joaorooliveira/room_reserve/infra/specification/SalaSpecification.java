package dev.joaorooliveira.room_reserve.infra.specification;

import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import dev.joaorooliveira.room_reserve.domain.sala.dto.SalaFiltroRequestDTO;
import dev.joaorooliveira.room_reserve.domain.sala.enums.StatusTipo;
import org.springframework.data.jpa.domain.Specification;

public class SalaSpecification {

    public static Specification<Sala> comFiltros(SalaFiltroRequestDTO filtro) {
        return Specification
                .where(nomeContem(filtro.nome()))
                .and(capacidadeMaiorOuIgual(filtro.capacidadeMinima()))
                .and(statusIgual(filtro.status()));
    }

    private static Specification<Sala> nomeContem(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()) {
                return null;
            }

            return cb.like(
                    cb.lower(root.get("nome")),
                    "%" + nome.toLowerCase() + "%"
            );
        };
    }

    private static Specification<Sala> capacidadeMaiorOuIgual(Integer capacidadeMinima) {
        return (root, query, cb) -> {
            if (capacidadeMinima == null) {
                return null;
            }

            return cb.greaterThanOrEqualTo(
                    root.<Integer>get("capacidade"),
                    capacidadeMinima
            );
        };
    }

    private static Specification<Sala> statusIgual(StatusTipo status) {
        return (root, query, cb) -> {
            if (status == null) {
                return null;
            }

            return cb.equal(root.get("status"), status);
        };
    }
}