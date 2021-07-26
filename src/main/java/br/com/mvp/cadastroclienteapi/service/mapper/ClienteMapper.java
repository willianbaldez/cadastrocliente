package br.com.mvp.cadastroclienteapi.service.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.mvp.cadastroclienteapi.controller.response.ConsultaClienteResponse;
import br.com.mvp.cadastroclienteapi.model.Cliente;

public class ClienteMapper {

	private ClienteMapper() {
	}

	public static List<ConsultaClienteResponse> toList(Page<Cliente> clientes) {
		if (clientes.isEmpty()) {
			return Collections.emptyList();
		}
		return clientes.stream().map(ClienteMapper::toResponse).collect(Collectors.toList());
	}

	public static ConsultaClienteResponse toResponse(Cliente cliente) {
		if (cliente == null) {
			return null;
		}
		return new ConsultaClienteResponse(cliente.getId(), cliente.getNome(), cliente.getNascimento(),
				cliente.getCpf(), cliente.getTelefone());
	}

}
