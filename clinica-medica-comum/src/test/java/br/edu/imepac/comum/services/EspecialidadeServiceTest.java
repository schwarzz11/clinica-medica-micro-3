package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.especialidade.EspecialidadeDto;
import br.edu.imepac.comum.dtos.especialidade.EspecialidadeRequest;
import br.edu.imepac.comum.exceptions.ResourceNotFoundException;
import br.edu.imepac.comum.models.Especialidade;
import br.edu.imepac.comum.repositories.EspecialidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EspecialidadeServiceTest {

    @Mock
    private EspecialidadeRepository especialidadeRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private EspecialidadeService especialidadeService;

    private EspecialidadeRequest especialidadeRequest;
    private Especialidade especialidade;
    private EspecialidadeDto especialidadeDto;

    @BeforeEach
    void setUp() {
        especialidadeRequest = new EspecialidadeRequest();
        especialidadeRequest.setNome("Cardiologia");

        especialidade = new Especialidade();
        especialidade.setId(1L);
        especialidade.setNome("Cardiologia");

        especialidadeDto = new EspecialidadeDto();
        especialidadeDto.setId(1L);
        especialidadeDto.setNome("Cardiologia");
    }

    @Test
    void testSave() {
        when(modelMapper.map(especialidadeRequest, Especialidade.class)).thenReturn(especialidade);
        when(especialidadeRepository.save(any(Especialidade.class))).thenReturn(especialidade);
        when(modelMapper.map(especialidade, EspecialidadeDto.class)).thenReturn(especialidadeDto);

        EspecialidadeDto result = especialidadeService.save(especialidadeRequest);

        assertNotNull(result);
        assertEquals(especialidadeDto.getId(), result.getId());
        verify(especialidadeRepository).save(especialidade);
    }

    @Test
    void testFindById_NotFound() {
        when(especialidadeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> especialidadeService.findById(99L));
    }

    @Test
    void testUpdate() {
        when(especialidadeRepository.findById(1L)).thenReturn(Optional.of(especialidade));
        when(especialidadeRepository.save(any(Especialidade.class))).thenReturn(especialidade);

        EspecialidadeRequest updateRequest = new EspecialidadeRequest();
        updateRequest.setNome("Cardiologia Clínica");

        especialidadeService.update(1L, updateRequest);

        verify(especialidadeRepository).save(especialidade);
        assertEquals("Cardiologia Clínica", especialidade.getNome());
    }
}