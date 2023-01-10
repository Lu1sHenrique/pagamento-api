package com.pagamento.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pagamento.dto.PagamentoDto;
import com.pagamento.entity.Pagamento;
import com.pagamento.entity.StatusPagamento;
import com.pagamento.exeption.GenericException;
import com.pagamento.repository.PagamentoRepository;

@Service
public class PagamentoServico implements IPagamentoServico{

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@GetMapping("/buscarTodosPagamentos")
	public ResponseEntity<List<Pagamento>> getAllPagamentos(){
		return new ResponseEntity<>(pagamentoRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/buscarPagamentoCodigoDebito/{codigoDebito}")
	public Pagamento getPagamentoByCodigoDebito(@PathVariable Integer codigoDebito) {
		return pagamentoRepository.findById(codigoDebito).orElseThrow(() -> new GenericException("erro ao buscar debito"));
	}
	
	@GetMapping("/buscarPagamentoCpfCnpj/{cpfCnpj}")
	public ResponseEntity<List<Pagamento>> getPagamentoByCpfCnpj(@PathVariable String cpfCnpj){
		return new ResponseEntity<>(pagamentoRepository.findByCpfCnpjContains(cpfCnpj), HttpStatus.OK);
	}
	
	@GetMapping("/buscarPagamentoStatus/{status}")
	public ResponseEntity<List<Pagamento>> getPagamentoByStatus(@PathVariable String status){
		return new ResponseEntity<>(pagamentoRepository.findByStatusContains(status), HttpStatus.OK);
	}


	@PostMapping("/criarPagamento")
	public ResponseEntity<Pagamento> savePagamento(@RequestBody PagamentoDto pagamentoDto){	
		pagamentoDto.setStatus(StatusPagamento.PENDENTE);
		return new ResponseEntity<Pagamento>(pagamentoRepository.save(new ModelMapper().map(pagamentoDto, Pagamento.class)),
                HttpStatus.OK);
	}
	
	@PostMapping("/processarPagamento/{codigoDebito}")
	public ResponseEntity<Pagamento> processarPagamento(@PathVariable Integer codigoDebito) throws Exception {
		
		Pagamento pagASerPross = getPagamentoByCodigoDebito(codigoDebito);
		
		if(pagASerPross == null) {
			throw new GenericException("Pagamento não encontrado");
		}
		
		if(pagASerPross.getStatus().equals(StatusPagamento.SUCESSO)) {
			throw new GenericException("Pagamento ja está processado com sucesso");
		}
		else if(pagASerPross.getStatus().equals(StatusPagamento.FALHA)) {
			pagASerPross.setStatus(StatusPagamento.PENDENTE);
		}else {
			pagASerPross.setStatus(StatusPagamento.SUCESSO);
		}
        return new ResponseEntity<Pagamento>(pagamentoRepository.save(pagASerPross), HttpStatus.OK);
	}
	
	@DeleteMapping("/deletarPagamento/{codigoDebito}")
    public ResponseEntity<String> deletePagamento(@PathVariable Integer codigoDebito) throws Exception {
        if (!getPagamentoByCodigoDebito(codigoDebito).getStatus().equals(StatusPagamento.PENDENTE)) {
            throw new GenericException("Pagamento ja foi processado");
        }
        pagamentoRepository.deleteById(codigoDebito);
        return new ResponseEntity<>("Pagamento deletado com exito", HttpStatus.OK);
    }
}

