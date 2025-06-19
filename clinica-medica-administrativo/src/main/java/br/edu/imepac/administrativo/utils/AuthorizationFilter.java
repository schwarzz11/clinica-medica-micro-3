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

//@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    // **Injeção de Dependência**: The `PerfilService` is injected to verify user authorization.
    private final PerfilService perfilService;

    // **Construtor**: Receives the `PerfilService` dependency for initialization.
    public AuthorizationFilter(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    // **doFilterInternal**: This method intercepts HTTP requests and applies the authorization logic.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        if (!path.startsWith("/clinica-medica-administrativo/swagger-ui.html")&&
                !path.startsWith("/clinica-medica-administrativo/v3/api-docs")) {
            // **Optional.ofNullable**: Retrieves the "usuario" header or throws an exception if not found.
            String usuario = Optional.ofNullable(request.getHeader("usuario"))
                    .orElseThrow(() -> new AuthenticationClinicaMedicaException("Usuario não encontrado!"));

            // **Optional.ofNullable**: Retrieves the "senha" header or throws an exception if not found.
            String senha = Optional.ofNullable(request.getHeader("senha"))
                    .orElseThrow(() -> new AuthenticationClinicaMedicaException("Senha não encontrado!"));

            // **Optional.ofNullable**: Retrieves the "action" header or throws an exception if not found.
            String acao = Optional.ofNullable(request.getHeader("action"))
                    .orElseThrow(() -> new AuthenticationClinicaMedicaException("Ação não encontrado!"));

            // **Verificação de Autorização**: Calls the service to verify if the user is authorized.
            boolean autorizado = perfilService.verificarAutorizacao(usuario, senha, acao);
        }

        // **filterChain.doFilter**: Proceeds with the filter chain if no exception is thrown.
        filterChain.doFilter(request, response);
    }
}