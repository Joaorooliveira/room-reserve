package dev.joaorooliveira.room_reserve.domain.reserva;

import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaRequestDTO;
import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> salvar(@RequestBody @Valid ReservaRequestDTO reservaRequestDTO) {
        ReservaResponseDTO reservaResponseDTO = reservaService.salvarReserva(reservaRequestDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservaResponseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(reservaResponseDTO);
    }
}
