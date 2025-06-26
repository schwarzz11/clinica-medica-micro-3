package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.convenio.ConvenioDto;
import br.edu.imepac.comum.dtos.convenio.ConvenioRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Convenio;
import br.edu.imepac.comum.repositories.ConvenioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConvenioServiceTest {

    @Mock
    private ConvenioRepository convenioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ConvenioService convenioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveConvenio() {
        // Arrange (Preparação)
        var request = new ConvenioRequest();
        request.setNomeEmpresa("Saúde+");

        var convenio = new Convenio();
        convenio.setId(1L);
        convenio.setNomeEmpresa("Saúde+");

        var dto = new ConvenioDto();
        dto.setId(1L);
        dto.setNomeEmpresa("Saúde+");

        when(modelMapper.map(request, Convenio.class)).thenReturn(convenio);
        when(convenioRepository.save(any(Convenio.class))).thenReturn(convenio);
        when(modelMapper.map(convenio, ConvenioDto.class)).thenReturn(dto);

        // Act (Ação)
        ConvenioDto result = convenioService.save(request);

        // Assert (Verificação)
        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getNomeEmpresa(), result.getNomeEmpresa());
        verify(convenioRepository, times(1)).save(convenio);
    }

    @Test
    void testFindById_WhenFound() {
        // Arrange
        var convenio = new Convenio();
        convenio.setId(1L);

        var dto = new ConvenioDto();
        dto.setId(1L);

        when(convenioRepository.findById(1L)).thenReturn(Optional.of(convenio));
        when(modelMapper.map(convenio, ConvenioDto.class)).thenReturn(dto);

        // Act
        ConvenioDto result = convenioService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindById_WhenNotFound_ShouldThrowException() {
        // Arrange
        when(convenioRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundClinicaMedicaException.class, () -> {
            convenioService.findById(99L);
        });
    }

    @Test
    void testDeleteConvenio() {
        // Arrange
        Long id = 1L;
        when(convenioRepository.existsById(id)).thenReturn(true);
        doNothing().when(convenioRepository).deleteById(id);

        // Act
        convenioService.delete(id);

        // Assert
        verify(convenioRepository, times(1)).deleteById(id);
    }
}
