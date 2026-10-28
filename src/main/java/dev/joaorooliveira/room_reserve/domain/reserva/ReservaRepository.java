package dev.joaorooliveira.room_reserve.domain.reserva;

import dev.joaorooliveira.room_reserve.domain.reserva.enums.StatusReservaTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservaRepository extends JpaRepository<Reserva, Long>, JpaSpecificationExecutor<Reserva> {
    boolean existsByFuncionarioId(Long id);

    boolean existsBySalaId(Long salaId);

    // Verifica se existe alguma reserva para a sala em uma data específica,
    // com status AGENDADA, e que tenha horário de término após o início da nova reserva e horário de início antes
    // do término da nova reserva.
    boolean existsBySalaIdAndDataAndStatusAndHoraFimAfterAndHoraInicioBefore(
            Long salaId,
            LocalDate data,
            StatusReservaTipo status,
            LocalTime horaInicio,
            LocalTime horaFim
    );
}
