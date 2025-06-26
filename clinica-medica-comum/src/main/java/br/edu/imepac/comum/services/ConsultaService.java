package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.consulta.ConsultaDto;
import br.edu.imepac.comum.dtos.consulta.ConsultaRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Consulta;
import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.models.Paciente;
import br.edu.imepac.comum.repositories.ConsultaRepository;
import br.edu.imepac.comum.repositories.FuncionarioRepository;
import br.edu.imepac.comum.repositories.PacienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ConsultaDto save(ConsultaRequest consultaRequest) {
        // Valida se o paciente existe
        Paciente paciente = pacienteRepository.findById(consultaRequest.getPacienteId())
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Paciente não encontrado"));

        // Valida se o médico (funcionário) existe
        Funcionario medico = funcionarioRepository.findById(consultaRequest.getMedicoId())
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Médico não encontrado"));

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHorario(consultaRequest.getDataHorario());
        consulta.setSintomas(consultaRequest.getSintomas());
        consulta.setERetorno(consultaRequest.isERetorno());
        consulta.setEstaAtiva(true); // Toda nova consulta começa como ativa

        Consulta savedConsulta = consultaRepository.save(consulta);
        return convertToDto(savedConsulta);
    }

    public List<ConsultaDto> findAll() {
        return consultaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ConsultaDto findById(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Consulta não encontrada"));
        return convertToDto(consulta);
    }

    // Método para cancelar uma consulta
    public void delete(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Consulta não encontrada"));

        consulta.setEstaAtiva(false); // Apenas desativa a consulta, não a remove do banco
        consultaRepository.save(consulta);
    }

    // Método auxiliar para converter para DTO
    private ConsultaDto convertToDto(Consulta consulta) {
        ConsultaDto dto = modelMapper.map(consulta, ConsultaDto.class);
        dto.setNomePaciente(consulta.getPaciente().getNome());
        dto.setNomeMedico(consulta.getMedico().getNome());
        return dto;
    }
    public ConsultaDto update(Long id, ConsultaRequest request) {
        Consulta consultaExistente = consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Consulta não encontrada"));

        // Valida se os novos IDs de paciente e médico existem, se forem fornecidos
        if (request.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                    .orElseThrow(() -> new NotFoundClinicaMedicaException("Paciente não encontrado para atualização"));
            consultaExistente.setPaciente(paciente);
        }

        if (request.getMedicoId() != null) {
            Funcionario medico = funcionarioRepository.findById(request.getMedicoId())
                    .orElseThrow(() -> new NotFoundClinicaMedicaException("Médico não encontrado para atualização"));
            consultaExistente.setMedico(medico);
        }

        // Atualiza os outros campos se eles foram fornecidos
        if (request.getDataHorario() != null) {
            consultaExistente.setDataHorario(request.getDataHorario());
        }
        if (request.getSintomas() != null) {
            consultaExistente.setSintomas(request.getSintomas());
        }

        Consulta updatedConsulta = consultaRepository.save(consultaExistente);
        return convertToDto(updatedConsulta);
    }

}
