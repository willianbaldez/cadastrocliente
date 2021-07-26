package br.com.mvp.cadastroclienteapi.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mvp.cadastroclienteapi.controller.request.AtualizaClienteRequest;
import br.com.mvp.cadastroclienteapi.controller.request.CadastraClienteRequest;
import br.com.mvp.cadastroclienteapi.controller.request.ConsultaClienteRequest;
import br.com.mvp.cadastroclienteapi.controller.response.AtualizaClienteResponse;
import br.com.mvp.cadastroclienteapi.controller.response.CadastraClienteResponse;
import br.com.mvp.cadastroclienteapi.controller.response.ConsultaClienteResponse;
import br.com.mvp.cadastroclienteapi.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Cadastro de Cliente", tags = { "Cadastro de Cliente" })
@Validated
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cliente cadastrada com sucesso"),
			@ApiResponse(code = 500, message = "Ocorreu uma falha na plataforma."),
			@ApiResponse(code = 404, message = "O recurso solicitado ou o endpoint não foi encontrado."),
			@ApiResponse(code = 422, message = "Entidade não processada."),
			@ApiResponse(code = 400, message = "Solicitação Inválida") })
	public ResponseEntity<CadastraClienteResponse> incluir(@RequestBody CadastraClienteRequest clienteRequestDTO) {
		var cliente = this.clienteService.salvar(clienteRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
	}

	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cliente Atualizado com sucesso"),
			@ApiResponse(code = 204, message = "Dados não encontrados"),
			@ApiResponse(code = 500, message = "Ocorreu uma falha na plataforma."),
			@ApiResponse(code = 404, message = "O recurso solicitado ou o endpoint não foi encontrado."),
			@ApiResponse(code = 422, message = "Entidade não processada."),
			@ApiResponse(code = 400, message = "Solicitação Inválida") })
	public ResponseEntity<AtualizaClienteResponse> atualizar(@PathVariable("id") String id,
			@RequestBody AtualizaClienteRequest clienteDTO) {
		var cliente = this.clienteService.atualizar(id, clienteDTO);
		return ResponseEntity.ok(cliente);
	}

	@DeleteMapping(path = "/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cliente Excluido com sucesso"),
			@ApiResponse(code = 204, message = "Dados não encontrados"),
			@ApiResponse(code = 500, message = "Ocorreu uma falha na plataforma."),
			@ApiResponse(code = 404, message = "O recurso solicitado ou o endpoint não foi encontrado."),
			@ApiResponse(code = 422, message = "Entidade não processada."),
			@ApiResponse(code = 400, message = "Solicitação Inválida") })
	public ResponseEntity<Void> deletar(@PathVariable(name = "id") String id) {
		this.clienteService.deletar(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Consulta realizada com sucesso"),
			@ApiResponse(code = 204, message = "Dados não encontrados"),
			@ApiResponse(code = 500, message = "Ocorreu uma falha na plataforma."),
			@ApiResponse(code = 404, message = "O recurso solicitado ou o endpoint não foi encontrado."),
			@ApiResponse(code = 422, message = "Entidade não processada."),
			@ApiResponse(code = 400, message = "Solicitação Inválida") })
	public ResponseEntity<List<ConsultaClienteResponse>> buscar(
			@RequestParam(name = "nascimento", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate nascimento,
			@RequestParam(name = "nome", required = false) final String nome,
			@RequestParam(name = "telefone", required = false) final String telefone,
			@RequestParam(name = "cpf", required = false) final String cpf, Pageable pageable) {
		var request = new ConsultaClienteRequest(cpf, nome, nascimento, telefone);
		var clientes = this.clienteService.buscarCliente(request, pageable);
		if (CollectionUtils.isEmpty(clientes)) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(clientes);
		}
	}

}
