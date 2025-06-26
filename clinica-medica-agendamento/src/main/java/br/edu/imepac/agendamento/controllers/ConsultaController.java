package br.edu.imepac.agendamento.controllers;

import br.edu.imepac.comum.dtos.consulta.ConsultaDto;
import br.edu.imepac.comum.dtos.consulta.ConsultaRequest;
import br.edu.imepac.comum.services.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<ConsultaDto> save(@RequestBody ConsultaRequest consultaRequest) {
        ConsultaDto novaConsulta = consultaService.save(consultaRequest);
        return new ResponseEntity<>(novaConsulta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ConsultaDto>> listAll() {
        return ResponseEntity.ok(consultaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDto> findById(@PathVariable Long id) {
        ConsultaDto consultaDto = consultaService.findById(id);
        return ResponseEntity.ok(consultaDto);
    }

    // O método DELETE agora representa o cancelamento da consulta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        consultaService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDto> update(@PathVariable Long id, @RequestBody ConsultaRequest request) {
        ConsultaDto consultaAtualizada = consultaService.update(id, request);
        return ResponseEntity.ok(consultaAtualizada);
    }
}
