package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.funcionario.FuncionarioDto;
import br.edu.imepac.comum.dtos.funcionario.FuncionarioRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.repositories.FuncionarioRepository;
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
    private ModelMapper modelMapper;

    public FuncionarioDto adicionarFuncionario(FuncionarioRequest funcionarioRequest) {
        // Futuramente, aqui entraria a lógica para encriptar a senha
        Funcionario funcionario = modelMapper.map(funcionarioRequest, Funcionario.class);
        Funcionario savedFuncionario = funcionarioRepository.save(funcionario);
        return modelMapper.map(savedFuncionario, FuncionarioDto.class);
    }

    // O seu controller está a passar um DTO aqui. O ideal seria um Request, mas vamos seguir o seu código.
    public FuncionarioDto atualizarFuncionario(Long id, FuncionarioDto funcionarioDto) {
        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Funcionário não encontrado"));

        // Mapeia os dados do DTO para a entidade existente, evitando mudar campos sensíveis
        funcionarioExistente.setNome(funcionarioDto.getNome());
        funcionarioExistente.setEmail(funcionarioDto.getEmail());
        // Adicione outros campos que podem ser atualizados

        Funcionario updatedFuncionario = funcionarioRepository.save(funcionarioExistente);
        return modelMapper.map(updatedFuncionario, FuncionarioDto.class);
    }

    public void removerFuncionario(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new NotFoundClinicaMedicaException("Funcionário não encontrado");
        }
        funcionarioRepository.deleteById(id);
    }

    public FuncionarioDto buscarFuncionarioPorId(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Funcionário não encontrado"));
        return modelMapper.map(funcionario, FuncionarioDto.class);
    }

    public List<FuncionarioDto> listarFuncionarios() {
        return funcionarioRepository.findAll().stream()
                .map(funcionario -> modelMapper.map(funcionario, FuncionarioDto.class))
                .collect(Collectors.toList());
    }
}
