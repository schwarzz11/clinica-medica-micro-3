package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.especialidade.EspecialidadeDto;
import br.edu.imepac.comum.dtos.especialidade.EspecialidadeRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Especialidade;
import br.edu.imepac.comum.repositories.EspecialidadeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EspecialidadeDto save(EspecialidadeRequest request) {
        log.info("Salvando nova especialidade...");
        Especialidade especialidade = modelMapper.map(request, Especialidade.class);
        Especialidade savedEspecialidade = especialidadeRepository.save(especialidade);
        return modelMapper.map(savedEspecialidade, EspecialidadeDto.class);
    }

    // *** MÉTODO CORRIGIDO ***
    // Trocamos o 'stream().map().collect()' por um loop 'for' explícito.
    // É mais robusto e mais fácil de depurar.
    public List<EspecialidadeDto> findAll() {
        log.info("Buscando todas as especialidades...");
        List<Especialidade> especialidades = especialidadeRepository.findAll();
        List<EspecialidadeDto> dtos = new ArrayList<>();

        for (Especialidade especialidade : especialidades) {
            log.info("Mapeando especialidade com ID: {}", especialidade.getId());
            dtos.add(modelMapper.map(especialidade, EspecialidadeDto.class));
        }

        log.info("Mapeamento de todas as especialidades concluído.");
        return dtos;
    }

    public EspecialidadeDto findById(Long id) {
        log.info("Buscando especialidade por ID: {}", id);
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Especialidade não encontrada"));
        return modelMapper.map(especialidade, EspecialidadeDto.class);
    }

    public EspecialidadeDto update(Long id, EspecialidadeRequest request) {
        log.info("Atualizando especialidade com ID: {}", id);
        Especialidade especialidadeExistente = especialidadeRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Especialidade não encontrada"));

        especialidadeExistente.setNome(request.getNome());

        Especialidade updatedEspecialidade = especialidadeRepository.save(especialidadeExistente);
        return modelMapper.map(updatedEspecialidade, EspecialidadeDto.class);
    }

    public void delete(Long id) {
        if (!especialidadeRepository.existsById(id)) {
            throw new NotFoundClinicaMedicaException("Especialidade não encontrada");
        }
        especialidadeRepository.deleteById(id);
    }
}