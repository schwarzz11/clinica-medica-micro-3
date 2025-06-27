package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.convenio.ConvenioDto;
import br.edu.imepac.comum.dtos.convenio.ConvenioRequest;
import br.edu.imepac.comum.exceptions.ResourceNotFoundException; // Usando a exceção padronizada
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
        List<Convenio> convenios = convenioRepository.findAll();
        return convenios.stream()
                .map(convenio -> modelMapper.map(convenio, ConvenioDto.class))
                .collect(Collectors.toList());
    }

    public ConvenioDto findById(Long id) {
        Convenio convenio = convenioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Convênio não encontrado!"));
        return modelMapper.map(convenio, ConvenioDto.class);
    }

    // *** MÉTODO DE ATUALIZAÇÃO CORRIGIDO ***
    // Esta implementação é mais robusta e garante que os campos sejam atualizados.
    public ConvenioDto update(Long id, ConvenioRequest request) {
        Convenio convenioExistente = convenioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Convênio não encontrado para atualização."));

        // Atualiza apenas os campos que não são nulos na requisição
        if (request.getNomeEmpresa() != null) {
            convenioExistente.setNomeEmpresa(request.getNomeEmpresa());
        }
        if (request.getCnpj() != null) {
            convenioExistente.setCnpj(request.getCnpj());
        }
        if (request.getNomeContato() != null) {
            convenioExistente.setNomeContato(request.getNomeContato());
        }
        if (request.getTelefone() != null) {
            convenioExistente.setTelefone(request.getTelefone());
        }

        Convenio updatedConvenio = convenioRepository.save(convenioExistente);
        return modelMapper.map(updatedConvenio, ConvenioDto.class);
    }

    public void delete(Long id) {
        if (!convenioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Convênio não encontrado!");
        }
        convenioRepository.deleteById(id);
    }
}
