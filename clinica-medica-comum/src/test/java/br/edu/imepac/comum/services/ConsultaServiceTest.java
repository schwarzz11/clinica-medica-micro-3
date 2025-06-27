package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.consulta.ConsultaRequest;
import br.edu.imepac.comum.exceptions.ResourceNotFoundException;
import br.edu.imepac.comum.models.Consulta;
import br.edu.imepac.comum.repositories.ConsultaRepository;
import br.edu.imepac.comum.repositories.FuncionarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTest {

    @Mock private ConsultaRepository consultaRepository;
    @Mock private FuncionarioRepository funcionarioRepository;
    @InjectMocks private ConsultaService consultaService;

    @Test
    void testUpdate_MedicoNotFound() {
        ConsultaRequest request = new ConsultaRequest();
        request.setMedicoId(99L);
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(new Consulta()));
        when(funcionarioRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> consultaService.update(1L, request));
    }

    @Test
    void testDelete() {
        Consulta consulta = new Consulta();
        consulta.setEstaAtiva(true);

        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        consultaService.delete(1L);

        assertFalse(consulta.isEstaAtiva());
        verify(consultaRepository).save(consulta);
    }
}