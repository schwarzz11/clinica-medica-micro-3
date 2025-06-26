package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.perfil.PerfilDto;
import br.edu.imepac.comum.dtos.perfil.PerfilRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Perfil;
import br.edu.imepac.comum.repositories.PerfilRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar operações de perfil.
 * Contém a lógica de negócio para CRUD de perfis.
 */
@Service // Indica que esta classe é um componente de serviço
public class PerfilService {

    @Autowired // Injeta uma instância de PerfilRepository
    private PerfilRepository perfilRepository;

    @Autowired // Injeta uma instância de ModelMapper para mapeamento entre entidades e DTOs
    private ModelMapper modelMapper;

    /**
     * Busca todos os perfis existentes.
     *
     * @return Uma lista de PerfilDto.
     */
    public List<PerfilDto> findAll() {
        // Busca todos os perfis no banco de dados
        List<Perfil> perfis = perfilRepository.findAll();
        // Converte cada Perfil para PerfilDto e coleta em uma nova lista
        return perfis.stream()
                .map(perfil -> modelMapper.map(perfil, PerfilDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca um perfil pelo seu ID.
     *
     * @param id O ID do perfil a ser buscado.
     * @return O PerfilDto correspondente.
     * @throws NotFoundClinicaMedicaException Se o perfil não for encontrado.
     */
    public PerfilDto findById(Long id) {
        // Busca o perfil pelo ID ou lança uma exceção se não encontrado
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Perfil não encontrado com o ID: " + id));
        // Converte o perfil encontrado para PerfilDto
        return modelMapper.map(perfil, PerfilDto.class);
    }

    /**
     * Cria um novo perfil.
     *
     * @param request Os dados do perfil a serem criados.
     * @return O PerfilDto do perfil recém-criado.
     */
    public PerfilDto create(PerfilRequest request) {
        // Mapeia o PerfilRequest para a entidade Perfil
        Perfil perfil = modelMapper.map(request, Perfil.class);
        // Salva o novo perfil no banco de dados
        Perfil savedPerfil = perfilRepository.save(perfil);
        // Converte o perfil salvo para PerfilDto
        return modelMapper.map(savedPerfil, PerfilDto.class);
    }

    /**
     * Atualiza um perfil existente.
     *
     * @param id      O ID do perfil a ser atualizado.
     * @param request Os novos dados do perfil.
     * @return O PerfilDto do perfil atualizado.
     * @throws NotFoundClinicaMedicaException Se o perfil não for encontrado.
     */
    public PerfilDto update(Long id, PerfilRequest request) {
        // Busca o perfil existente ou lança uma exceção se não encontrado
        Perfil existingPerfil = perfilRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Perfil não encontrado com o ID: " + id));

        // Atualiza os campos do perfil existente com os dados da requisição
        existingPerfil.setNome(request.getNome());

        // Salva as alterações no banco de dados
        Perfil updatedPerfil = perfilRepository.save(existingPerfil);
        // Converte o perfil atualizado para PerfilDto
        return modelMapper.map(updatedPerfil, PerfilDto.class);
    }

    /**
     * Deleta um perfil pelo seu ID.
     *
     * @param id O ID do perfil a ser deletado.
     * @throws NotFoundClinicaMedicaException Se o perfil não for encontrado.
     */
    public void delete(Long id) {
        // Verifica se o perfil existe antes de deletar
        if (!perfilRepository.existsById(id)) {
            throw new NotFoundClinicaMedicaException("Perfil não encontrado com o ID: " + id);
        }
        // Deleta o perfil pelo ID
        perfilRepository.deleteById(id);
    }
}