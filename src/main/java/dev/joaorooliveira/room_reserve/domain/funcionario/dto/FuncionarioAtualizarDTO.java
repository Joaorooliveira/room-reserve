package dev.joaorooliveira.room_reserve.domain.funcionario.dto;

import dev.joaorooliveira.room_reserve.domain.funcionario.Funcionario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record FuncionarioAtualizarDTO(

        @Size(max = 150,message = "Nome so pode ter ate 150 caracteres")
        String nome,

        @Email(message = "Nao esta no formato de Email")
        @Size(max = 150,message = "Email so pode ter ate 150 caracteres")
        String email,

        @Size(max = 10,message = "Ramal fora do tamanho padrao")
        String ramal
) {

    public void preencher(Funcionario funcionario){
        if(this.nome!=null){
            funcionario.setNome(this.nome);
        }
        if(this.email!=null){
            funcionario.setEmail(this.email);
        }
        if(this.ramal!=null){
            funcionario.setRamal(this.ramal);
        }
    }

}
