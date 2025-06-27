package br.edu.imepac.comum.services;

import br.edu.imepac.comum.exceptions.AuthenticationClinicaMedicaException;
import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.models.Perfil;
import br.edu.imepac.comum.repositories.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PerfilServiceTest {

    @Mock private FuncionarioRepository funcionarioRepository;
    @InjectMocks private PerfilService perfilService;
    private Funcionario funcionario;

    @BeforeEach void setUp() {
        Perfil perfil = new Perfil();
        perfil.setCadastrarPaciente(true);
        funcionario = new Funcionario();
        funcionario.setSenha("senhaCorreta");
        funcionario.setPerfil(perfil);
    }

    @Test
    void testVerificarAutorizacao_Success() {
        when(funcionarioRepository.findByUsuario("user")).thenReturn(Optional.of(funcionario));
        assertTrue(perfilService.verificarAutorizacao("user", "senhaCorreta", "cadastrarPaciente"));
    }

    @Test
    void testVerificarAutorizacao_WrongPassword() {
        when(funcionarioRepository.findByUsuario("user")).thenReturn(Optional.of(funcionario));
        assertThrows(AuthenticationClinicaMedicaException.class, () -> {
            perfilService.verificarAutorizacao("user", "senhaErrada", "cadastrarPaciente");
        });
    }
}