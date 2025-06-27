package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.especialidade.EspecialidadeDto;
import br.edu.imepac.comum.dtos.especialidade.EspecialidadeRequest;
import br.edu.imepac.comum.exceptions.ResourceNotFoundException; // Usando a exceção padronizada
import br.edu.imepac.comum.models.Especialidade;
import br.edu.imepac.comum.repositories.EspecialidadeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EspecialidadeDto save(EspecialidadeRequest request) {
        Especialidade especialidade = modelMapper.map(request, Especialidade.class);
        Especialidade savedEspecialidade = especialidadeRepository.save(especialidade);
        return modelMapper.map(savedEspecialidade, EspecialidadeDto.class);
    }

    public List<EspecialidadeDto> findAll() {
        List<Especialidade> especialidades = especialidadeRepository.findAll();
        return especialidades.stream()
                .map(especialidade -> modelMapper.map(especialidade, EspecialidadeDto.class))
                .collect(Collectors.toList());
    }

    public EspecialidadeDto findById(Long id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidade não encontrada"));
        return modelMapper.map(especialidade, EspecialidadeDto.class);
    }

    public EspecialidadeDto update(Long id, EspecialidadeRequest request) {
        Especialidade especialidadeExistente = especialidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidade não encontrada"));

        especialidadeExistente.setNome(request.getNome());

        Especialidade updatedEspecialidade = especialidadeRepository.save(especialidadeExistente);
        return modelMapper.map(updatedEspecialidade, EspecialidadeDto.class);
    }

    public void delete(Long id) {
        if (!especialidadeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Especialidade não encontrada");
        }
        especialidadeRepository.deleteById(id);
    }
}
