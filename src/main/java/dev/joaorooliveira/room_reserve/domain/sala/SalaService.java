package dev.joaorooliveira.room_reserve.domain.sala;

import dev.joaorooliveira.room_reserve.domain.sala.dto.SalaRequestDTO;
import dev.joaorooliveira.room_reserve.domain.sala.dto.SalaResponseDTO;
import dev.joaorooliveira.room_reserve.infra.exception.EntidadeNaoEncontradaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Transactional
    public SalaResponseDTO salvarSala(SalaRequestDTO salaRequestDTO) {
        Sala sala = salaRepository.save(salaRequestDTO.toEntity());
        return SalaResponseDTO.fromEntity(sala);
    }

    public SalaResponseDTO buscarSalaPorId(Long id) {
        Sala sala = buscarSalaPorIdInterno(id);
        return SalaResponseDTO.fromEntity(sala);
    }

    private Sala buscarSalaPorIdInterno(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Sala não encontrada com o ID: " + id));
    }

}
