package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.prontuario.ProntuarioRequest;
import br.edu.imepac.comum.exceptions.ResourceNotFoundException;
import br.edu.imepac.comum.models.Consulta;
import br.edu.imepac.comum.models.Prontuario;
import br.edu.imepac.comum.repositories.ConsultaRepository;
import br.edu.imepac.comum.repositories.ProntuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProntuarioServiceTest {

    @Mock
    private ProntuarioRepository prontuarioRepository;
    @Mock
    private ConsultaRepository consultaRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private ProntuarioService prontuarioService;

    // CORREÇÃO: Este método @BeforeEach garante que os mocks
    // são inicializados antes de cada teste, resolvendo o NullPointerException.
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_ConsultaNotFound() {
        ProntuarioRequest request = new ProntuarioRequest();
        request.setConsultaId(99L);
        when(consultaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> prontuarioService.save(request));
    }

    @Test
    void testUpdate() {
        ProntuarioRequest request = new ProntuarioRequest();
        request.setObservacoes("Paciente com melhora significativa.");

        Prontuario prontuarioExistente = new Prontuario();

        when(prontuarioRepository.findById(1L)).thenReturn(Optional.of(prontuarioExistente));
        when(prontuarioRepository.save(any(Prontuario.class))).thenReturn(prontuarioExistente);

        prontuarioService.update(1L, request);

        assertEquals("Paciente com melhora significativa.", prontuarioExistente.getObservacoes());
        verify(prontuarioRepository).save(prontuarioExistente);
    }

    @Test
    void testUpdate_ProntuarioNotFound() {
        ProntuarioRequest request = new ProntuarioRequest();
        when(prontuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> prontuarioService.update(99L, request));
    }
}
