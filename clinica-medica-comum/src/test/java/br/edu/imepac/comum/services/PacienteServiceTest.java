package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.paciente.PacienteDto;
import br.edu.imepac.comum.dtos.paciente.PacienteRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Convenio;
import br.edu.imepac.comum.models.Paciente;
import br.edu.imepac.comum.repositories.ConvenioRepository;
import br.edu.imepac.comum.repositories.PacienteRepository;
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

class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ConvenioRepository convenioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PacienteService pacienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePaciente_WithConvenio() {
        // Arrange
        var request = new PacienteRequest();
        request.setNome("João Silva");
        request.setPossuiConvenio(true);
        request.setConvenioId(1L);

        var convenio = new Convenio();
        convenio.setId(1L);
        convenio.setNomeEmpresa("Saúde+");

        var paciente = new Paciente();
        paciente.setNome("João Silva");

        var dto = new PacienteDto();
        dto.setNome("João Silva");
        dto.setNomeConvenio("Saúde+");

        when(convenioRepository.findById(1L)).thenReturn(Optional.of(convenio));
        when(modelMapper.map(request, Paciente.class)).thenReturn(paciente);
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);
        when(modelMapper.map(paciente, PacienteDto.class)).thenReturn(dto);

        // Act
        PacienteDto result = pacienteService.save(request);

        // Assert
        assertNotNull(result);
        assertEquals("João Silva", result.getNome());
        assertEquals("Saúde+", result.getNomeConvenio());
        verify(pacienteRepository, times(1)).save(paciente);
    }

    @Test
    void testSavePaciente_WithoutConvenio() {
        // Arrange
        var request = new PacienteRequest();
        request.setNome("Maria Costa");
        request.setPossuiConvenio(false);

        var paciente = new Paciente();
        paciente.setNome("Maria Costa");

        var dto = new PacienteDto();
        dto.setNome("Maria Costa");

        when(modelMapper.map(request, Paciente.class)).thenReturn(paciente);
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);
        when(modelMapper.map(paciente, PacienteDto.class)).thenReturn(dto);

        // Act
        PacienteDto result = pacienteService.save(request);

        // Assert
        assertNotNull(result);
        assertNull(result.getNomeConvenio());
        verify(convenioRepository, never()).findById(anyLong());
    }
}
