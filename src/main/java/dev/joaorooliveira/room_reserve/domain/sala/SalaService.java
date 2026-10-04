package dev.joaorooliveira.room_reserve.domain.sala;

import org.springframework.stereotype.Service;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }
}
