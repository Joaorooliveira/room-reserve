package dev.joaorooliveira.room_reserve.domain.funcionario;

import dev.joaorooliveira.room_reserve.domain.funcionario.dto.FuncionarioRequestDTO;
import dev.joaorooliveira.room_reserve.domain.funcionario.dto.FuncionarioResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Transactional
    public FuncionarioResponseDTO salvarFuncionario(FuncionarioRequestDTO funcionarioRequestDTO) {
        Funcionario funcionario = funcionarioRepository.save(funcionarioRequestDTO.toEntity());
        return FuncionarioResponseDTO.fromEntity(funcionario);
    }
}
