package com.pagamento.dto;

import com.pagamento.entity.MetodoPagamento;
import com.pagamento.entity.StatusPagamento;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pagamento.entity.Pagamento} entity
 */
public class PagamentoDto implements Serializable {
    private Integer codigoDebito;
    private String cpfCnpj;
    private MetodoPagamento metodoPagamento;
    private String numeroCartao;
    private Double valor;
    private StatusPagamento status;

    public String getNumeroCartao() {
    	String metodoPag = getMetodoPagamento().toString().toUpperCase();
    	
        if (metodoPag.equals("BOLETO") || metodoPag.equals("PIX")){
            return null;
        }
        return numeroCartao;
    }

	public Integer getCodigoDebito() {
		return codigoDebito;
	}

	public void setCodigoDebito(Integer codigoDebito) {
		this.codigoDebito = codigoDebito;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public MetodoPagamento getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public StatusPagamento getStatus() {
		return status;
	}

	public void setStatus(StatusPagamento status) {
		this.status = status;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}
	
}