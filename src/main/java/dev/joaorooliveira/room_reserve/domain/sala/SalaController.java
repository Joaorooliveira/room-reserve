package dev.joaorooliveira.room_reserve.domain.sala;

import dev.joaorooliveira.room_reserve.domain.sala.dto.SalaAtualizarDTO;
import dev.joaorooliveira.room_reserve.domain.sala.dto.SalaFiltroRequestDTO;
import dev.joaorooliveira.room_reserve.domain.sala.dto.SalaRequestDTO;
import dev.joaorooliveira.room_reserve.domain.sala.dto.SalaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping
    public ResponseEntity<SalaResponseDTO> salvar(
            @RequestBody @Valid SalaRequestDTO salaRequestDTO) {

        SalaResponseDTO salaResponseDTO =
                salaService.salvarSala(salaRequestDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salaResponseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(salaResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<SalaResponseDTO>> buscarTodas(
            SalaFiltroRequestDTO filtro,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<SalaResponseDTO> salas =
                salaService.buscarSalas(filtro, pageable);

        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> buscarPorId(
            @PathVariable Long id) {

        SalaResponseDTO salaResponseDTO =
                salaService.buscarSalaPorId(id);

        return ResponseEntity.ok(salaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        salaService.deletarSala(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid SalaAtualizarDTO salaAtualizarDTO) {

        SalaResponseDTO salaResponseDTO =
                salaService.atualizarSala(id, salaAtualizarDTO);

        return ResponseEntity.ok(salaResponseDTO);
    }
}