package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat; // Importar
import java.text.ParseException; // Importar
import java.util.Locale; // Importar
import javax.swing.JOptionPane;
import model.bo.ProdutoCopa;
import service.ProdutoCopaService;
import view.TelaBuscaProdutoCopa;
import view.TelaCadastroProdutoCopa;

public class ControllerCadProdutoCopa implements ActionListener {

    TelaCadastroProdutoCopa telaCadProdutoCopa;
    public static int codigo;
    private final NumberFormat currencyFormat; // Formatador de moeda

    public ControllerCadProdutoCopa(TelaCadastroProdutoCopa telaCadProdutoCopa) {
        this.telaCadProdutoCopa = telaCadProdutoCopa;

        this.telaCadProdutoCopa.getjButtonNovo().addActionListener(this);
        this.telaCadProdutoCopa.getjButtonGravar().addActionListener(this);
        this.telaCadProdutoCopa.getjButtonCancelar().addActionListener(this);
        this.telaCadProdutoCopa.getjButtonBuscar().addActionListener(this);
        this.telaCadProdutoCopa.getjButtonSair().addActionListener(this);
        this.telaCadProdutoCopa.getjButtonExcluir().addActionListener(this);

        // Configura o formatador para Real Brasileiro (R$)
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        ativaDesativa(true);
        utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), false);
        this.telaCadProdutoCopa.getjTextFieldStatus().setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadProdutoCopa.getjButtonNovo()) {
            ativaDesativa(false);
            utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), true);
            this.telaCadProdutoCopa.getjTextFieldId().setEnabled(false);
            this.telaCadProdutoCopa.getjTextFieldStatus().setText("A");
            this.telaCadProdutoCopa.getjTextFieldStatus().setEnabled(false);
            this.telaCadProdutoCopa.getjButtonGravar().setEnabled(true);

        } else if (e.getSource() == this.telaCadProdutoCopa.getjButtonGravar()) {
            String descricao = this.telaCadProdutoCopa.getjTextFieldDescricao().getText();
            String valorStr = this.telaCadProdutoCopa.getjTextFieldValor().getText();
            String codBarra = this.telaCadProdutoCopa.getjTextFieldCodigoBarra().getText();
            float valor = 0;

            if (descricao.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadProdutoCopa, "A descrição é obrigatória!");
                this.telaCadProdutoCopa.getjTextFieldDescricao().requestFocus();
                return;
            }
             if (valorStr.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadProdutoCopa, "O valor é obrigatório!");
                 this.telaCadProdutoCopa.getjTextFieldValor().requestFocus();
                return;
            }
             
             // Tratamento robusto para valor monetário
             try {
                 // Remove R$, espaços e substitui vírgula por ponto
                 String valorLimpo = valorStr.replace("R$", "").trim().replace(".", "").replace(",", ".");
                 valor = Float.parseFloat(valorLimpo);
                 
                 if (valor <= 0) {
                     JOptionPane.showMessageDialog(this.telaCadProdutoCopa, "O valor deve ser positivo!");
                     this.telaCadProdutoCopa.getjTextFieldValor().requestFocus();
                     return;
                 }
             } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(this.telaCadProdutoCopa, "Valor inválido! Digite apenas números (ex: 10,50 ou R$ 10,50).");
                 this.telaCadProdutoCopa.getjTextFieldValor().requestFocus();
                 return;
             }
             
              if (codBarra.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadProdutoCopa, "O código de barras é obrigatório!");
                this.telaCadProdutoCopa.getjTextFieldCodigoBarra().requestFocus();
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
            this.telaCadProdutoCopa.getjTextFieldStatus().setEnabled(false);

        } else if (e.getSource() == this.telaCadProdutoCopa.getjButtonCancelar()) {
            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), false);
            this.telaCadProdutoCopa.getjTextFieldStatus().setEnabled(false);

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
                // Formata o valor como moeda R$ para exibição
                this.telaCadProdutoCopa.getjTextFieldValor().setText(currencyFormat.format(produto.getValor())); 
                this.telaCadProdutoCopa.getjTextFieldCodigoBarra().setText(produto.getCodigoBarra());
                this.telaCadProdutoCopa.getjTextFieldStatus().setText(String.valueOf(produto.getStatus()));

                this.telaCadProdutoCopa.getjTextFieldId().setEnabled(false);
                this.telaCadProdutoCopa.getjTextFieldStatus().setEnabled(false);

                if(produto.getStatus() == 'A'){
                    this.telaCadProdutoCopa.getjButtonGravar().setEnabled(true);
                    this.telaCadProdutoCopa.getjButtonExcluir().setEnabled(true);
                } else {
                     this.telaCadProdutoCopa.getjButtonGravar().setEnabled(false);
                    this.telaCadProdutoCopa.getjButtonExcluir().setEnabled(false);
                }
            } else {
                 ativaDesativa(true);
                 utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), false);
                 this.telaCadProdutoCopa.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadProdutoCopa.getjButtonExcluir()) {
             if (this.telaCadProdutoCopa.getjTextFieldId().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Você precisa buscar um produto antes de excluir.");
                return;
            }
            
            if (this.telaCadProdutoCopa.getjTextFieldStatus().getText().equalsIgnoreCase("I")) {
                 JOptionPane.showMessageDialog(null, "Este registro já está inativo.");
                 return;
            }
            
            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja INATIVAR este produto?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                ProdutoCopa produto = new ProdutoCopa();
                produto.setId(Integer.parseInt(this.telaCadProdutoCopa.getjTextFieldId().getText()));
                
                ProdutoCopaService.Apagar(produto); 
                
                JOptionPane.showMessageDialog(null, "Produto inativado com sucesso!");
                
                ativaDesativa(true);
                utilities.Utilities.limpaComponentes(this.telaCadProdutoCopa.getjPanelDados(), false);
                this.telaCadProdutoCopa.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadProdutoCopa.getjButtonSair()) {
            this.telaCadProdutoCopa.dispose();
        }
    }

    private void ativaDesativa(boolean estadoInicial) {
        this.telaCadProdutoCopa.getjButtonNovo().setEnabled(estadoInicial);
        this.telaCadProdutoCopa.getjButtonGravar().setEnabled(!estadoInicial);
        this.telaCadProdutoCopa.getjButtonCancelar().setEnabled(!estadoInicial);
        this.telaCadProdutoCopa.getjButtonBuscar().setEnabled(estadoInicial);
        this.telaCadProdutoCopa.getjButtonSair().setEnabled(estadoInicial);
        this.telaCadProdutoCopa.getjButtonExcluir().setEnabled(false);
    }
}