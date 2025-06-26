package br.edu.imepac.agendamento.controllers;

import br.edu.imepac.comum.dtos.prontuario.ProntuarioDto;
import br.edu.imepac.comum.dtos.prontuario.ProntuarioRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.services.ProntuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas a prontuários.
 * Expõe endpoints para criação, leitura, atualização e exclusão de prontuários.
 */
@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/prontuarios") // Define o caminho base para todos os endpoints neste controlador
public class ProntuarioController {

    @Autowired
    private ProntuarioService prontuarioService;

    /**
     * Retorna todos os prontuários.
     * @return Uma lista de ProntuarioDto com status HTTP 200 (OK).
     */
    @GetMapping // Mapeia requisições GET para /prontuarios
    public ResponseEntity<List<ProntuarioDto>> getAllProntuarios() {
        // Chama o serviço para obter todos os prontuários
        List<ProntuarioDto> prontuarios = prontuarioService.findAll();
        // Retorna a lista de prontuários com status OK
        return ResponseEntity.ok(prontuarios);
    }

    /**
     * Retorna um prontuário específico pelo seu ID.
     * @param id O ID do prontuário a ser buscado.
     * @return O ProntuarioDto correspondente com status HTTP 200 (OK).
     */
    @GetMapping("/{id}") // Mapeia requisições GET para /prontuarios/{id}
    public ResponseEntity<ProntuarioDto> getProntuarioById(@PathVariable Long id) {
        // Chama o serviço para obter um prontuário pelo ID
        ProntuarioDto prontuario = prontuarioService.findById(id);
        // Retorna o prontuário encontrado com status OK
        return ResponseEntity.ok(prontuario);
    }

    /**
     * Cria um novo prontuário.
     * @param request Os dados do prontuario a serem criados.
     * @return O ProntuarioDto do prontuário recém-criado com status HTTP 201 (Created).
     */
    @PostMapping // Mapeia requisições POST para /prontuarios
    public ResponseEntity<ProntuarioDto> createProntuario(@Valid @RequestBody ProntuarioRequest request) {
        // Chama o serviço para criar um novo prontuário
        ProntuarioDto createdProntuario = prontuarioService.create(request);
        // Retorna o prontuário criado com status 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProntuario);
    }

    /**
     * Atualiza um prontuário existente.
     * @param id O ID do prontuário a ser atualizado.
     * @param request Os novos dados do prontuário.
     * @return O ProntuarioDto do prontuário atualizado com status HTTP 200 (OK).
     */
    @PutMapping("/{id}") // Mapeia requisições PUT para /prontuarios/{id}
    public ResponseEntity<ProntuarioDto> updateProntuario(@PathVariable Long id, @Valid @RequestBody ProntuarioRequest request) {
        // Chama o serviço para atualizar o prontuário
        ProntuarioDto updatedProntuario = prontuarioService.update(id, request);
        // Retorna o prontuário atualizado com status OK
        return ResponseEntity.ok(updatedProntuario);
    }

    /**
     * Deleta um prontuário pelo seu ID.
     * @param id O ID do prontuário a ser deletado.
     * @return Uma resposta vazia com status HTTP 204 (No Content).
     */
    @DeleteMapping("/{id}") // Mapeia requisições DELETE para /prontuarios/{id}
    public ResponseEntity<Void> deleteProntuario(@PathVariable Long id) {
        // Chama o serviço para deletar o prontuário
        prontuarioService.delete(id);
        // Retorna uma resposta vazia com status 204 (No Content)
        return ResponseEntity.noContent().build();
    }
}
