package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.paciente.PacienteDto;
import br.edu.imepac.comum.dtos.paciente.PacienteRequest;
import br.edu.imepac.comum.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<PacienteDto> save(@RequestBody PacienteRequest pacienteRequest) {
        PacienteDto novoPaciente = pacienteService.save(pacienteRequest);
        return new ResponseEntity<>(novoPaciente, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PacienteDto>> listAll() {
        return ResponseEntity.ok(pacienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> findById(@PathVariable Long id) {
        PacienteDto pacienteDto = pacienteService.findById(id);
        return ResponseEntity.ok(pacienteDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDto> update(@PathVariable Long id, @RequestBody PacienteRequest pacienteRequest) {
        PacienteDto updatedPaciente = pacienteService.update(id, pacienteRequest);
        return ResponseEntity.ok(updatedPaciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
