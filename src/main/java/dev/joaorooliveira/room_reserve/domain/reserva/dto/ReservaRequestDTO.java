package dev.joaorooliveira.room_reserve.domain.reserva.dto;

import dev.joaorooliveira.room_reserve.domain.funcionario.Funcionario;
import dev.joaorooliveira.room_reserve.domain.reserva.Reserva;
import dev.joaorooliveira.room_reserve.domain.reserva.enums.StatusReservaTipo;
import dev.joaorooliveira.room_reserve.domain.sala.Sala;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaRequestDTO(

        @FutureOrPresent(message = "Data deve ser no futuro ou presente")
        @NotNull(message = "Data nao pode ser nula")
        LocalDate data,

        @NotNull(message = "Hora de inicio nao pode ser nula")
        LocalTime horaInicio,

        @NotNull(message = "Hora de fim nao pode ser nula")
        LocalTime horaFim,

        @NotNull(message = "Id do funcionario nao pode ser nulo")
        Long funcionarioId,

        @NotNull(message = "Id da sala nao pode ser nulo")
        Long salaId

) {

    public Reserva toEntity(Funcionario funcionario, Sala sala) {
        Reserva reserva = new Reserva();
        preencher(reserva,funcionario,sala);
        return reserva;
    }

    private void preencher(Reserva reserva, Funcionario funcionario, Sala sala) {
        reserva.setData(this.data);
        reserva.setHoraInicio(this.horaInicio);
        reserva.setHoraFim(this.horaFim);
        reserva.setFuncionario(funcionario);
        reserva.setSala(sala);
        reserva.setStatus(StatusReservaTipo.AGENDADA);
    }


}
