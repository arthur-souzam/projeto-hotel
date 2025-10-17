package model.bo;

public class CopaQuarto {

    private int id;
    private int qtdProduto;
    private float valorUnitario;
    private char status;
    private ProdutoCopa produtoCopa;
    private CheckQuarto checkQuarto;

    public CopaQuarto() {
    }

    public CopaQuarto(int id, int qtdProduto, float valorUnitario, char status, ProdutoCopa produtoCopa, CheckQuarto checkQuarto) {
        this.id = id;
        this.qtdProduto = qtdProduto;
        this.valorUnitario = valorUnitario;
        this.status = status;
        this.produtoCopa = produtoCopa;
        this.checkQuarto = checkQuarto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQtdProduto() {
        return qtdProduto;
    }

    public void setQtdProduto(int qtdProduto) {
        this.qtdProduto = qtdProduto;
    }

    public float getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public ProdutoCopa getProdutoCopa() {
        return produtoCopa;
    }

    public void setProdutoCopa(ProdutoCopa produtoCopa) {
        this.produtoCopa = produtoCopa;
    }

    public CheckQuarto getCheckQuarto() {
        return checkQuarto;
    }

    public void setCheckQuarto(CheckQuarto checkQuarto) {
        this.checkQuarto = checkQuarto;
    }

    @Override
    public String toString() {
        return this.getId() + ", " 
                + this.getProdutoCopa().getDescricao() + ", " 
                + this.getQtdProduto() + ", " 
                + this.getValorUnitario();
    }
}

