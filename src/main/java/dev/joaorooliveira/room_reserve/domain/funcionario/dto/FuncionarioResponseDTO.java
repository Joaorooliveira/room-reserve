package dev.joaorooliveira.room_reserve.domain.funcionario.dto;

import dev.joaorooliveira.room_reserve.domain.funcionario.Funcionario;

public record FuncionarioResponseDTO(

        Long id,
        String nome,
        String email,
        String ramal
) {

    public static FuncionarioResponseDTO fromEntity(Funcionario funcionario) {
        return new FuncionarioResponseDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getEmail(),
                funcionario.getRamal()
        );
    }

}
