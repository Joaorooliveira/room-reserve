package dev.joaorooliveira.room_reserve.domain.funcionario;

import dev.joaorooliveira.room_reserve.domain.funcionario.dto.FuncionarioRequestDTO;
import dev.joaorooliveira.room_reserve.domain.funcionario.dto.FuncionarioResponseDTO;
import dev.joaorooliveira.room_reserve.infra.exception.EntidadeNaoEncontradaException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public FuncionarioResponseDTO buscarFuncionarioPorId(Long id) {
        Funcionario funcionario = buscarFuncionarioPorIdEntidade(id);
        return FuncionarioResponseDTO.fromEntity(funcionario);
    }

    public Page<FuncionarioResponseDTO> buscarTodosFuncionarios(Pageable pageable) {
        return funcionarioRepository.findAll(pageable)
                .map(FuncionarioResponseDTO::fromEntity);
    }

    @Transactional
    public void deletarFuncionario(Long id) {
        Funcionario funcionario = buscarFuncionarioPorIdEntidade(id);
        funcionarioRepository.delete(funcionario);
    }

    private Funcionario buscarFuncionarioPorIdEntidade(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionario não encontrado"));
    }


}
