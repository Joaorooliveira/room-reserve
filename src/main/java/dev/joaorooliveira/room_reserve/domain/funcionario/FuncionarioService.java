package dev.joaorooliveira.room_reserve.domain.funcionario;

import dev.joaorooliveira.room_reserve.domain.funcionario.dto.FuncionarioAtualizarDTO;
import dev.joaorooliveira.room_reserve.domain.funcionario.dto.FuncionarioRequestDTO;
import dev.joaorooliveira.room_reserve.domain.funcionario.dto.FuncionarioResponseDTO;
import dev.joaorooliveira.room_reserve.domain.reserva.ReservaRepository;
import dev.joaorooliveira.room_reserve.infra.exception.EntidadeNaoEncontradaException;
import dev.joaorooliveira.room_reserve.infra.exception.RegraNegocioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final ReservaRepository reservaRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, ReservaRepository reservaRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.reservaRepository = reservaRepository;
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
    public FuncionarioResponseDTO atualizarFuncionario(Long id, FuncionarioAtualizarDTO funcionarioAtualizarDTO) {
        Funcionario funcionario = buscarFuncionarioPorIdEntidade(id);
        funcionarioAtualizarDTO.preencher(funcionario);
        return FuncionarioResponseDTO.fromEntity(funcionario);
    }

    @Transactional
    public void deletarFuncionario(Long id) {
        Funcionario funcionario = buscarFuncionarioPorIdEntidade(id);
        if (reservaRepository.existsByFuncionarioId(id)) {
            throw new RegraNegocioException("Não é possível deletar o funcionário," +
                    " pois ele possui reservas associadas.");
        }
        funcionarioRepository.delete(funcionario);
    }


    private Funcionario buscarFuncionarioPorIdEntidade(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionario não encontrado"));
    }


}
