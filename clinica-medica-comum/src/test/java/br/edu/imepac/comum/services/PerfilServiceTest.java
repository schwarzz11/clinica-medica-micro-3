package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.perfil.PerfilDto;
import br.edu.imepac.comum.dtos.perfil.PerfilRequest;
import br.edu.imepac.comum.models.Perfil;
import br.edu.imepac.comum.repositories.PerfilRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PerfilServiceTest {

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PerfilService perfilService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePerfil() {
        // Arrange
        var request = new PerfilRequest();
        request.setNome("ADMINISTRADOR");
        request.setCadastrarFuncionario(true);

        var perfil = new Perfil();
        perfil.setId(1L);
        perfil.setNome("ADMINISTRADOR");
        perfil.setCadastrarFuncionario(true);

        var dto = new PerfilDto();
        dto.setId(1L);
        dto.setNome("ADMINISTRADOR");
        dto.setCadastrarFuncionario(true);

        when(modelMapper.map(request, Perfil.class)).thenReturn(perfil);
        when(perfilRepository.save(any(Perfil.class))).thenReturn(perfil);
        when(modelMapper.map(perfil, PerfilDto.class)).thenReturn(dto);

        // Act
        PerfilDto result = perfilService.save(request);

        // Assert
        assertNotNull(result);
        assertTrue(result.isCadastrarFuncionario());
        assertEquals("ADMINISTRADOR", result.getNome());
        verify(perfilRepository, times(1)).save(perfil);
    }
}
