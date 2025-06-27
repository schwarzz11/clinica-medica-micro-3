package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.prontuario.ProntuarioDto;
import br.edu.imepac.comum.dtos.prontuario.ProntuarioRequest;
import br.edu.imepac.comum.exceptions.ResourceNotFoundException; // Usando a exceção padronizada
import br.edu.imepac.comum.models.Consulta;
import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.models.Paciente;
import br.edu.imepac.comum.models.Prontuario;
import br.edu.imepac.comum.repositories.ConsultaRepository;
import br.edu.imepac.comum.repositories.ProntuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada"));

        Prontuario prontuario = modelMapper.map(request, Prontuario.class);
        prontuario.setConsulta(consulta);

        Prontuario savedProntuario = prontuarioRepository.save(prontuario);
        return convertToDto(savedProntuario);
    }

    public ProntuarioDto update(Long id, ProntuarioRequest request) {
        Prontuario prontuarioExistente = prontuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prontuário não encontrado com o ID: " + id));

        if (request.getReceituario() != null) prontuarioExistente.setReceituario(request.getReceituario());
        if (request.getExames() != null) prontuarioExistente.setExames(request.getExames());
        if (request.getObservacoes() != null) prontuarioExistente.setObservacoes(request.getObservacoes());

        Prontuario updatedProntuario = prontuarioRepository.save(prontuarioExistente);
        return convertToDto(updatedProntuario);
    }

    private ProntuarioDto convertToDto(Prontuario prontuario) {
        ProntuarioDto dto = modelMapper.map(prontuario, ProntuarioDto.class);
        if (prontuario.getConsulta() != null) {
            dto.setConsultaId(prontuario.getConsulta().getId());
            dto.setDataConsulta(prontuario.getConsulta().getDataHorario());

            Paciente paciente = prontuario.getConsulta().getPaciente();
            if(paciente != null) dto.setNomePaciente(paciente.getNome());

            Funcionario medico = prontuario.getConsulta().getMedico();
            if(medico != null) dto.setNomeMedico(medico.getNome());
        }
        return dto;
    }

    public List<ProntuarioDto> findAll() {
        return prontuarioRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ProntuarioDto findById(Long id) {
        Prontuario prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prontuário não encontrado"));
        return convertToDto(prontuario);
    }

    public void delete(Long id) {
        if (!prontuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Prontuário não encontrado");
        }
        prontuarioRepository.deleteById(id);
    }
}