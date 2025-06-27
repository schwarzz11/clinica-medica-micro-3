package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.funcionario.FuncionarioRequest;
import br.edu.imepac.comum.exceptions.ResourceNotFoundException;
import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.models.Perfil;
import br.edu.imepac.comum.repositories.FuncionarioRepository;
import br.edu.imepac.comum.repositories.PerfilRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;
    @Mock
    private PerfilRepository perfilRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private FuncionarioService funcionarioService;

    // CORREÇÃO: O @BeforeEach com MockitoAnnotations.openMocks(this)
    // garante que todos os @Mock e @InjectMocks sejam inicializados antes de cada teste.
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdicionarFuncionario_PerfilNotFound() {
        FuncionarioRequest request = new FuncionarioRequest();
        request.setPerfilId(99L);
        when(perfilRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> funcionarioService.adicionarFuncionario(request));
    }

    @Test
    void testAtualizarFuncionario() {
        FuncionarioRequest request = new FuncionarioRequest();
        request.setEmail("emailnovo@teste.com");

        Funcionario funcionarioExistente = new Funcionario();

        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionarioExistente));
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionarioExistente);

        funcionarioService.atualizarFuncionario(1L, request);

        assertEquals("emailnovo@teste.com", funcionarioExistente.getEmail());
        verify(funcionarioRepository).save(funcionarioExistente);
    }
}
