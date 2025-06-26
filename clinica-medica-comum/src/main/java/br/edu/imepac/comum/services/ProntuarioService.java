package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.prontuario.ProntuarioDto;
import br.edu.imepac.comum.dtos.prontuario.ProntuarioRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Paciente;
import br.edu.imepac.comum.models.Prontuario;
import br.edu.imepac.comum.repositories.PacienteRepository;
import br.edu.imepac.comum.repositories.ProntuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar operações de prontuário.
 * Contém a lógica de negócio para CRUD de prontuários.
 */
@Service // Indica que esta classe é um componente de serviço
public class ProntuarioService {

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Autowired
    private PacienteRepository pacienteRepository; // Para buscar o paciente associado

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Busca todos os prontuários existentes.
     * @return Uma lista de ProntuarioDto.
     */
    public List<ProntuarioDto> findAll() {
        // Busca todos os prontuários no banco de dados
        List<Prontuario> prontuarios = prontuarioRepository.findAll();
        // Converte cada Prontuario para ProntuarioDto e coleta em uma nova lista
        return prontuarios.stream().map(prontuario -> {
            ProntuarioDto dto = modelMapper.map(prontuario, ProntuarioDto.class);
            if (prontuario.getPaciente() != null) {
                dto.setPacienteNome(prontuario.getPaciente().getNome());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Busca um prontuário pelo seu ID.
     * @param id O ID do prontuário a ser buscado.
     * @return O ProntuarioDto correspondente.
     * @throws NotFoundClinicaMedicaException Se o prontuário não for encontrado.
     */
    public ProntuarioDto findById(Long id) {
        // Busca o prontuário pelo ID ou lança uma exceção se não encontrado
        Prontuario prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Prontuário não encontrado com o ID: " + id));

        // Converte o prontuário encontrado para ProntuarioDto
        ProntuarioDto dto = modelMapper.map(prontuario, ProntuarioDto.class);
        if (prontuario.getPaciente() != null) {
            dto.setPacienteNome(prontuario.getPaciente().getNome());
        }
        return dto;
    }

    /**
     * Cria um novo prontuário.
     * @param request Os dados do prontuário a serem criados.
     * @return O ProntuarioDto do prontuário recém-criado.
     * @throws NotFoundClinicaMedicaException Se o paciente associado não for encontrado.
     */
    public ProntuarioDto create(ProntuarioRequest request) {
        // Busca o paciente associado ao prontuário
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Paciente não encontrado com o ID: " + request.getPacienteId()));

        // Mapeia o ProntuarioRequest para a entidade Prontuario
        Prontuario prontuario = modelMapper.map(request, Prontuario.class);
        prontuario.setPaciente(paciente); // Define o paciente no prontuário

        // Salva o novo prontuário no banco de dados
        Prontuario savedProntuario = prontuarioRepository.save(prontuario);

        // Converte o prontuário salvo para ProntuarioDto
        ProntuarioDto dto = modelMapper.map(savedProntuario, ProntuarioDto.class);
        dto.setPacienteNome(paciente.getNome()); // Define o nome do paciente no DTO
        return dto;
    }

    /**
     * Atualiza um prontuário existente.
     * @param id O ID do prontuário a ser atualizado.
     * @param request Os novos dados do prontuário.
     * @return O ProntuarioDto do prontuário atualizado.
     * @throws NotFoundClinicaMedicaException Se o prontuário ou o paciente associado não for encontrado.
     */
    public ProntuarioDto update(Long id, ProntuarioRequest request) {
        // Busca o prontuário existente ou lança uma exceção se não encontrado
        Prontuario existingProntuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Prontuário não encontrado com o ID: " + id));

        // Busca o paciente associado, caso o ID do paciente seja diferente
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Paciente não encontrado com o ID: " + request.getPacienteId()));

        // Atualiza os campos do prontuário existente com os dados da requisição
        existingProntuario.setDataCriacao(request.getDataCriacao());
        existingProntuario.setHistoricoMedico(request.getHistoricoMedico());
        existingProntuario.setMedicacaoAtual(request.getMedicacaoAtual());
        existingProntuario.setAlergias(request.getAlergias());
        existingProntuario.setPaciente(paciente); // Atualiza o paciente associado

        // Salva as alterações no banco de dados
        Prontuario updatedProntuario = prontuarioRepository.save(existingProntuario);

        // Converte o prontuário atualizado para ProntuarioDto
        ProntuarioDto dto = modelMapper.map(updatedProntuario, ProntuarioDto.class);
        dto.setPacienteNome(paciente.getNome()); // Define o nome do paciente no DTO
        return dto;
    }

    /**
     * Deleta um prontuário pelo seu ID.
     * @param id O ID do prontuário a ser deletado.
     * @throws NotFoundClinicaMedicaException Se o prontuário não for encontrado.
     */
    public void delete(Long id) {
        // Verifica se o prontuário existe antes de deletar
        if (!prontuarioRepository.existsById(id)) {
            throw new NotFoundClinicaMedicaException("Prontuário não encontrado com o ID: " + id);
        }
        // Deleta o prontuário pelo ID
        prontuarioRepository.deleteById(id);
    }
}
