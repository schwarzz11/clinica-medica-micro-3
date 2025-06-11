package br.edu.imepac.comum.services;

import br.edu.imepac.comum.models.Perfil;
import br.edu.imepac.comum.repositories.PerfilRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PerfilService {

    private final ModelMapper modelMapper;
    private final PerfilRepository perfilRepository;

    public PerfilService(ModelMapper modelMapper, PerfilRepository perfilRepository) {
        this.modelMapper = modelMapper;
        this.perfilRepository = perfilRepository;
    }

    public Perfil adicionarPerfil(Perfil perfil) {
        log.info("Cadastro de perfil - service: {}", perfil);
        return perfilRepository.save(perfil);
    }

    public Perfil atualizarPerfil(Long id, Perfil perfilAtualizado) {
        log.info("Atualizando perfil com ID: {}", id);
        Perfil perfilExistente = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
        modelMapper.map(perfilAtualizado, perfilExistente);
        return perfilRepository.save(perfilExistente);
    }

    public void removerPerfil(Long id) {
        log.info("Removendo perfil com ID: {}", id);
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
        perfilRepository.delete(perfil);
    }

    public Perfil buscarPerfilPorId(Long id) {
        log.info("Buscando perfil com ID: {}", id);
        return perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
    }

    public List<Perfil> listarPerfis() {
        log.info("Listando todos os perfis");
        return perfilRepository.findAll();
    }

    public boolean verificarAutorizacao(String usuario, String senha, String url, String metodo) {
        log.info("Verificando autorização para usuário: {}, URL: {}, Método: {}", usuario, url, metodo);
        return true;
    }
}