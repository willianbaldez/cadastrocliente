package br.com.mvp.cadastroclienteapi.service.handler;

public class ExceptionCliente extends ExceptionObjectNotFound {
	private static final long serialVersionUID = -7194188501355758790L;

	public ExceptionCliente() {
		super("Não foi localizado cliente com os dados informada");
	}

	public ExceptionCliente(String mensagem) {
		super(mensagem);
	}

}
