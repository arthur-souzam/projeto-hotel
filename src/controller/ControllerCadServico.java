package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.JOptionPane;
import model.bo.Servico;
import service.ServicoService;
import view.TelaBuscaServico;
import view.TelaCadastroServico;
import javax.swing.text.MaskFormatter; 


public class ControllerCadServico implements ActionListener {

    TelaCadastroServico telaCadServico;
    public static int codigo;
    

    public ControllerCadServico(TelaCadastroServico telaCadServico) {
        this.telaCadServico = telaCadServico;

        this.telaCadServico.getjButtonNovo().addActionListener(this);
        this.telaCadServico.getjButtonGravar().addActionListener(this);
        this.telaCadServico.getjButtonCancelar().addActionListener(this);
        this.telaCadServico.getjButtonBuscar().addActionListener(this);
        this.telaCadServico.getjButtonSair().addActionListener(this);
        this.telaCadServico.getjButtonExcluir().addActionListener(this);
        
        ativaDesativa(true);
        utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), false);
        this.telaCadServico.getjTextFieldStatus().setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadServico.getjButtonNovo()) {
            ativaDesativa(false);
            utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), true);
            this.telaCadServico.getjTextFieldId().setEnabled(false);
            this.telaCadServico.getjTextFieldStatus().setText("A");
            this.telaCadServico.getjTextFieldStatus().setEnabled(false);
            this.telaCadServico.getjButtonGravar().setEnabled(true);
            this.telaCadServico.getjFormattedTextFieldValor().setValue(0f); 

        } else if (e.getSource() == this.telaCadServico.getjButtonGravar()) {
            String descricao = this.telaCadServico.getjTextFieldDescricao().getText();
            Object valorObj = this.telaCadServico.getjFormattedTextFieldValor().getValue();
            float valor = 0;

            if (descricao.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadServico, "A descrição é obrigatória!");
                 this.telaCadServico.getjTextFieldDescricao().requestFocus();
                return;
            }
             
             if (valorObj == null) {
                JOptionPane.showMessageDialog(this.telaCadServico, "O valor é obrigatório!");
                 this.telaCadServico.getjFormattedTextFieldValor().requestFocus();
                return;
            }

           
             if (valorObj instanceof Number) {
                 valor = ((Number) valorObj).floatValue();
             } else {
              
                  try {
                     String valorStr = valorObj.toString().replaceAll("[^0-9,]", "").replace(",", ".");
                     valor = Float.parseFloat(valorStr);
                  } catch (NumberFormatException ex) {
                     JOptionPane.showMessageDialog(this.telaCadServico, "Valor inválido!");
                     this.telaCadServico.getjFormattedTextFieldValor().requestFocus();
                     return;
                 }
             }

             if (valor <= 0) {
                 JOptionPane.showMessageDialog(this.telaCadServico, "O valor deve ser positivo!");
                 this.telaCadServico.getjFormattedTextFieldValor().requestFocus();
                 return;
             }
             

            Servico servico = new Servico();
            servico.setDescricao(descricao);
            servico.setValor(valor);
            servico.setStatus('A');

            if (this.telaCadServico.getjTextFieldId().getText().equalsIgnoreCase("")) {
                ServicoService.Criar(servico);
            } else {
                servico.setId(Integer.parseInt(this.telaCadServico.getjTextFieldId().getText()));
                ServicoService.Atualizar(servico);
            }

            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), false);
            this.telaCadServico.getjTextFieldStatus().setEnabled(false);

        } else if (e.getSource() == this.telaCadServico.getjButtonCancelar()) {
            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), false);
            this.telaCadServico.getjTextFieldStatus().setEnabled(false);

        } else if (e.getSource() == this.telaCadServico.getjButtonBuscar()) {
            codigo = 0;
            TelaBuscaServico telaBusca = new TelaBuscaServico(null, true);
            ControllerBuscaServico controllerBusca = new ControllerBuscaServico(telaBusca); 
            telaBusca.setVisible(true);

            if (codigo != 0) {
                Servico servico = ServicoService.Carregar(codigo);
                ativaDesativa(false);
                utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), true);

                this.telaCadServico.getjTextFieldId().setText(String.valueOf(servico.getId()));
                this.telaCadServico.getjTextFieldDescricao().setText(servico.getDescricao());
                this.telaCadServico.getjFormattedTextFieldValor().setValue(servico.getValor()); 
                this.telaCadServico.getjTextFieldStatus().setText(String.valueOf(servico.getStatus()));

                this.telaCadServico.getjTextFieldId().setEnabled(false);
                this.telaCadServico.getjTextFieldStatus().setEnabled(false);

                if(servico.getStatus() == 'A'){
                    this.telaCadServico.getjButtonGravar().setEnabled(true);
                    this.telaCadServico.getjButtonExcluir().setEnabled(true);
                } else {
                     this.telaCadServico.getjButtonGravar().setEnabled(false);
                    this.telaCadServico.getjButtonExcluir().setEnabled(false);
                }
            } else {
                 ativaDesativa(true);
                 utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), false);
                 this.telaCadServico.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadServico.getjButtonExcluir()) {
             if (this.telaCadServico.getjTextFieldId().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Você precisa buscar um serviço antes de excluir.");
                return;
            }
            
            if (this.telaCadServico.getjTextFieldStatus().getText().equalsIgnoreCase("I")) {
                 JOptionPane.showMessageDialog(null, "Este registro já está inativo.");
                 return;
            }
            
            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja INATIVAR este serviço?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                Servico servico = new Servico();
                servico.setId(Integer.parseInt(this.telaCadServico.getjTextFieldId().getText()));
                
                ServicoService.Apagar(servico); 
                
                JOptionPane.showMessageDialog(null, "Serviço inativado com sucesso!");
                
                ativaDesativa(true);
                utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), false);
                this.telaCadServico.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadServico.getjButtonSair()) {
            this.telaCadServico.dispose();
        }
    }

    private void ativaDesativa(boolean estadoInicial) {
        this.telaCadServico.getjButtonNovo().setEnabled(estadoInicial);
        this.telaCadServico.getjButtonGravar().setEnabled(!estadoInicial);
        this.telaCadServico.getjButtonCancelar().setEnabled(!estadoInicial);
        this.telaCadServico.getjButtonBuscar().setEnabled(estadoInicial);
        this.telaCadServico.getjButtonSair().setEnabled(estadoInicial);
        this.telaCadServico.getjButtonExcluir().setEnabled(false);
    }
}