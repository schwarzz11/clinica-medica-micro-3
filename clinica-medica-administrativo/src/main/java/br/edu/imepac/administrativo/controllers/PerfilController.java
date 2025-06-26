package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.perfil.PerfilDto;
import br.edu.imepac.comum.dtos.perfil.PerfilRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.services.PerfilService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas a perfis.
 * Expõe endpoints para criação, leitura, atualização e exclusão de perfis.
 */
@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/perfis") // Define o caminho base para todos os endpoints neste controlador
public class PerfilController {

    @Autowired // Injeta uma instância de PerfilService
    private PerfilService perfilService;

    /**
     * Retorna todos os perfis existentes.
     * @return Uma lista de PerfilDto com status HTTP 200 (OK).
     */
    @GetMapping // Mapeia requisições GET para /perfis
    public ResponseEntity<List<PerfilDto>> getAllPerfis() {
        // Chama o serviço para obter todos os perfis
        List<PerfilDto> perfis = perfilService.findAll();
        // Retorna a lista de perfis com status OK
        return ResponseEntity.ok(perfis);
    }

    /**
     * Retorna um perfil específico pelo seu ID.
     * @param id O ID do perfil a ser buscado.
     * @return O PerfilDto correspondente com status HTTP 200 (OK).
     * @throws NotFoundClinicaMedicaException Se o perfil não for encontrado.
     */
    @GetMapping("/{id}") // Mapeia requisições GET para /perfis/{id}
    public ResponseEntity<PerfilDto> getPerfilById(@PathVariable Long id) {
        // Chama o serviço para obter um perfil pelo ID
        PerfilDto perfil = perfilService.findById(id);
        // Retorna o perfil encontrado com status OK
        return ResponseEntity.ok(perfil);
    }

    /**
     * Cria um novo perfil.
     * @param request Os dados do perfil a serem criados.
     * @return O PerfilDto do perfil recém-criado com status HTTP 201 (Created).
     */
    @PostMapping // Mapeia requisições POST para /perfis
    public ResponseEntity<PerfilDto> createPerfil(@Valid @RequestBody PerfilRequest request) {
        // *** CORREÇÃO APLICADA AQUI ***
        // O nome do método no serviço é 'save', não 'create'.
        PerfilDto createdPerfil = perfilService.save(request);
        // Retorna o perfil criado com status 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerfil);
    }

    /**
     * Atualiza um perfil existente.
     * @param id O ID do perfil a ser atualizado.
     * @param request Os novos dados do perfil.
     * @return O PerfilDto do perfil atualizado com status HTTP 200 (OK).
     * @throws NotFoundClinicaMedicaException Se o perfil não for encontrado.
     */
    @PutMapping("/{id}") // Mapeia requisições PUT para /perfis/{id}
    public ResponseEntity<PerfilDto> updatePerfil(@PathVariable Long id, @Valid @RequestBody PerfilRequest request) {
        // Chama o serviço para atualizar o perfil
        PerfilDto updatedPerfil = perfilService.update(id, request);
        // Retorna o perfil atualizado com status OK
        return ResponseEntity.ok(updatedPerfil);
    }

    /**
     * Deleta um perfil pelo seu ID.
     * @param id O ID do perfil a ser deletado.
     * @return Uma resposta vazia com status HTTP 204 (No Content).
     * @throws NotFoundClinicaMedicaException Se o perfil não for encontrado.
     */
    @DeleteMapping("/{id}") // Mapeia requisições DELETE para /perfis/{id}
    public ResponseEntity<Void> deletePerfil(@PathVariable Long id) {
        // Chama o serviço para deletar o perfil
        perfilService.delete(id);
        // Retorna uma resposta vazia com status 204 (No Content)
        return ResponseEntity.noContent().build();
    }
}
