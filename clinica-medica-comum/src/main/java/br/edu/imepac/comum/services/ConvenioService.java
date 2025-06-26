package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.convenio.ConvenioDto;
import br.edu.imepac.comum.dtos.convenio.ConvenioRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Convenio;
import br.edu.imepac.comum.repositories.ConvenioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvenioService {

    @Autowired
    private ConvenioRepository convenioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ConvenioDto save(ConvenioRequest convenioRequest) {
        Convenio convenio = modelMapper.map(convenioRequest, Convenio.class);
        Convenio savedConvenio = convenioRepository.save(convenio);
        return modelMapper.map(savedConvenio, ConvenioDto.class);
    }

    public List<ConvenioDto> findAll() {
        return convenioRepository.findAll().stream()
                .map(convenio -> modelMapper.map(convenio, ConvenioDto.class))
                .collect(Collectors.toList());
    }

    public ConvenioDto findById(Long id) {
        // *** CORREÇÃO APLICADA AQUI ***
        // Trocamos a exceção genérica 'RuntimeException' pela nossa exceção customizada.
        // Agora, o comportamento da classe corresponde ao que o teste espera.
        Convenio convenio = convenioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Convênio não encontrado!"));
        return modelMapper.map(convenio, ConvenioDto.class);
    }

    public ConvenioDto update(Long id, ConvenioRequest convenioRequest) {
        Convenio convenioExistente = convenioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Convênio não encontrado!"));

        modelMapper.map(convenioRequest, convenioExistente);
        convenioExistente.setId(id);

        Convenio updatedConvenio = convenioRepository.save(convenioExistente);
        return modelMapper.map(updatedConvenio, ConvenioDto.class);
    }

    public void delete(Long id) {
        if (!convenioRepository.existsById(id)) {
            // Aqui já estava correto, usando a exceção customizada.
            throw new NotFoundClinicaMedicaException("Convênio não encontrado!");
        }
        convenioRepository.deleteById(id);
    }
}
