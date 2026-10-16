package dev.joaorooliveira.room_reserve.domain.reserva;

import dev.joaorooliveira.room_reserve.domain.funcionario.Funcionario;
import dev.joaorooliveira.room_reserve.domain.funcionario.FuncionarioRepository;
import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaAtualizarDTO;
import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaFiltroRequestDTO;
import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaRequestDTO;
import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaResponseDTO;
import dev.joaorooliveira.room_reserve.domain.reserva.enums.StatusReservaTipo;
import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import dev.joaorooliveira.room_reserve.domain.sala.SalaRepository;
import dev.joaorooliveira.room_reserve.infra.exception.EntidadeNaoEncontradaException;
import dev.joaorooliveira.room_reserve.infra.exception.RegraNegocioException;
import dev.joaorooliveira.room_reserve.infra.specification.ReservaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final SalaRepository salaRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          FuncionarioRepository funcionarioRepository, SalaRepository salaRepository) {
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

    public Page<ReservaResponseDTO> buscarReservas(
            ReservaFiltroRequestDTO filtro,
            Pageable pageable) {

        return reservaRepository
                .findAll(ReservaSpecification.comFiltros(filtro), pageable)
                .map(ReservaResponseDTO::fromEntity);
    }

    public ReservaResponseDTO buscarReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Reserva não encontrada"
                ));

        return ReservaResponseDTO.fromEntity(reserva);
    }

    // Faltam validações de horário, disponibilidade da sala e do funcionário, etc.
    @Transactional
    public ReservaResponseDTO atualizarReserva(Long id, ReservaAtualizarDTO reservaAtualizarDTO) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Reserva não encontrada"
                ));

        Sala sala = reserva.getSala();

        if (reservaAtualizarDTO.salaId() != null) {
            sala = salaRepository.findById(reservaAtualizarDTO.salaId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(
                            "Sala não encontrada"
                    ));
        }

        reservaAtualizarDTO.preencher(reserva, sala);

        return ReservaResponseDTO.fromEntity(reserva);
    }

    // Faltam validações de horário, disponibilidade da sala e do funcionário, etc.
    @Transactional
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Reserva não encontrada"
                ));

        if (reserva.getStatus() != StatusReservaTipo.AGENDADA) {
            throw new RegraNegocioException(
                    "Apenas reservas agendadas podem ser canceladas."
            );
        }
        reserva.setStatus(StatusReservaTipo.CANCELADA);
    }


}
