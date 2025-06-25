package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.paciente.PacienteDto;
import br.edu.imepac.comum.dtos.paciente.PacienteRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Convenio;
import br.edu.imepac.comum.models.Paciente;
import br.edu.imepac.comum.repositories.ConvenioRepository;
import br.edu.imepac.comum.repositories.PacienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConvenioRepository convenioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PacienteDto save(PacienteRequest pacienteRequest) {
        Paciente paciente = modelMapper.map(pacienteRequest, Paciente.class);

        if (pacienteRequest.isPossuiConvenio() && pacienteRequest.getConvenioId() != null) {
            Convenio convenio = convenioRepository.findById(pacienteRequest.getConvenioId())
                    .orElseThrow(() -> new NotFoundClinicaMedicaException("Convênio não encontrado"));
            paciente.setConvenio(convenio);
        } else {
            paciente.setConvenio(null);
        }

        Paciente savedPaciente = pacienteRepository.save(paciente);
        return convertToDto(savedPaciente);
    }

    public List<PacienteDto> findAll() {
        return pacienteRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PacienteDto findById(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Paciente não encontrado"));
        return convertToDto(paciente);
    }

    public PacienteDto update(Long id, PacienteRequest pacienteRequest) {
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Paciente não encontrado"));

        modelMapper.map(pacienteRequest, pacienteExistente);
        pacienteExistente.setId(id); // Garante que o ID não será alterado

        if (pacienteRequest.isPossuiConvenio() && pacienteRequest.getConvenioId() != null) {
            Convenio convenio = convenioRepository.findById(pacienteRequest.getConvenioId())
                    .orElseThrow(() -> new NotFoundClinicaMedicaException("Convênio não encontrado"));
            pacienteExistente.setConvenio(convenio);
        } else {
            pacienteExistente.setConvenio(null);
            pacienteExistente.setNumeroCarteirinha(null);
            pacienteExistente.setValidadeCarteirinha(null);
        }

        Paciente updatedPaciente = pacienteRepository.save(pacienteExistente);
        return convertToDto(updatedPaciente);
    }

    public void delete(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new NotFoundClinicaMedicaException("Paciente não encontrado");
        }
        pacienteRepository.deleteById(id);
    }

    // Método auxiliar para converter para DTO e lidar com o nome do convênio
    private PacienteDto convertToDto(Paciente paciente) {
        PacienteDto dto = modelMapper.map(paciente, PacienteDto.class);
        if (paciente.getConvenio() != null) {
            dto.setNomeConvenio(paciente.getConvenio().getNomeEmpresa());
        }
        return dto;
    }
}
