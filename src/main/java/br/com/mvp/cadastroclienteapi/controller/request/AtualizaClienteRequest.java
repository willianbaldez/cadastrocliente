package br.com.mvp.cadastroclienteapi.controller.request;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Immutable
@AllArgsConstructor
public class AtualizaClienteRequest implements Serializable {

	private static final long serialVersionUID = -7266937797815934560L;

	@NotEmpty(message = "Campo valor é obrigatório")
	private String nome;

	@NotNull(message = "Campo nascimento é obrigatório")
	private LocalDate nascimento;

	private String cpf;

	private String telefone;

}
