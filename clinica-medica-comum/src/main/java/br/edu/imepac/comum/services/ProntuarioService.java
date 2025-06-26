package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.prontuario.ProntuarioDto;
import br.edu.imepac.comum.dtos.prontuario.ProntuarioRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Consulta;
import br.edu.imepac.comum.models.Prontuario;
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

        Prontuario prontuario = new Prontuario();
        prontuario.setConsulta(consulta);
        prontuario.setReceituario(request.getReceituario());
        prontuario.setExames(request.getExames());
        prontuario.setObservacoes(request.getObservacoes());

        Prontuario savedProntuario = prontuarioRepository.save(prontuario);
        return convertToDto(savedProntuario);
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

    public ProntuarioDto update(Long id, ProntuarioRequest request) {
        Prontuario prontuarioExistente = prontuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Prontuário não encontrado"));

        // Valida se a consulta associada na requisição de update existe
        consultaRepository.findById(request.getConsultaId())
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Consulta não encontrada para atualização"));

        modelMapper.map(request, prontuarioExistente);
        // Garante que o ID e a consulta original não sejam alterados no update.
        // Se a regra de negócio permitir trocar a consulta de um prontuário, essa lógica mudaria.
        prontuarioExistente.setId(id);

        Prontuario updatedProntuario = prontuarioRepository.save(prontuarioExistente);
        return convertToDto(updatedProntuario);
    }

    public void delete(Long id) {
        if (!prontuarioRepository.existsById(id)) {
            throw new NotFoundClinicaMedicaException("Prontuário não encontrado");
        }
        prontuarioRepository.deleteById(id);
    }

    private ProntuarioDto convertToDto(Prontuario prontuario) {
        ProntuarioDto dto = modelMapper.map(prontuario, ProntuarioDto.class);
        if (prontuario.getConsulta() != null) {
            dto.setConsultaId(prontuario.getConsulta().getId());
            dto.setDataConsulta(prontuario.getConsulta().getDataHorario());
            dto.setNomePaciente(prontuario.getConsulta().getPaciente().getNome());
            dto.setNomeMedico(prontuario.getConsulta().getMedico().getNome());
        }
        return dto;
    }
}
