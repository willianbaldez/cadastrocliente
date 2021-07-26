package br.com.mvp.cadastroclienteapi.controller.response;

import java.time.LocalDate;
import java.time.Period;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConsultaClienteResponse {

	private String id;
	private String nome;
	private int idade;
	private LocalDate nascimento;
	private String cpf;
	private String telefone;

	public ConsultaClienteResponse(String id, String nome, LocalDate nascimento, String cpf, String telefone) {
		this.id = id;
		this.nome = nome;
		this.nascimento = nascimento;
		this.idade = calculaIdade(nascimento);
		this.cpf = cpf;
		this.telefone = telefone;
	}

	private int calculaIdade(LocalDate nascimento) {
		return Period.between(nascimento, LocalDate.now()).getYears();
	}
}
