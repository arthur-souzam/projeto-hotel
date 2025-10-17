package model.bo;

import java.util.Date;

public class MovimentoCaixa {

    private int id;
    private Date dataMovimento;
    private float valorMovimento;
    private String observacao;
    private char flagTipoMovimento;
    private char status;
    private Caixa caixa;
    private Receber receber;

    public MovimentoCaixa() {
    }

    public MovimentoCaixa(int id, Date dataMovimento, float valorMovimento, String observacao, char flagTipoMovimento, char status, Caixa caixa, Receber receber) {
        this.id = id;
        this.dataMovimento = dataMovimento;
        this.valorMovimento = valorMovimento;
        this.observacao = observacao;
        this.flagTipoMovimento = flagTipoMovimento;
        this.status = status;
        this.caixa = caixa;
        this.receber = receber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(Date dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public float getValorMovimento() {
        return valorMovimento;
    }

    public void setValorMovimento(float valorMovimento) {
        this.valorMovimento = valorMovimento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public char getFlagTipoMovimento() {
        return flagTipoMovimento;
    }

    public void setFlagTipoMovimento(char flagTipoMovimento) {
        this.flagTipoMovimento = flagTipoMovimento;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public Receber getReceber() {
        return receber;
    }

    public void setReceber(Receber receber) {
        this.receber = receber;
    }

    @Override
    public String toString() {
        return this.getId() + ", " + this.getValorMovimento() + ", " + this.getFlagTipoMovimento();
    }
}