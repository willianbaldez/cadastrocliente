package br.com.mvp.cadastroclienteapi.controller.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AtualizaClienteResponse {

	private String nome;
	private LocalDate nascimento;
	private String cpf;
	private String telefone;

}
