package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.paciente.PacienteDto;
import br.edu.imepac.comum.dtos.paciente.PacienteRequest;
import br.edu.imepac.comum.services.PacienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<PacienteDto> save(@RequestBody PacienteRequest pacienteRequest) {
        log.info("CONTROLLER: Recebida requisição para criar paciente: {}", pacienteRequest.getNome());
        try {
            PacienteDto novoPaciente = pacienteService.save(pacienteRequest);
            log.info("CONTROLLER: Serviço retornou o DTO. Preparando para enviar resposta HTTP.");
            return new ResponseEntity<>(novoPaciente, HttpStatus.CREATED);
        } catch (Exception e) {
            // Se qualquer erro acontecer, ele será capturado aqui e impresso no console.
            log.error("CONTROLLER: OCORREU UM ERRO INESPERADO AO CRIAR O PACIENTE!", e);
            // Lança a exceção novamente para que o handler global possa retornar um erro 500.
            throw e;
        }
    }

    // O resto dos seus métodos (GET, PUT, DELETE) permanece igual.
    @GetMapping
    public ResponseEntity<List<PacienteDto>> listAll() {
        return ResponseEntity.ok(pacienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDto> update(@PathVariable Long id, @RequestBody PacienteRequest pacienteRequest) {
        return ResponseEntity.ok(pacienteService.update(id, pacienteRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
