package model.bo;

public class CheckHospede {
    private int id;
    private String tipoHospede;
    private String obs;
    private char status;
    private Check check;
    private Hospede hospede;

    public CheckHospede() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTipoHospede() { return tipoHospede; }
    public void setTipoHospede(String tipoHospede) { this.tipoHospede = tipoHospede; }
    public String getObs() { return obs; }
    public void setObs(String obs) { this.obs = obs; }
    public char getStatus() { return status; }
    public void setStatus(char status) { this.status = status; }
    public Check getCheck() { return check; }
    public void setCheck(Check check) { this.check = check; }
    public Hospede getHospede() { return hospede; }
    public void setHospede(Hospede hospede) { this.hospede = hospede; }

     @Override
    public String toString() {
         return this.getId() + " - Check: " + (this.check != null ? this.check.getId() : "N/A") + " Hospede: " + (this.hospede != null ? this.hospede.getNome() : "N/A");
    }
}