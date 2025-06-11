package br.edu.imepac.administrativo.utils;

import br.edu.imepac.comum.services.PerfilService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
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
        String usuario = Optional.ofNullable(request.getHeader("usuario")).orElseThrow(() -> new ArithmeticException("Usuario não encontrado!"));
        String senha = Optional.ofNullable(request.getHeader("senha")).orElseThrow(() -> new ArithmeticException("Senha não encontrado!"));
        String acao = Optional.ofNullable(request.getHeader("action")).orElseThrow(() -> new ArithmeticException("Ação não encontrado!"));

        if (usuario == null || senha == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Usuário ou senha não fornecidos");
            return;
        }

        try {
            boolean autorizado = perfilService.verificarAutorizacao(usuario, senha, request.getRequestURI(), request.getMethod());
            if (!autorizado) {
                response.sendError(HttpStatus.FORBIDDEN.value(), "Usuário não autorizado");
                return;
            }
        } catch (Exception e) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro ao validar autorização");
            return;
        }

        filterChain.doFilter(request, response);
    }
}