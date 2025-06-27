package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.convenio.ConvenioDto;
import br.edu.imepac.comum.dtos.convenio.ConvenioRequest;
import br.edu.imepac.comum.exceptions.ResourceNotFoundException;
import br.edu.imepac.comum.models.Convenio;
import br.edu.imepac.comum.repositories.ConvenioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConvenioServiceTest {

    @Mock private ConvenioRepository convenioRepository;
    @Mock private ModelMapper modelMapper;
    @InjectMocks private ConvenioService convenioService;

    private Convenio convenio;

    @BeforeEach
    void setUp() {
        convenio = new Convenio();
        convenio.setId(1L);
        convenio.setNomeEmpresa("Saúde+");
        convenio.setCnpj("11222333000144");
    }

    @Test
    void testUpdate() {
        when(convenioRepository.findById(1L)).thenReturn(Optional.of(convenio));
        when(convenioRepository.save(any(Convenio.class))).thenReturn(convenio);

        ConvenioRequest updateRequest = new ConvenioRequest();
        updateRequest.setNomeEmpresa("Saúde Total");
        updateRequest.setNomeContato("Contato Novo");

        convenioService.update(1L, updateRequest);

        verify(convenioRepository).save(convenio);
        assertEquals("Saúde Total", convenio.getNomeEmpresa());
        assertEquals("Contato Novo", convenio.getNomeContato());
    }

    @Test
    void testFindById_NotFound() {
        when(convenioRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> convenioService.findById(99L));
    }
}