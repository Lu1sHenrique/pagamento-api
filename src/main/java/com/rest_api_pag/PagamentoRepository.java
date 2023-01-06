package com.rest_api_pag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
	
	public List<Pagamento> findByCpfCnpjContains(String cpfCnpj);
	
	public List<Pagamento> findByStatusContains(String status);
}
