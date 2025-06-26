package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.funcionario.FuncionarioDto;
import br.edu.imepac.comum.dtos.funcionario.FuncionarioRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.models.Perfil;
import br.edu.imepac.comum.repositories.FuncionarioRepository;
import br.edu.imepac.comum.repositories.PerfilRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private ModelMapper modelMapper;

    public FuncionarioDto adicionarFuncionario(FuncionarioRequest request) {
        Perfil perfil = perfilRepository.findById(request.getPerfilId())
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Perfil não encontrado"));

        Funcionario funcionario = modelMapper.map(request, Funcionario.class);
        funcionario.setPerfil(perfil);

        Funcionario savedFuncionario = funcionarioRepository.save(funcionario);
        return modelMapper.map(savedFuncionario, FuncionarioDto.class);
    }

    // *** MÉTODO DE ATUALIZAÇÃO FINAL E CORRIGIDO ***
    public FuncionarioDto atualizarFuncionario(Long id, FuncionarioRequest request) {
        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Funcionário não encontrado"));

        // Verifica campo a campo se um novo valor foi enviado e atualiza.
        if (request.getNome() != null) {
            funcionarioExistente.setNome(request.getNome());
        }
        if (request.getEmail() != null) {
            funcionarioExistente.setEmail(request.getEmail());
        }
        if (request.getUsuario() != null) {
            funcionarioExistente.setUsuario(request.getUsuario());
        }
        // Adicione outros ifs para todos os campos que podem ser atualizados.

        // Se um novo perfilId for enviado, atualiza o relacionamento.
        if (request.getPerfilId() != null) {
            Perfil perfil = perfilRepository.findById(request.getPerfilId())
                    .orElseThrow(() -> new NotFoundClinicaMedicaException("Perfil não encontrado para atualização"));
            funcionarioExistente.setPerfil(perfil);
        }

        Funcionario updatedFuncionario = funcionarioRepository.save(funcionarioExistente);
        return modelMapper.map(updatedFuncionario, FuncionarioDto.class);
    }

    public List<FuncionarioDto> listarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        // Usamos o ModelMapper aqui, que é seguro para listas quando o DTO é simples.
        return funcionarios.stream()
                .map(funcionario -> modelMapper.map(funcionario, FuncionarioDto.class))
                .collect(Collectors.toList());
    }

    public FuncionarioDto buscarFuncionarioPorId(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Funcionário não encontrado"));
        return modelMapper.map(funcionario, FuncionarioDto.class);
    }

    public void removerFuncionario(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new NotFoundClinicaMedicaException("Funcionário não encontrado");
        }
        funcionarioRepository.deleteById(id);
    }
}
