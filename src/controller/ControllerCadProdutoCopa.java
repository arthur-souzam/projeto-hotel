package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.bo.ProdutoCopa;
import service.ProdutoCopaService;
import view.TelaBuscaProdutoCopa;
import view.TelaCadastroProdutoCopa;

public class ControllerCadProdutoCopa implements ActionListener {

    TelaCadastroProdutoCopa telaCadProdutoCopa;
    public static int codigo;

    public ControllerCadProdutoCopa(TelaCadastroProdutoCopa telaCadProdutoCopa) {
        this.telaCadProdutoCopa = telaCadProdutoCopa;

        this.telaCadProdutoCopa.getjButtonNovo().addActionListener(this);
        this.telaCadProdutoCopa.getjButtonGravar().addActionListener(this);
        this.telaCadProdutoCopa.getjButtonCancelar().addActionListener(this);
        this.telaCadProdutoCopa.getjButtonBuscar().addActionListener(this);
        this.telaCadProdutoCopa.getjButtonSair().addActionListener(this);

        utilities.Utilities.ativaDesativa(this.telaCadProdutoCopa.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadProdutoCopa.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadProdutoCopa.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), true);
            this.telaCadProdutoCopa.getjTextFieldId().setEnabled(false);

        } else if (e.getSource() == this.telaCadProdutoCopa.getjButtonGravar()) {
            ProdutoCopa produto = new ProdutoCopa();
            produto.setDescricao(this.telaCadProdutoCopa.getjTextFieldDescricao().getText());
            produto.setValor(Float.parseFloat(this.telaCadProdutoCopa.getjTextFieldValor().getText()));
            produto.setCodigoBarra(this.telaCadProdutoCopa.getjTextFieldCodigoBarra().getText());
            produto.setStatus('A');

            if (this.telaCadProdutoCopa.getjTextFieldId().getText().equalsIgnoreCase("")) {
                ProdutoCopaService.Criar(produto);
            } else {
                produto.setId(Integer.parseInt(this.telaCadProdutoCopa.getjTextFieldId().getText()));
                ProdutoCopaService.Atualizar(produto);
            }
            
            utilities.Utilities.ativaDesativa(this.telaCadProdutoCopa.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadProdutoCopa.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadProdutoCopa.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadProdutoCopa.getjButtonBuscar()) {
            codigo = 0;
            
           /* ERRO DE BOSTA TelaBuscaProdutoCopa telaBusca = new TelaBuscaProdutoCopa(null, true);
            ControllerBuscaProdutoCopa controllerBusca = new ControllerBuscaProdutoCopa(telaBusca);
            telaBusca.setVisible(true);*/

            if (codigo != 0) {
                ProdutoCopa produto = ProdutoCopaService.Carregar(codigo);
                
                utilities.Utilities.ativaDesativa(this.telaCadProdutoCopa.getjPanelBotoes(), false);
                utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), true);

                this.telaCadProdutoCopa.getjTextFieldId().setText(String.valueOf(produto.getId()));
                this.telaCadProdutoCopa.getjTextFieldDescricao().setText(produto.getDescricao());
                this.telaCadProdutoCopa.getjTextFieldValor().setText(String.valueOf(produto.getValor()));
                this.telaCadProdutoCopa.getjTextFieldCodigoBarra().setText(produto.getCodigoBarra());
                
                this.telaCadProdutoCopa.getjTextFieldId().setEnabled(false);
            }
            
        } else if (e.getSource() == this.telaCadProdutoCopa.getjButtonSair()) {
            this.telaCadProdutoCopa.dispose();
        }
    }
}