package dev.joaorooliveira.room_reserve.domain.reserva;

import dev.joaorooliveira.room_reserve.domain.funcionario.Funcionario;
import dev.joaorooliveira.room_reserve.domain.funcionario.FuncionarioRepository;
import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaRequestDTO;
import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaResponseDTO;
import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import dev.joaorooliveira.room_reserve.domain.sala.SalaRepository;
import dev.joaorooliveira.room_reserve.infra.exception.EntidadeNaoEncontradaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final SalaRepository salaRepository;

    public ReservaService(ReservaRepository reservaRepository, FuncionarioRepository funcionarioRepository, SalaRepository salaRepository) {
        this.reservaRepository = reservaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.salaRepository = salaRepository;
    }

    // Faltam validações de horário, disponibilidade da sala e do funcionário, etc.
    @Transactional
    public ReservaResponseDTO salvarReserva(ReservaRequestDTO reservaRequestDTO) {

        Funcionario funcionario = funcionarioRepository
                .findById(reservaRequestDTO.funcionarioId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Funcionário não encontrado"
                ));

        Sala sala = salaRepository
                .findById(reservaRequestDTO.salaId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Sala não encontrada"
                ));

        Reserva reserva = reservaRequestDTO.toEntity(funcionario, sala);

        Reserva reservaSalva = reservaRepository.save(reserva);

        return ReservaResponseDTO.fromEntity(reservaSalva);
    }

    public ReservaResponseDTO buscarReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Reserva não encontrada"
                ));

        return ReservaResponseDTO.fromEntity(reserva);
    }

}
