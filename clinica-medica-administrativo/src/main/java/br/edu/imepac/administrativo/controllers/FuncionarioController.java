package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.funcionario.FuncionarioDto;
import br.edu.imepac.comum.dtos.funcionario.FuncionarioRequest;
import br.edu.imepac.comum.services.FuncionarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioDto criarFuncionario(@RequestBody FuncionarioRequest funcionarioRequest) {
        log.info("Criando funcionário - controller: {}", funcionarioRequest);
        return funcionarioService.adicionarFuncionario(funcionarioRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuncionarioDto atualizarFuncionario(@PathVariable Long id, @RequestBody FuncionarioDto funcionarioDto) {
        log.info("Atualizar funcionário - controller: {}", funcionarioDto);
        return funcionarioService.atualizarFuncionario(id, funcionarioDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFuncionario(@PathVariable Long id) {
        log.info("Remover funcionário - controller: {}", id);
        funcionarioService.removerFuncionario(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuncionarioDto buscarFuncionarioPorId(@PathVariable Long id) {
        log.info("Buscar funcionário - controller: {}", id);
        return funcionarioService.buscarFuncionarioPorId(id);
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<FuncionarioDto> listarFuncionarios() {
        log.info("Listar funcionários - controller");
        return funcionarioService.listarFuncionarios();
    }
}