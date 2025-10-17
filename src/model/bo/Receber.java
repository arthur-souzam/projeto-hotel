package model.bo;

import java.util.Date;

public class Receber {

    private int id;
    private Date dataEmissao;
    private Date dataVencimento;
    private float valorEmitido;
    private float valorAcrescimo;
    private float valorDesconto;
    private float valorPago;
    private Date dataPagamento;
    private char status;
    private Check check;

    public Receber() {
    }

    public Receber(int id, Date dataEmissao, Date dataVencimento, float valorEmitido, float valorAcrescimo, float valorDesconto, float valorPago, Date dataPagamento, char status, Check check) {
        this.id = id;
        this.dataEmissao = dataEmissao;
        this.dataVencimento = dataVencimento;
        this.valorEmitido = valorEmitido;
        this.valorAcrescimo = valorAcrescimo;
        this.valorDesconto = valorDesconto;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        this.status = status;
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public float getValorEmitido() {
        return valorEmitido;
    }

    public void setValorEmitido(float valorEmitido) {
        this.valorEmitido = valorEmitido;
    }

    public float getValorAcrescimo() {
        return valorAcrescimo;
    }

    public void setValorAcrescimo(float valorAcrescimo) {
        this.valorAcrescimo = valorAcrescimo;
    }

    public float getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(float valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public float getValorPago() {
        return valorPago;
    }

    public void setValorPago(float valorPago) {
        this.valorPago = valorPago;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return this.getId() + ", " + this.getValorEmitido() + ", " + this.getStatus();
    }
}