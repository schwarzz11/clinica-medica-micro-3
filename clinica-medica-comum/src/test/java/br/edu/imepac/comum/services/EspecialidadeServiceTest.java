package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.especialidade.EspecialidadeDto;
import br.edu.imepac.comum.dtos.especialidade.EspecialidadeRequest;
import br.edu.imepac.comum.models.Especialidade;
import br.edu.imepac.comum.repositories.EspecialidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EspecialidadeServiceTest {

    // **@Mock**: Cria objetos simulados (mocks) para as dependências.
    // Esses mocks substituem as implementações reais, permitindo isolar a classe que está sendo testada.
    @Mock
    private ModelMapper modelMapper;

    // **@Mock**: Cria objetos simulados (mocks) para as dependências.
    // Esses mocks substituem as implementações reais, permitindo isolar a classe que está sendo testada.
    @Mock
    private EspecialidadeRepository especialidadeRepository;

    // **@InjectMocks**: Injeta automaticamente os mocks criados nas dependências da classe que está sendo testada.
    // Isso garante que a classe seja testada de forma isolada.
    @InjectMocks
    private EspecialidadeService especialidadeService;

    // **@BeforeEach**: Indica que este método será executado antes de cada caso de teste.
    // Aqui, ele inicializa os mocks e prepara o ambiente de teste.
    @BeforeEach
    void setUp() {
        // Inicializa os mocks anotados com @Mock e injeta-os nos campos anotados com @InjectMocks.
        MockitoAnnotations.openMocks(this);
    }

    // **@Test**: Marca este método como um caso de teste.
    @Test
    void testAdicionarEspecialidade() {
        // **Criação de mocks**: Simula instâncias das classes necessárias para o teste.
        EspecialidadeRequest especialidadeRequest = Mockito.mock(EspecialidadeRequest.class);
        EspecialidadeDto especialidadeDto = Mockito.mock(EspecialidadeDto.class);
        Especialidade especialidade = Mockito.mock(Especialidade.class);

        // **Definição de comportamento (stubbing)**: Configura o comportamento dos métodos simulados.
        // Quando o método `map` do `modelMapper` for chamado, ele retornará o objeto `especialidade`.
        when(modelMapper.map(especialidadeRequest, Especialidade.class)).thenReturn(especialidade);
        when(especialidadeRepository.save(any(Especialidade.class))).thenReturn(especialidade);
        when(modelMapper.map(especialidade, EspecialidadeDto.class)).thenReturn(especialidadeDto);

        // **Ação (Act)**: Chama o método que está sendo testado.
        EspecialidadeDto result = especialidadeService.adicionarEspecialidade(especialidadeRequest);

        // **Verificação (Assert)**: Compara os resultados esperados com os resultados reais.
        // Garante que o método produza os resultados corretos.
        assertEquals(especialidadeDto.getId(), result.getId());
        assertEquals(especialidadeDto.getNome(), result.getNome());
    }
}