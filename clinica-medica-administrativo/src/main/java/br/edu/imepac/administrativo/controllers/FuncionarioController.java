package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.funcionario.FuncionarioDto;
import br.edu.imepac.comum.dtos.funcionario.FuncionarioRequest;
import br.edu.imepac.comum.services.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<FuncionarioDto> criarFuncionario(@Valid @RequestBody FuncionarioRequest request) {
        FuncionarioDto funcionarioCriado = funcionarioService.adicionarFuncionario(request);
        return new ResponseEntity<>(funcionarioCriado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDto>> listarFuncionarios() {
        List<FuncionarioDto> funcionarios = funcionarioService.listarFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDto> buscarFuncionarioPorId(@PathVariable Long id) {
        FuncionarioDto funcionario = funcionarioService.buscarFuncionarioPorId(id);
        return ResponseEntity.ok(funcionario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDto> atualizarFuncionario(@PathVariable Long id, @Valid @RequestBody FuncionarioRequest request) {
        FuncionarioDto funcionarioAtualizado = funcionarioService.atualizarFuncionario(id, request);
        return ResponseEntity.ok(funcionarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFuncionario(@PathVariable Long id) {
        funcionarioService.removerFuncionario(id);
        return ResponseEntity.noContent().build();
    }
}
