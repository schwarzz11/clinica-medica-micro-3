package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.convenio.ConvenioDto;
import br.edu.imepac.comum.dtos.convenio.ConvenioRequest;
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
        // Implementar a busca e o tratamento de erro caso não encontre
        Convenio convenio = convenioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convênio não encontrado!")); // Usar sua exceção customizada aqui
        return modelMapper.map(convenio, ConvenioDto.class);
    }

    public ConvenioDto update(Long id, ConvenioRequest convenioRequest) {
        // Primeiro, verifica se o convênio existe
        Convenio convenioExistente = convenioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convênio não encontrado!")); // Usar sua exceção customizada aqui

        // Mapeia os dados do request para a entidade existente
        modelMapper.map(convenioRequest, convenioExistente);

        // Garante que o ID não será alterado
        convenioExistente.setId(id);

        Convenio updatedConvenio = convenioRepository.save(convenioExistente);
        return modelMapper.map(updatedConvenio, ConvenioDto.class);
    }

    public void delete(Long id) {
        // Verifica se existe antes de deletar para poder lançar uma exceção
        if (!convenioRepository.existsById(id)) {
            throw new RuntimeException("Convênio não encontrado!"); // Usar sua exceção customizada aqui
        }
        convenioRepository.deleteById(id);
    }
}
