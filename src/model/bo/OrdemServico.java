package model.bo;

import java.util.Date;

public class OrdemServico {

    private int id;
    private Date dataHoraCadastro;
    private Date dataHoraPrevistaInicio;
    private Date dataHoraPrevistaTermino;
    private String obs;
    private char status;
    private Check check;
    private Servico servico;
    private Quarto quarto;

    public OrdemServico() {
    }

    public OrdemServico(int id, Date dataHoraCadastro, Date dataHoraPrevistaInicio, Date dataHoraPrevistaTermino, String obs, char status, Check check, Servico servico, Quarto quarto) {
        this.id = id;
        this.dataHoraCadastro = dataHoraCadastro;
        this.dataHoraPrevistaInicio = dataHoraPrevistaInicio;
        this.dataHoraPrevistaTermino = dataHoraPrevistaTermino;
        this.obs = obs;
        this.status = status;
        this.check = check;
        this.servico = servico;
        this.quarto = quarto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(Date dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public Date getDataHoraPrevistaInicio() {
        return dataHoraPrevistaInicio;
    }

    public void setDataHoraPrevistaInicio(Date dataHoraPrevistaInicio) {
        this.dataHoraPrevistaInicio = dataHoraPrevistaInicio;
    }

    public Date getDataHoraPrevistaTermino() {
        return dataHoraPrevistaTermino;
    }

    public void setDataHoraPrevistaTermino(Date dataHoraPrevistaTermino) {
        this.dataHoraPrevistaTermino = dataHoraPrevistaTermino;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
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

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    @Override
    public String toString() {
        return this.getId() + ", " + this.getServico().getDescricao() + " - " + this.getQuarto().getDescricao();
    }
}