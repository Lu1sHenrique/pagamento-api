package com.rest_api_pag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest-pagamento")
public class PagamentoController {

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@GetMapping("/buscarTodosPagamentos")
	public List<Pagamento> getAllPagamentos() {
		return pagamentoRepository.findAll();
	}

	@GetMapping("/buscarPagamentoCodigoDebito/{codigoDebito}")
	public Pagamento getPagamentoByCodigoDebito(@PathVariable Integer codigoDebito) {
		return pagamentoRepository.findById(codigoDebito).get();
	}
	
	@GetMapping("/buscarPagamentoCpfCnpj/{cpfCnpj}")
	public List<Pagamento> getPagamentoByCpfCnpj(@PathVariable String cpfCnpj) {
		return pagamentoRepository.findByCpfCnpjContains(cpfCnpj);
	}
	
	@GetMapping("/buscarPagamentoStatus/{status}")
	public List<Pagamento> getPagamentoByStatus(@PathVariable String status) {
		return pagamentoRepository.findByStatusContains(status);
	}

	@PostMapping("/criarPagamento")
	public Pagamento savePagamento(@RequestBody Pagamento pagamento){
		
		String metodoPag = pagamento.getMetodoPagamento().toUpperCase();
	
		if (metodoPag.equals("BOLETO") == true || metodoPag.equals("PIX") == true) {
			pagamento.setNumeroCartao(null);
		}
		
		pagamento.setStatus("Pendente de processamento");
	
	return pagamentoRepository.save(pagamento);
	}
	
	@PostMapping("/processarPagamento/{codigoDebito}")
	public Pagamento processarPagamento(@PathVariable Integer codigoDebito) throws Exception{
		
		Pagamento pagASerPross = getPagamentoByCodigoDebito(codigoDebito);
		
		if(pagASerPross == null) {
			throw new Exception("Pagamento não encontrado");
		}
		
		if(pagASerPross.getStatus().equals("Processado com sucesso") == true) {
			throw new Exception("Pagamento ja está processado com sucesso");
		}
		else if(pagASerPross.getStatus().equals("Processado com falha") == true) {
			pagASerPross.setStatus("Pendente de processamento");
		}else {
			pagASerPross.setStatus("Processado com sucesso");
		}
	
	return pagamentoRepository.save(pagASerPross);
	}

	@DeleteMapping("/deletarPagamento/{codigoDebito}")
	public void deletePagamento(@PathVariable Integer codigoDebito) throws Exception{
		
		Pagamento pagAserDel = getPagamentoByCodigoDebito(codigoDebito);
		if(pagAserDel.getStatus().equals("Pendente de processamento") == false) {
			throw new Exception("Pagamento ja foi processado");
		}
		pagamentoRepository.deleteById(codigoDebito);
	}

}
