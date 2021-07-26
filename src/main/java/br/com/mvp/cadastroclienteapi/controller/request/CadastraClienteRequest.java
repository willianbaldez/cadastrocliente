package br.com.mvp.cadastroclienteapi.controller.request;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Immutable
@NoArgsConstructor
@AllArgsConstructor
public class CadastraClienteRequest implements Serializable {

	private static final long serialVersionUID = -7266937797815934560L;

	@NotEmpty(message = "Campo valor é obrigatório")
	private String nome;

	@NotNull(message = "Campo nascimento é obrigatório")
	private LocalDate nascimento;

	@NotEmpty(message = "Campo CPF é obrigatório")
	private String cpf;

	private String telefone;

}
