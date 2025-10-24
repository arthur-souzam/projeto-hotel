package model.bo;

import java.time.LocalDateTime;

public class ReservaQuarto {
    private int id;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private String obs;
    private char status;
    private Reserva reserva;
    private Quarto quarto;

    public ReservaQuarto() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }
    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public void setDataHoraFim(LocalDateTime dataHoraFim) { this.dataHoraFim = dataHoraFim; }
    public String getObs() { return obs; }
    public void setObs(String obs) { this.obs = obs; }
    public char getStatus() { return status; }
    public void setStatus(char status) { this.status = status; }
    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }
    public Quarto getQuarto() { return quarto; }
    public void setQuarto(Quarto quarto) { this.quarto = quarto; }
    
    @Override
    public String toString() {
        return this.getId() + " - Reserva: " + (this.reserva != null ? this.reserva.getId() : "N/A") + " Quarto: " + (this.quarto != null ? this.quarto.getIdentificacao() : "N/A");
    }
}