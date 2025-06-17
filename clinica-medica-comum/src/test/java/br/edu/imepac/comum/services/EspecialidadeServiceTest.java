package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.especialidade.EspecialidadeDto;
import br.edu.imepac.comum.dtos.especialidade.EspecialidadeRequest;
import br.edu.imepac.comum.models.Especialidade;
import br.edu.imepac.comum.repositories.EspecialidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EspecialidadeServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private EspecialidadeRepository especialidadeRepository;

    @InjectMocks
    private EspecialidadeService especialidadeService; // Add this field

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdicionarEspecialidade() {
        // Arrange
        EspecialidadeRequest especialidadeRequest = new EspecialidadeRequest();
        Especialidade especialidade = new Especialidade();
        especialidade.setId(1L);
        especialidade.setNome("Cardiologia");

        EspecialidadeDto especialidadeDto = new EspecialidadeDto();
        especialidadeDto.setId(1L);
        especialidadeDto.setNome("Cardiologia");

        when(modelMapper.map(especialidadeRequest, Especialidade.class)).thenReturn(especialidade);
        when(especialidadeRepository.save(any(Especialidade.class))).thenReturn(especialidade);
        when(modelMapper.map(especialidade, EspecialidadeDto.class)).thenReturn(especialidadeDto);

        // Act
        EspecialidadeDto result = especialidadeService.adicionarEspecialidade(especialidadeRequest);

        // Assert
        assertEquals(especialidadeDto.getId(), result.getId());
        assertEquals(especialidadeDto.getNome(), result.getNome());
    }
}