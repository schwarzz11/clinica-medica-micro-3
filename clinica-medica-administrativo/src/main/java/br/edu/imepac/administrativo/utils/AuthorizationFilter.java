package br.edu.imepac.administrativo.utils;

import br.edu.imepac.administrativo.exception.AuthenticationClinicaMedicaException;
import br.edu.imepac.comum.services.PerfilService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private final PerfilService perfilService;

    public AuthorizationFilter(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String usuario = Optional.ofNullable(request.getHeader("usuario")).orElseThrow(() -> new AuthenticationClinicaMedicaException("Usuario não encontrado!"));
        String senha = Optional.ofNullable(request.getHeader("senha")).orElseThrow(() -> new AuthenticationClinicaMedicaException("Senha não encontrado!"));
        String acao = Optional.ofNullable(request.getHeader("action")).orElseThrow(() -> new AuthenticationClinicaMedicaException("Ação não encontrado!"));

        boolean autorizado = perfilService.verificarAutorizacao(usuario, senha, acao);

        filterChain.doFilter(request, response);
    }
}