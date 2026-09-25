package dev.joaorooliveira.room_reserve.infra.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erro de Validação de DTO (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarErroValidacao(MethodArgumentNotValidException ex) {
        List<String> mensagens = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .toList();
        ErroResponse response = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), // 400
                "Erro de validação",
                mensagens
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Regra de Negócio Violada
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponse> tratarRegraNegocio(RegraNegocioException ex) {
        ErroResponse response = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), // 400
                "Erro de regra de negócio",
                List.of(ex.getMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //Recurso Não Encontrado (findById)
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErroResponse> tratarNaoEncontrado(EntidadeNaoEncontradaException ex) {
        ErroResponse response = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(), // 404
                "Recurso não encontrado",
                List.of(ex.getMessage())
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //JSON Mal Formatado
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> tratarJSONMalFormatado(HttpMessageNotReadableException ex) {
        ErroResponse response = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), // 400
                "Corpo da requisição inválido",
                List.of("Verifique se o JSON enviado está correto. Pode haver um erro de sintaxe ou tipo de dado incorreto.")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //Conflito no Banco de Dados (Ex: CPF/Email duplicado)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponse> tratarViolacaoIntegridade(DataIntegrityViolationException ex) {
        ErroResponse response = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(), // 409
                "Conflito de dados",
                List.of("A operação viola uma restrição do banco de dados (ex: registro duplicado ou dependência existente).")
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // Método HTTP Incorreto
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErroResponse> tratarMetodoNaoSuportado(HttpRequestMethodNotSupportedException ex) {
        ErroResponse response = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.METHOD_NOT_ALLOWED.value(), // 405
                "Método HTTP não suportado",
                List.of(ex.getMessage())
        );
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    //  Parâmetro de URL com tipo errado (Ex: esperava Long, recebeu String)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResponse> tratarTipoParametroInvalido(MethodArgumentTypeMismatchException ex) {
        String mensagem = String.format("O parâmetro '%s' recebeu o valor '%s', que é de um tipo inválido.", ex.getName(), ex.getValue());
        ErroResponse response = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), // 400
                "Tipo de parâmetro inválido",
                List.of(mensagem)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Parâmetro de URL obrigatório que não foi enviado
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErroResponse> tratarParametroAusente(MissingServletRequestParameterException ex) {
        String mensagem = String.format("O parâmetro de requisição '%s' é obrigatório.", ex.getParameterName());
        ErroResponse response = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), // 400
                "Parâmetro obrigatório ausente",
                List.of(mensagem)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /*

    // Acesso Negado (Usuário logado não tem permissão de Admin, por exemplo)
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ErroResponse> tratarAcessoNegado(org.springframework.security.access.AccessDeniedException ex) {
        ErroResponse response = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(), // 403
                "Acesso negado",
                List.of("Você não tem permissão para acessar este recurso.")
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    // Não Autenticado (Usuário tentou acessar rota privada sem estar logado)
    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<ErroResponse> tratarNaoAutenticado(org.springframework.security.core.AuthenticationException ex) {
        ErroResponse response = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(), // 401
                "Não autorizado",
                List.of("Você precisa estar autenticado para acessar este recurso.")
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    */

    // O CATA-TUDO (Erro Interno do Servidor)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> tratarErroInesperado(Exception ex) {
        System.out.println(ex.getMessage());
        ErroResponse response = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "Erro interno no servidor",
                List.of("Ocorreu um erro inesperado. Tente novamente mais tarde ou contate o suporte.")
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}