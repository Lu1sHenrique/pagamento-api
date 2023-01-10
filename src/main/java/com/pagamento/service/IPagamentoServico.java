package com.pagamento.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.pagamento.dto.PagamentoDto;
import com.pagamento.entity.Pagamento;

public interface IPagamentoServico {
	
	public ResponseEntity<List<Pagamento>> getAllPagamentos();
	
	public Pagamento getPagamentoByCodigoDebito(@PathVariable Integer codigoDebito);
	
	public ResponseEntity<List<Pagamento>> getPagamentoByCpfCnpj(@PathVariable String cpfCnpj);
	
	public ResponseEntity<List<Pagamento>> getPagamentoByStatus(@PathVariable String status);
	
	public ResponseEntity<Pagamento> savePagamento(@RequestBody PagamentoDto pagamentoDto);
	
	public ResponseEntity<Pagamento> processarPagamento(@PathVariable Integer codigoDebito) throws Exception;
	
	public ResponseEntity<String> deletePagamento(@PathVariable Integer codigoDebito) throws Exception;
}
