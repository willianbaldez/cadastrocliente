package br.com.mvp.cadastroclienteapi.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cliente implements Serializable {

	private static final long serialVersionUID = -1516644350768086299L;

	@Id
	private String id;

	@NotNull(message = "Campo nome é obrigatório")
	private String nome;

	@NotNull(message = "Campo nascimento é obrigatório")
	private LocalDate nascimento;

	@NotNull(message = "Campo cpf é obrigatório")
	private String cpf;

	private String telefone;

	public Cliente(String nome, LocalDate nascimento, String cpf, String telefone) {
		this.id = UUID.randomUUID().toString();
		this.nome = nome;
		this.nascimento = nascimento;
		this.cpf = cpf;
		this.telefone = telefone;
	}

}
