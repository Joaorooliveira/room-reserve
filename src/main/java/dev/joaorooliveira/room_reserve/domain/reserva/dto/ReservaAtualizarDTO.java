package dev.joaorooliveira.room_reserve.domain.reserva.dto;

import dev.joaorooliveira.room_reserve.domain.reserva.Reserva;
import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaAtualizarDTO(

        @FutureOrPresent(message = "Data deve ser no futuro ou presente")
        LocalDate data,

        LocalTime horaInicio,
        LocalTime horaFim,
        @Positive(message = "Id da sala deve ser maior que zero")
        Long salaId

) {

    public void preencher(Reserva reserva, Sala sala) {
        if (this.data != null) {
            reserva.setData(this.data);
        }
        if (this.horaInicio != null) {
            reserva.setHoraInicio(this.horaInicio);
        }
        if (this.horaFim != null) {
            reserva.setHoraFim(this.horaFim);
        }
        if (this.salaId != null) {
            reserva.setSala(sala);
        }
    }

}
