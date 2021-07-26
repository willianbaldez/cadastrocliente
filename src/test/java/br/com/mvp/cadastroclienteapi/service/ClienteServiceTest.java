package br.com.mvp.cadastroclienteapi.service;

import java.time.LocalDate;

import br.com.mvp.cadastroclienteapi.controller.response.ConsultaClienteResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.CollectionUtils;

import br.com.mvp.cadastroclienteapi.controller.request.AtualizaClienteRequest;
import br.com.mvp.cadastroclienteapi.controller.request.CadastraClienteRequest;
import br.com.mvp.cadastroclienteapi.controller.request.ConsultaClienteRequest;
import br.com.mvp.cadastroclienteapi.controller.response.CadastraClienteResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class ClienteServiceTest {

	@Autowired
	private ClienteService service;

	@Test
	void deveCadastrarCliente() {
		var cpf = "12331";
		cadastrarCliente(cpf);

		var consultaRequest = new ConsultaClienteRequest(cpf, null, null, null);
		var clientes = service.buscarCliente(consultaRequest, Pageable.unpaged());

		assertNotNull(clientes);
		assertFalse(CollectionUtils.isEmpty(clientes));

		var cliente = clientes.get(0);
		assertNotNull(cliente);
		assertNotNull(cliente.getId());
		assertEquals("Cliente Teste", cliente.getNome());
		assertEquals(LocalDate.of(2017, 5, 14), cliente.getNascimento());
		assertEquals(cpf, cliente.getCpf());
		assertEquals("1199999999", cliente.getTelefone());
		assertEquals(4, cliente.getIdade());
	}

	@Test
	void deveAtualizarCliente() {
		var cadastraClienteResponse = cadastrarCliente("12331");

		var cpf = "9999999";
		var nome = "Nome atualizado";
		var nascimento = LocalDate.now().minusDays(1);
		var telefone = "2222222";
		var atualizaRequest = new AtualizaClienteRequest(nome, nascimento, cpf, telefone);
		service.atualizar(cadastraClienteResponse.getId(), atualizaRequest);

		var consultaRequest = new ConsultaClienteRequest(cpf, null, null, null);
		var clientes = service.buscarCliente(consultaRequest, Pageable.unpaged());

		assertNotNull(clientes);
		assertFalse(CollectionUtils.isEmpty(clientes));

		var cliente = clientes.get(0);
		assertNotNull(cliente);
		assertNotNull(cliente.getId());
		assertEquals(nome, cliente.getNome());
		assertEquals(nascimento, cliente.getNascimento());
		assertEquals(cpf, cliente.getCpf());
		assertEquals(telefone, cliente.getTelefone());
	}

	@Test
	void deveExcluirCliente() {
		var cpf = "1234567890";
		var cadastraClienteResponse = cadastrarCliente(cpf);

		service.deletar(cadastraClienteResponse.getId());

		var consultaRequest = new ConsultaClienteRequest(cpf, null, null, null);
		var clientes = service.buscarCliente(consultaRequest, Pageable.unpaged());

		assertNotNull(clientes);
		assertTrue(CollectionUtils.isEmpty(clientes));
	}

	private CadastraClienteResponse cadastrarCliente(String cpf) {
		var nome = "Cliente Teste";
		var nascimento = LocalDate.of(2017, 5, 14);
		var telefone = "1199999999";

		var request = new CadastraClienteRequest(nome, nascimento, cpf, telefone);
		return service.salvar(request);
	}

}
