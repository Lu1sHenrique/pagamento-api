package com.pagamento.repository;

import com.pagamento.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
	
	public List<Pagamento> findByCpfCnpjContains(String cpfCnpj);
	
	public List<Pagamento> findByStatusContains(String status);
}
