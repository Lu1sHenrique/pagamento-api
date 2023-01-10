package com.pagamento.controller;

import com.pagamento.dto.PagamentoDto;
import com.pagamento.entity.Pagamento;
import com.pagamento.entity.StatusPagamento;
import com.pagamento.exeption.GenericException;
import com.pagamento.repository.PagamentoRepository;
import com.pagamento.service.IPagamentoServico;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController {
	
	@Autowired
	private IPagamentoServico iPagamentoServico;


    @GetMapping("/buscarTodosPagamentos")
    public ResponseEntity<List<Pagamento>> getAllPagamentos() {
    	return iPagamentoServico.getAllPagamentos();
    }

    @GetMapping("/buscarPagamentoCodigoDebito/{codigoDebito}")
    public Pagamento getPagamentoByCodigoDebito(@PathVariable Integer codigoDebito) {
    	return iPagamentoServico.getPagamentoByCodigoDebito(codigoDebito);
    }

    @GetMapping("/buscarPagamentoCpfCnpj/{cpfCnpj}")
    public ResponseEntity<List<Pagamento>> getPagamentoByCpfCnpj(@PathVariable String cpfCnpj) {
    	return iPagamentoServico.getPagamentoByCpfCnpj(cpfCnpj);
    }

    @GetMapping("/buscarPagamentoStatus/{status}")
    public ResponseEntity<List<Pagamento>> getPagamentoByStatus(@PathVariable String status) {
    	return iPagamentoServico.getPagamentoByStatus(status);
    }

    @PostMapping("/criarPagamento")
    public ResponseEntity<Pagamento>  savePagamento(@RequestBody PagamentoDto pagamentoDto) {
    	return iPagamentoServico.savePagamento(pagamentoDto);
    }

    @PostMapping("/processarPagamento/{codigoDebito}")
    public ResponseEntity<Pagamento>  processarPagamento(@PathVariable Integer codigoDebito) throws Exception  {
    	return iPagamentoServico.processarPagamento(codigoDebito);
    }

    @DeleteMapping("/deletarPagamento/{codigoDebito}")
    public ResponseEntity<String> deletePagamento(@PathVariable Integer codigoDebito) throws Exception {
    	return iPagamentoServico.deletePagamento(codigoDebito);
    }
}
