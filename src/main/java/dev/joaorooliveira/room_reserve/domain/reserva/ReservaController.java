package dev.joaorooliveira.room_reserve.domain.reserva;

import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaFiltroRequestDTO;
import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaRequestDTO;
import dev.joaorooliveira.room_reserve.domain.reserva.dto.ReservaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public ResponseEntity<Page<ReservaResponseDTO>> buscar(@ModelAttribute ReservaFiltroRequestDTO filtro,
                                                           @PageableDefault(size = 10) Pageable pageable) {
        Page<ReservaResponseDTO> reservas = reservaService.buscarReservas(filtro, pageable);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarPorId(@PathVariable Long id) {
        ReservaResponseDTO reservaResponseDTO = reservaService.buscarReservaPorId(id);
        return ResponseEntity.ok(reservaResponseDTO);
    }

}
