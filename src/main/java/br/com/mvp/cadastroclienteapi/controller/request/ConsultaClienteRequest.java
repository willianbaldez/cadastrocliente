package br.com.mvp.cadastroclienteapi.controller.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConsultaClienteRequest {

	private String cpf;
	private String nome;
	private LocalDate nascimento;
	private String telefone;

}
