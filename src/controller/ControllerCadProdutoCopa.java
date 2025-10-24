package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
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

        ativaDesativa(true);
        utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadProdutoCopa.getjButtonNovo()) {
            ativaDesativa(false);
            utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), true);
            this.telaCadProdutoCopa.getjTextFieldId().setEnabled(false);

        } else if (e.getSource() == this.telaCadProdutoCopa.getjButtonGravar()) {
            String descricao = this.telaCadProdutoCopa.getjTextFieldDescricao().getText();
            String valorStr = this.telaCadProdutoCopa.getjTextFieldValor().getText();
            String codBarra = this.telaCadProdutoCopa.getjTextFieldCodigoBarra().getText();
            float valor = 0;

            if (descricao.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadProdutoCopa, "A descrição é obrigatória!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }
             if (valorStr.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadProdutoCopa, "O valor é obrigatório!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }
             try {
                 valor = Float.parseFloat(valorStr.replace(",", "."));
                 if (valor <= 0) {
                     JOptionPane.showMessageDialog(this.telaCadProdutoCopa, "O valor deve ser positivo!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                     return;
                 }
             } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(this.telaCadProdutoCopa, "Valor inválido! Use apenas números (ex: 10.50).", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                 return;
             }
              if (codBarra.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadProdutoCopa, "O código de barras é obrigatório!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ProdutoCopa produto = new ProdutoCopa();
            produto.setDescricao(descricao);
            produto.setValor(valor);
            produto.setCodigoBarra(codBarra);
            produto.setStatus('A');

            if (this.telaCadProdutoCopa.getjTextFieldId().getText().equalsIgnoreCase("")) {
                ProdutoCopaService.Criar(produto);
            } else {
                produto.setId(Integer.parseInt(this.telaCadProdutoCopa.getjTextFieldId().getText()));
                ProdutoCopaService.Atualizar(produto);
            }

            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadProdutoCopa.getjButtonCancelar()) {
            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadProdutoCopa.getjButtonBuscar()) {
            codigo = 0;
            TelaBuscaProdutoCopa telaBusca = new TelaBuscaProdutoCopa(null, true);
            ControllerBuscaProdutoCopa controllerBusca = new ControllerBuscaProdutoCopa(telaBusca);
            telaBusca.setVisible(true);

            if (codigo != 0) {
                ProdutoCopa produto = ProdutoCopaService.Carregar(codigo);
                ativaDesativa(false);
                utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), true);

                this.telaCadProdutoCopa.getjTextFieldId().setText(String.valueOf(produto.getId()));
                this.telaCadProdutoCopa.getjTextFieldDescricao().setText(produto.getDescricao());
                this.telaCadProdutoCopa.getjTextFieldValor().setText(String.format("%.2f", produto.getValor()).replace(".", ",")); // Formata valor
                this.telaCadProdutoCopa.getjTextFieldCodigoBarra().setText(produto.getCodigoBarra());

                this.telaCadProdutoCopa.getjTextFieldId().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadProdutoCopa.getjButtonSair()) {
            this.telaCadProdutoCopa.dispose();
        }
    }

    private void ativaDesativa(boolean ativado) {
        this.telaCadProdutoCopa.getjButtonNovo().setEnabled(ativado);
        this.telaCadProdutoCopa.getjButtonGravar().setEnabled(!ativado);
        this.telaCadProdutoCopa.getjButtonCancelar().setEnabled(!ativado);
        this.telaCadProdutoCopa.getjButtonBuscar().setEnabled(ativado);
        this.telaCadProdutoCopa.getjButtonSair().setEnabled(ativado);
    }
}