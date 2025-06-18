package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.especialidade.EspecialidadeDto;
import br.edu.imepac.comum.dtos.especialidade.EspecialidadeRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Especialidade;
import br.edu.imepac.comum.repositories.EspecialidadeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j // **@Slf4j**: Gera automaticamente um logger para a classe, permitindo registrar mensagens de log.
@Service // **@Service**: Indica que esta classe é um componente de serviço do Spring, responsável pela lógica de negócios.
public class EspecialidadeService {

    // **Injeção de Dependência**: O `ModelMapper` e o `EspecialidadeRepository` são injetados para manipular os dados e realizar mapeamentos.
    private ModelMapper modelMapper;
    private EspecialidadeRepository especialidadeRepository;

    // **Construtor**: Recebe as dependências necessárias para inicializar o serviço.
    public EspecialidadeService(ModelMapper modelMapper, EspecialidadeRepository especialidadeRepository) {
        this.modelMapper = modelMapper;
        this.especialidadeRepository = especialidadeRepository;
    }

    // **Método para adicionar uma especialidade**:
    // Recebe um `EspecialidadeRequest`, mapeia para o modelo, salva no repositório e retorna o DTO correspondente.
    public EspecialidadeDto adicionarEspecialidade(EspecialidadeRequest especialidadeRequest) {
        log.info("Cadadastro de especialidade - service: {}", especialidadeRequest); // **Log**: Registra informações sobre a operação.
        Especialidade especialidade = modelMapper.map(especialidadeRequest, Especialidade.class); // Mapeia o request para o modelo.
        especialidade = especialidadeRepository.save(especialidade); // Salva a especialidade no banco de dados.
        return modelMapper.map(especialidade, EspecialidadeDto.class); // Retorna o DTO criado.
    }

    // **Método para atualizar uma especialidade**:
    // Atualiza os dados de uma especialidade existente com base no ID e no DTO fornecido.
    public EspecialidadeDto atualizarEspecialidade(Long id, EspecialidadeDto especialidadeDto) {
        log.info("Atualizando especialidade com ID: {}", id); // **Log**: Registra informações sobre a operação.
        Especialidade especialidadeExistente = especialidadeRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Especialidade não encontrada com ID: " + id)); // Lança exceção se não encontrar.
        modelMapper.map(especialidadeDto, especialidadeExistente); // Atualiza os dados da especialidade existente.
        Especialidade especialidadeAtualizada = especialidadeRepository.save(especialidadeExistente); // Salva as alterações.
        return modelMapper.map(especialidadeAtualizada, EspecialidadeDto.class); // Retorna o DTO atualizado.
    }

    // **Método para remover uma especialidade**:
    // Remove uma especialidade com base no ID fornecido.
    public void removerEspecialidade(Long id) {
        log.info("Removendo especialidade com ID: {}", id); // **Log**: Registra informações sobre a operação.
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Especialidade não encontrada com ID: " + id)); // Lança exceção se não encontrar.
        especialidadeRepository.delete(especialidade); // Remove a especialidade do banco de dados.
    }

    // **Método para buscar uma especialidade por ID**:
    // Retorna o DTO de uma especialidade com base no ID fornecido.
    public EspecialidadeDto buscarEspecialidadePorId(Long id) {
        log.info("Buscando especialidade com ID: {}", id); // **Log**: Registra informações sobre a operação.
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Especialidade não encontrada com ID: " + id)); // Lança exceção se não encontrar.
        return modelMapper.map(especialidade, EspecialidadeDto.class); // Retorna o DTO correspondente.
    }

    // **Método para listar todas as especialidades**:
    // Retorna uma lista de DTOs de todas as especialidades.
    public List<EspecialidadeDto> listarEspecialidades() {
        log.info("Listando todas as especialidades"); // **Log**: Registra informações sobre a operação.
        List<Especialidade> especialidades = especialidadeRepository.findAll(); // Busca todas as especialidades no banco de dados.
        return especialidades.stream()
                .map(especialidade -> modelMapper.map(especialidade, EspecialidadeDto.class)) // Mapeia cada especialidade para um DTO.
                .toList(); // Retorna a lista de DTOs.
    }
}