package model.bo;

public class AlocacaoVaga {
    private int id;
    private String obs;
    private char status;
    private Veiculo veiculo;
    private Vaga vaga;
    private Check check;

    public AlocacaoVaga() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getObs() { return obs; }
    public void setObs(String obs) { this.obs = obs; }
    public char getStatus() { return status; }
    public void setStatus(char status) { this.status = status; }
    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
    public Vaga getVaga() { return vaga; }
    public void setVaga(Vaga vaga) { this.vaga = vaga; }
    public Check getCheck() { return check; }
    public void setCheck(Check check) { this.check = check; }
    
     @Override
    public String toString() {
        return this.getId() + " - Vaga: " + (this.vaga != null ? this.vaga.getDescricao() : "N/A") + " Veiculo: " + (this.veiculo != null ? this.veiculo.getPlaca() : "N/A");
    }
}