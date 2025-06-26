package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.prontuario.ProntuarioDto;
import br.edu.imepac.comum.dtos.prontuario.ProntuarioRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Consulta;
import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.models.Paciente;
import br.edu.imepac.comum.models.Prontuario;
import br.edu.imepac.comum.repositories.ConsultaRepository;
import br.edu.imepac.comum.repositories.ProntuarioRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProntuarioServiceTest {

    @Mock
    private ProntuarioRepository prontuarioRepository;

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProntuarioService prontuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProntuario() {
        // Arrange (Organizar)
        var request = new ProntuarioRequest();
        request.setConsultaId(1L);
        request.setObservacoes("Paciente está bem.");

        var paciente = new Paciente();
        paciente.setNome("Ana");

        var medico = new Funcionario();
        medico.setNome("Dr. House");

        var consulta = new Consulta();
        consulta.setId(1L);
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHorario(LocalDateTime.now());

        // Este é o objeto que o repositório retornará após salvar
        var prontuarioSalvo = new Prontuario();
        prontuarioSalvo.setId(10L); // Simula o ID gerado pelo banco
        prontuarioSalvo.setConsulta(consulta);
        prontuarioSalvo.setObservacoes(request.getObservacoes());

        // Este é o DTO base que o ModelMapper irá retornar.
        var dtoBase = new ProntuarioDto();

        // Ensinando os mocks
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(prontuarioRepository.save(any(Prontuario.class))).thenReturn(prontuarioSalvo);

        // Simula a primeira parte da conversão, feita pelo ModelMapper
        when(modelMapper.map(prontuarioSalvo, ProntuarioDto.class)).thenReturn(dtoBase);

        // Act (Agir)
        // Chamamos o método público. Ele vai internamente chamar o 'convertToDto' privado.
        ProntuarioDto result = prontuarioService.save(request);

        // Assert (Verificar)
        // Verificamos o resultado final, que é o DTO após ser modificado pelo 'convertToDto'.
        assertNotNull(result);
        assertEquals(1L, result.getConsultaId());
        assertEquals("Ana", result.getNomePaciente());
        assertEquals("Dr. House", result.getNomeMedico());

        // Verificamos se as interações com os mocks ocorreram como esperado.
        verify(prontuarioRepository, times(1)).save(any(Prontuario.class));
        verify(modelMapper, times(1)).map(prontuarioSalvo, ProntuarioDto.class);
    }
}
