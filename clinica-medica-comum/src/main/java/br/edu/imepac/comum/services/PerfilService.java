package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.perfil.PerfilDto;
import br.edu.imepac.comum.dtos.perfil.PerfilRequest;
import br.edu.imepac.comum.exceptions.AuthenticationClinicaMedicaException;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.models.Perfil;
import br.edu.imepac.comum.repositories.FuncionarioRepository;
import br.edu.imepac.comum.repositories.PerfilRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; // Certifique-se de que este import existe
import java.util.stream.Collectors;


@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    // Dependência necessária para a lógica de autorização
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PerfilDto save(PerfilRequest request) {
        Perfil perfil = modelMapper.map(request, Perfil.class);
        Perfil savedPerfil = perfilRepository.save(perfil);
        return modelMapper.map(savedPerfil, PerfilDto.class);
    }

    public List<PerfilDto> findAll() {
        return perfilRepository.findAll().stream()
                .map(perfil -> modelMapper.map(perfil, PerfilDto.class))
                .collect(Collectors.toList());
    }

    public PerfilDto findById(Long id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Perfil não encontrado"));
        return modelMapper.map(perfil, PerfilDto.class);
    }

    public PerfilDto update(Long id, PerfilRequest request) {
        Perfil perfilExistente = perfilRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Perfil não encontrado"));

        // ModelMapper cuida de mapear todos os campos do request para a entidade existente
        modelMapper.map(request, perfilExistente);
        perfilExistente.setId(id); // Garante que o ID não seja alterado

        Perfil updatedPerfil = perfilRepository.save(perfilExistente);
        return modelMapper.map(updatedPerfil, PerfilDto.class);
    }

    public void delete(Long id) {
        if (!perfilRepository.existsById(id)) {
            throw new NotFoundClinicaMedicaException("Perfil não encontrado");
        }
        perfilRepository.deleteById(id);
    }

    /**
     * Verifica se um usuário está autorizado a realizar uma determinada ação.
     *
     * @param usuario O nome de usuário a ser verificado.
     * @param senha A senha do usuário.
     * @param acao A ação que o usuário deseja realizar (ex: "cadastrarPaciente").
     * @return true se autorizado, false caso contrário.
     * @throws AuthenticationClinicaMedicaException se o usuário ou senha forem inválidos.
     */
    public boolean verificarAutorizacao(String usuario, String senha, String acao) {
        // CORREÇÃO 1: Adicione o método abaixo na sua INTERFACE 'FuncionarioRepository.java'
        // public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
        //     Optional<Funcionario> findByUsuario(String usuario);
        // }
        Funcionario funcionario = funcionarioRepository.findByUsuario(usuario)
                .orElseThrow(() -> new AuthenticationClinicaMedicaException("Usuário ou senha inválidos."));

        // 2. Verifica se a senha corresponde (ATENÇÃO: esta é uma verificação simples. Em produção, use bcrypt!).
        if (!funcionario.getSenha().equals(senha)) {
            throw new AuthenticationClinicaMedicaException("Usuário ou senha inválidos.");
        }

        // CORREÇÃO 2: Adicione o relacionamento abaixo na sua CLASSE 'Funcionario.java'
        // @ManyToOne
        // @JoinColumn(name = "perfil_id")
        // private Perfil perfil;
        Perfil perfil = funcionario.getPerfil();
        if (perfil == null) {
            return false; // Se não tiver perfil, não tem permissão para nada.
        }

        // 4. Verifica a permissão para a ação específica usando um switch.
        switch (acao) {
            // Casos para Paciente
            case "cadastrarPaciente": return perfil.isCadastrarPaciente();
            case "lerPaciente": return perfil.isLerPaciente();
            case "atualizarPaciente": return perfil.isAtualizarPaciente();
            case "deletarPaciente": return perfil.isDeletarPaciente();
            case "listarPaciente": return perfil.isListarPaciente();

            // Casos para Funcionário
            case "cadastrarFuncionario": return perfil.isCadastrarFuncionario();
            case "lerFuncionario": return perfil.isLerFuncionario();
            case "atualizarFuncionario": return perfil.isAtualizarFuncionario();
            case "deletarFuncionario": return perfil.isDeletarFuncionario();
            case "listarFuncionario": return perfil.isListarFuncionario();

            // Casos para Consulta
            case "cadastrarConsulta": return perfil.isCadastrarConsulta();
            case "lerConsulta": return perfil.isLerConsulta();
            case "atualizarConsulta": return perfil.isAtualizarConsulta();
            case "deletarConsulta": return perfil.isDeletarConsulta();
            case "listarConsulta": return perfil.isListarConsulta();

            // Casos para Especialidade
            case "cadastrarEspecialidade": return perfil.isCadastrarEspecialidade();
            case "lerEspecialidade": return perfil.isLerEspecialidade();
            case "atualizarEspecialidade": return perfil.isAtualizarEspecialidade();
            case "deletarEspecialidade": return perfil.isDeletarEspecialidade();
            case "listarEspecialidade": return perfil.isListarEspecialidade();

            // Casos para Convênio
            case "cadastrarConvenio": return perfil.isCadastrarConvenio();
            case "lerConvenio": return perfil.isLerConvenio();
            case "atualizarConvenio": return perfil.isAtualizarConvenio();
            case "deletarConvenio": return perfil.isDeletarConvenio();
            case "listarConvenio": return perfil.isListarConvenio();

            // Casos para Prontuário
            case "cadastrarProntuario": return perfil.isCadastrarProntuario();
            case "lerProntuario": return perfil.isLerProntuario();
            case "atualizarProntuario": return perfil.isAtualizarProntuario();
            case "deletarProntuario": return perfil.isDeletarProntuario();
            case "listarProntuario": return perfil.isListarProntuario();

            // Se a ação não for encontrada, nega a permissão.
            default:
                return false;
        }
    }
}
