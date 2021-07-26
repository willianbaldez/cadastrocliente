package br.com.mvp.cadastroclienteapi.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.mvp.cadastroclienteapi.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
	Optional<Cliente> findByCpf(String cpf);

	@Query("SELECT c FROM Cliente c WHERE (:nome IS NULL OR c.nome LIKE %:nome%) AND (:cpf IS NULL OR c.cpf = :cpf) "
			+ "AND (:nascimento IS NULL OR c.nascimento = :nascimento) "
			+ "AND (:telefone IS NULL OR c.telefone = :telefone) ")
	Page<Cliente> buscar(String nome, String cpf, LocalDate nascimento, String telefone, Pageable pageable);
}
