package dev.joaorooliveira.room_reserve.domain.reserva;

import dev.joaorooliveira.room_reserve.domain.funcionario.Funcionario;
import dev.joaorooliveira.room_reserve.domain.funcionario.FuncionarioRepository;
import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaAtualizarDTO;
import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaFiltroRequestDTO;
import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaRequestDTO;
import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaResponseDTO;
import dev.joaorooliveira.room_reserve.domain.reserva.enums.StatusReservaTipo;
import dev.joaorooliveira.room_reserve.domain.reserva.validacoes.atualizacao.ValidadorAtualizarReserva;
import dev.joaorooliveira.room_reserve.domain.reserva.validacoes.criacao.ValidadorSalvarReserva;
import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import dev.joaorooliveira.room_reserve.domain.sala.SalaRepository;
import dev.joaorooliveira.room_reserve.infra.exception.EntidadeNaoEncontradaException;
import dev.joaorooliveira.room_reserve.infra.exception.RegraNegocioException;
import dev.joaorooliveira.room_reserve.infra.specification.ReservaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final SalaRepository salaRepository;
    private final List<ValidadorSalvarReserva> validadoresSalvarReserva;
    private final List<ValidadorAtualizarReserva> validadoresAtualizarReservas;

    public ReservaService(ReservaRepository reservaRepository,
                          FuncionarioRepository funcionarioRepository, SalaRepository salaRepository,
                          List<ValidadorSalvarReserva> validadoresSalvarReserva,
                          List<ValidadorAtualizarReserva> validadoresAtualizarReservas) {
        this.reservaRepository = reservaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.salaRepository = salaRepository;
        this.validadoresSalvarReserva = validadoresSalvarReserva;
        this.validadoresAtualizarReservas = validadoresAtualizarReservas;
    }

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
        validadoresSalvarReserva.forEach(v -> v.validar(sala, reservaRequestDTO.data(),
                reservaRequestDTO.horaInicio(), reservaRequestDTO.horaFim()));

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
        Reserva reserva = buscarReservaPorIdEntidade(id);

        return ReservaResponseDTO.fromEntity(reserva);
    }

    @Transactional
    public ReservaResponseDTO atualizarReserva(
            Long id,
            ReservaAtualizarDTO reservaAtualizarDTO
    ) {

        Reserva reserva = buscarReservaPorIdEntidade(id);

        if (reserva.getStatus() != StatusReservaTipo.AGENDADA) {
            throw new RegraNegocioException(
                    "Apenas reservas agendadas podem ser atualizadas."
            );
        }

        Sala sala = reserva.getSala();

        if (reservaAtualizarDTO.salaId() != null) {
            sala = salaRepository.findById(reservaAtualizarDTO.salaId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(
                            "Sala não encontrada"
                    ));
        }

        Sala salaFinal = sala;

        LocalDate data = reservaAtualizarDTO.data() != null
                ? reservaAtualizarDTO.data()
                : reserva.getData();

        LocalTime horaInicio = reservaAtualizarDTO.horaInicio() != null
                ? reservaAtualizarDTO.horaInicio()
                : reserva.getHoraInicio();

        LocalTime horaFim = reservaAtualizarDTO.horaFim() != null
                ? reservaAtualizarDTO.horaFim()
                : reserva.getHoraFim();

        validadoresAtualizarReservas.forEach(v -> v.validar(
                reserva,
                salaFinal,
                data,
                horaInicio,
                horaFim
        ));

        reservaAtualizarDTO.preencher(reserva, salaFinal);

        return ReservaResponseDTO.fromEntity(reserva);
    }

    // Faltam validações de horário, disponibilidade da sala e do funcionário, etc.
    @Transactional
    public void cancelarReserva(Long id) {
        Reserva reserva = buscarReservaPorIdEntidade(id);

        if (reserva.getStatus() != StatusReservaTipo.AGENDADA) {
            throw new RegraNegocioException(
                    "Apenas reservas agendadas podem ser canceladas."
            );
        }
        reserva.setStatus(StatusReservaTipo.CANCELADA);
    }

    private Reserva buscarReservaPorIdEntidade(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Reserva não encontrada"
                ));
    }

}
