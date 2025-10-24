package model.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva {
    private int id;
    private LocalDateTime dataHoraReserva;
    private LocalDate dataPrevistaEntrada;
    private LocalDate dataPrevistaSaida;
    private String obs;
    private char status;
    private Check check;

    public Reserva() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getDataHoraReserva() { return dataHoraReserva; }
    public void setDataHoraReserva(LocalDateTime dataHoraReserva) { this.dataHoraReserva = dataHoraReserva; }
    public LocalDate getDataPrevistaEntrada() { return dataPrevistaEntrada; }
    public void setDataPrevistaEntrada(LocalDate dataPrevistaEntrada) { this.dataPrevistaEntrada = dataPrevistaEntrada; }
    public LocalDate getDataPrevistaSaida() { return dataPrevistaSaida; }
    public void setDataPrevistaSaida(LocalDate dataPrevistaSaida) { this.dataPrevistaSaida = dataPrevistaSaida; }
    public String getObs() { return obs; }
    public void setObs(String obs) { this.obs = obs; }
    public char getStatus() { return status; }
    public void setStatus(char status) { this.status = status; }
    public Check getCheck() { return check; }
    public void setCheck(Check check) { this.check = check; }

    @Override
    public String toString() {
        return String.valueOf(this.getId());
    }
}