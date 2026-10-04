package dev.joaorooliveira.room_reserve.domain.sala;

import dev.joaorooliveira.room_reserve.domain.reserva.ReservaRepository;
import dev.joaorooliveira.room_reserve.domain.sala.dto.SalaFiltroRequestDTO;
import dev.joaorooliveira.room_reserve.domain.sala.dto.SalaRequestDTO;
import dev.joaorooliveira.room_reserve.domain.sala.dto.SalaResponseDTO;
import dev.joaorooliveira.room_reserve.infra.exception.EntidadeNaoEncontradaException;
import dev.joaorooliveira.room_reserve.infra.exception.RegraNegocioException;
import dev.joaorooliveira.room_reserve.infra.specification.SalaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final ReservaRepository reservaRepository;

    public SalaService(SalaRepository salaRepository, ReservaRepository reservaRepository) {
        this.salaRepository = salaRepository;
        this.reservaRepository = reservaRepository;
    }

    @Transactional
    public SalaResponseDTO salvarSala(SalaRequestDTO salaRequestDTO) {
        Sala sala = salaRepository.save(salaRequestDTO.toEntity());
        return SalaResponseDTO.fromEntity(sala);
    }

    public Page<SalaResponseDTO> buscarSalas(SalaFiltroRequestDTO filtro, Pageable pageable) {
        return salaRepository.findAll(SalaSpecification.comFiltros(filtro), pageable)
                .map(SalaResponseDTO::fromEntity);
    }

    public SalaResponseDTO buscarSalaPorId(Long id) {
        Sala sala = buscarSalaPorIdInterno(id);
        return SalaResponseDTO.fromEntity(sala);
    }

    @Transactional
    public void deletarSala(Long id) {
        if (!salaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(
                    "Sala não encontrada com o ID: " + id
            );
        }

        if (reservaRepository.existsBySalaId(id)) {
            throw new RegraNegocioException(
                    "Não é possível deletar a sala, pois ela possui reservas associadas."
            );
        }

        salaRepository.deleteById(id);
    }

    private Sala buscarSalaPorIdInterno(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Sala não encontrada com o ID: " + id));
    }

}
