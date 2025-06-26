package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.especialidade.EspecialidadeDto;
import br.edu.imepac.comum.dtos.especialidade.EspecialidadeRequest;
import br.edu.imepac.comum.services.EspecialidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService especialidadeService;

    @PostMapping
    public ResponseEntity<EspecialidadeDto> salvarEspecialidade(@Valid @RequestBody EspecialidadeRequest request) {
        EspecialidadeDto especialidadeSalva = especialidadeService.save(request);
        return new ResponseEntity<>(especialidadeSalva, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadeDto>> listarEspecialidades() {
        List<EspecialidadeDto> especialidades = especialidadeService.findAll();
        return ResponseEntity.ok(especialidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeDto> buscarPorId(@PathVariable Long id) {
        EspecialidadeDto especialidade = especialidadeService.findById(id);
        return ResponseEntity.ok(especialidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeDto> atualizarEspecialidade(@PathVariable Long id, @Valid @RequestBody EspecialidadeRequest request) {
        EspecialidadeDto especialidadeAtualizada = especialidadeService.update(id, request);
        return ResponseEntity.ok(especialidadeAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEspecialidade(@PathVariable Long id) {
        especialidadeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}