package dev.joaorooliveira.room_reserve.domain.funcionario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FuncionarioRequestDTO(

        @NotBlank(message = "Nome nao pode ser nulo nem vazio")
        @Size(max = 150,message = "Nome so pode ter ate 150 caracteres")
        String nome,

        @Email(message = "Nao esta no formato de Email")
        @Size(max = 150,message = "Email so pode ter ate 150 caracteres")
        @NotBlank(message = "Email nao pode ser nulo nem vazio")
        String email,

        @Size(max = 10,message = "Ramal fora do tamanho padrao")
        @NotBlank(message = "Ramal nao pode ser nulo nem vazio")
        String ramal

) {
}
