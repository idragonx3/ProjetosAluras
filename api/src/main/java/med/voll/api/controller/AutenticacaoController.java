package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.experimental.var;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.DadosVerificacaoAuth;
import med.voll.api.infra.security.TokenService;
import med.voll.api.usuario.DadosAutenticacao;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private TokenService tokenService;

	@CrossOrigin(origins = "*")
	@PostMapping
	public ResponseEntity doLogin(@RequestBody @Valid DadosAutenticacao dados) {
		var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var authentication = manager.authenticate(authToken);
		var tokenJWT = tokenService.generateToken((Usuario) authentication.getPrincipal());
		return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
	}

	@PostMapping("/validate")
	public ResponseEntity validateToken(@RequestBody DadosVerificacaoAuth dados) {
		try {
			String subject = tokenService.getSubject(dados.authToken());
			return ResponseEntity.ok("Token válido para o usuário: " + subject);
		} catch (RuntimeException e) {
			return ResponseEntity.status(401).body("Token inválido ou expirado");
		}
	}
}
