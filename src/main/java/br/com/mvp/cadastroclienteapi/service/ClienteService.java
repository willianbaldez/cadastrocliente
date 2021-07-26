package br.com.mvp.cadastroclienteapi.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.com.mvp.cadastroclienteapi.controller.request.AtualizaClienteRequest;
import br.com.mvp.cadastroclienteapi.controller.request.CadastraClienteRequest;
import br.com.mvp.cadastroclienteapi.controller.request.ConsultaClienteRequest;
import br.com.mvp.cadastroclienteapi.controller.response.AtualizaClienteResponse;
import br.com.mvp.cadastroclienteapi.controller.response.CadastraClienteResponse;
import br.com.mvp.cadastroclienteapi.controller.response.ConsultaClienteResponse;
import br.com.mvp.cadastroclienteapi.model.Cliente;
import br.com.mvp.cadastroclienteapi.repository.ClienteRepository;
import br.com.mvp.cadastroclienteapi.service.handler.ExceptionCliente;
import br.com.mvp.cadastroclienteapi.service.mapper.ClienteMapper;

@Service
@Validated
public class ClienteService {

	private ClienteRepository clienteRepository;

	@Autowired
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Transactional
	public CadastraClienteResponse salvar(
			@Valid @NotNull(message = "Cliente é obrigatório") CadastraClienteRequest request) {
		var optional = clienteRepository.findByCpf(request.getCpf());

		if (optional.isEmpty()) {
			var cliente = new Cliente(request.getNome(), request.getNascimento(), request.getCpf(),
					request.getTelefone());
			clienteRepository.save(cliente);

			return new CadastraClienteResponse(cliente.getId(), cliente.getNome(), cliente.getNascimento(),
					cliente.getCpf(), cliente.getTelefone());
		} else {
			throw new ExceptionCliente("O CPF informado já foi cadastrado no sistema");
		}
	}

	@Transactional
	public AtualizaClienteResponse atualizar(String id,
			@Valid @NotNull(message = "Cliente é obrigatório") AtualizaClienteRequest request) {
		var optional = clienteRepository.findById(id);
		if (optional.isPresent()) {
			var cliente = optional.get();

			cliente = new Cliente(cliente.getId(), request.getNome(), request.getNascimento(), request.getCpf(),
					request.getTelefone());
			this.clienteRepository.save(cliente);

			return new AtualizaClienteResponse(cliente.getNome(), cliente.getNascimento(), cliente.getCpf(),
					cliente.getTelefone());
		} else {
			throw new ExceptionCliente("Cliente não encontrado para o ID informado.");
		}
	}

	@Transactional
	public void deletar(String id) {
		try {
			this.clienteRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ExceptionCliente("Cliente não encontrado para o ID informado.");
		}
	}

	@Transactional(readOnly = true)
	public List<ConsultaClienteResponse> buscarCliente(ConsultaClienteRequest request, Pageable pageable) {
		var clientes = this.clienteRepository.buscar(request.getNome(), request.getCpf(), request.getNascimento(),
				request.getTelefone(), pageable);
		return ClienteMapper.toList(clientes);
	}

}
