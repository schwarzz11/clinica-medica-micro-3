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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConsultaServiceTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ConsultaService consultaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveConsulta() {
        // Arrange
        var request = new ConsultaRequest();
        request.setPacienteId(1L);
        request.setMedicoId(2L);
        request.setDataHorario(LocalDateTime.now().plusDays(1));

        var paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Carlos");

        var medico = new Funcionario();
        medico.setId(2L);
        medico.setNome("Dr. House");

        var consulta = new Consulta();
        consulta.setId(10L);
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);

        var dto = new ConsultaDto();
        dto.setId(10L);
        dto.setNomePaciente("Carlos");
        dto.setNomeMedico("Dr. House");

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(funcionarioRepository.findById(2L)).thenReturn(Optional.of(medico));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);
        when(modelMapper.map(any(Consulta.class), eq(ConsultaDto.class))).thenReturn(dto);

        // Act
        ConsultaDto result = consultaService.save(request);

        // Assert
        assertNotNull(result);
        assertEquals("Carlos", result.getNomePaciente());
        assertEquals("Dr. House", result.getNomeMedico());
        verify(consultaRepository, times(1)).save(any(Consulta.class));
    }

    @Test
    void testSaveConsulta_WhenPacienteNotFound_ShouldThrowException() {
        // Arrange
        var request = new ConsultaRequest();
        request.setPacienteId(99L); // ID inexistente
        request.setMedicoId(2L);

        when(pacienteRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundClinicaMedicaException.class, () -> {
            consultaService.save(request);
        });
    }
}
