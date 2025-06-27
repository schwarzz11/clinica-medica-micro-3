package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.paciente.PacienteDto;
import br.edu.imepac.comum.dtos.paciente.PacienteRequest;
import br.edu.imepac.comum.exceptions.ResourceNotFoundException;
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

    private PacienteDto convertToDto(Paciente paciente) {
        return modelMapper.map(paciente, PacienteDto.class);
    }

    public PacienteDto save(PacienteRequest pacienteRequest) {
        Paciente paciente = modelMapper.map(pacienteRequest, Paciente.class);

        if (pacienteRequest.isPossuiConvenio() && pacienteRequest.getConvenioId() != null) {
            Convenio convenio = convenioRepository.findById(pacienteRequest.getConvenioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Convênio não encontrado"));
            paciente.setConvenio(convenio);
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
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));
        return convertToDto(paciente);
    }

    public PacienteDto update(Long id, PacienteRequest request) {
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        modelMapper.map(request, pacienteExistente);

        if (request.getConvenioId() != null) {
            Convenio convenio = convenioRepository.findById(request.getConvenioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Convênio não encontrado para atualização"));
            pacienteExistente.setConvenio(convenio);
        }

        Paciente updatedPaciente = pacienteRepository.save(pacienteExistente);
        return convertToDto(updatedPaciente);
    }

    public void delete(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente não encontrado");
        }
        pacienteRepository.deleteById(id);
    }
}