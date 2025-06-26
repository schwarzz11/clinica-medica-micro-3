package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.prontuario.ProntuarioDto;
import br.edu.imepac.comum.dtos.prontuario.ProntuarioRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Consulta;
import br.edu.imepac.comum.models.Prontuario;
import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.models.Paciente;
import br.edu.imepac.comum.repositories.ConsultaRepository;
import br.edu.imepac.comum.repositories.ProntuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProntuarioService {

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProntuarioDto save(ProntuarioRequest request) {
        Consulta consulta = consultaRepository.findById(request.getConsultaId())
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Consulta não encontrada"));

        Prontuario prontuario = modelMapper.map(request, Prontuario.class);
        prontuario.setConsulta(consulta);

        Prontuario savedProntuario = prontuarioRepository.save(prontuario);
        return convertToDto(savedProntuario);
    }

    // *** MÉTODO DE ATUALIZAÇÃO CORRIGIDO E ROBUSTO ***
    public ProntuarioDto update(Long id, ProntuarioRequest request) {
        // 1. Busca o prontuário que já existe no banco.
        Prontuario prontuarioExistente = prontuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Prontuário não encontrado com o ID: " + id));

        // 2. Atualiza apenas os campos que foram enviados na requisição.
        if (request.getReceituario() != null) {
            prontuarioExistente.setReceituario(request.getReceituario());
        }
        if (request.getExames() != null) {
            prontuarioExistente.setExames(request.getExames());
        }
        if (request.getObservacoes() != null) {
            prontuarioExistente.setObservacoes(request.getObservacoes());
        }

        // A consulta associada não é alterada em um update.

        // 3. Salva o objeto modificado.
        Prontuario updatedProntuario = prontuarioRepository.save(prontuarioExistente);

        // 4. Retorna o DTO atualizado.
        return convertToDto(updatedProntuario);
    }

    private ProntuarioDto convertToDto(Prontuario prontuario) {
        ProntuarioDto dto = modelMapper.map(prontuario, ProntuarioDto.class);
        if (prontuario.getConsulta() != null) {
            dto.setConsultaId(prontuario.getConsulta().getId());
            dto.setDataConsulta(prontuario.getConsulta().getDataHorario());
            // Carregamento LAZY pode precisar de uma busca explícita se a sessão fechar.
            // Para garantir, podemos buscar o paciente e médico.
            Paciente paciente = prontuario.getConsulta().getPaciente();
            if(paciente != null) dto.setNomePaciente(paciente.getNome());

            Funcionario medico = prontuario.getConsulta().getMedico();
            if(medico != null) dto.setNomeMedico(medico.getNome());
        }
        return dto;
    }

    public List<ProntuarioDto> findAll() {
        return prontuarioRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProntuarioDto findById(Long id) {
        Prontuario prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Prontuário não encontrado"));
        return convertToDto(prontuario);
    }

    public void delete(Long id) {
        if (!prontuarioRepository.existsById(id)) {
            throw new NotFoundClinicaMedicaException("Prontuário não encontrado");
        }
        prontuarioRepository.deleteById(id);
    }
}
