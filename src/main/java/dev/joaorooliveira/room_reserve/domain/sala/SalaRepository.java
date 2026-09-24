package dev.joaorooliveira.room_reserve.domain.sala;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SalaRepository extends JpaRepository<Sala,Long> , JpaSpecificationExecutor<Sala> {
}
