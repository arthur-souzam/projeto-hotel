package model.bo;

import java.time.LocalDateTime;

public class Check {
    private int id;
    private LocalDateTime dataHoraCadastro;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private String obs;
    private char status;
    private CheckQuarto checkQuarto;

    public Check() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getDataHoraCadastro() { return dataHoraCadastro; }
    public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) { this.dataHoraCadastro = dataHoraCadastro; }
    public LocalDateTime getDataHoraEntrada() { return dataHoraEntrada; }
    public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) { this.dataHoraEntrada = dataHoraEntrada; }
    public LocalDateTime getDataHoraSaida() { return dataHoraSaida; }
    public void setDataHoraSaida(LocalDateTime dataHoraSaida) { this.dataHoraSaida = dataHoraSaida; }
    public String getObs() { return obs; }
    public void setObs(String obs) { this.obs = obs; }
    public char getStatus() { return status; }
    public void setStatus(char status) { this.status = status; }
    public CheckQuarto getCheckQuarto() { return checkQuarto; }
    public void setCheckQuarto(CheckQuarto checkQuarto) { this.checkQuarto = checkQuarto; }
    
    @Override
    public String toString() {
        return String.valueOf(this.getId());
    }
}