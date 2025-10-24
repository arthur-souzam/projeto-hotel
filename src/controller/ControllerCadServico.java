package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.bo.Servico;
import service.ServicoService;
import view.TelaBuscaServico;
import view.TelaCadastroServico;

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

        ativaDesativa(true);
        utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadServico.getjButtonNovo()) {
            ativaDesativa(false);
            utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), true);
            this.telaCadServico.getjTextFieldId().setEnabled(false);

        } else if (e.getSource() == this.telaCadServico.getjButtonGravar()) {
            String descricao = this.telaCadServico.getjTextFieldDescricao().getText();
            String valorStr = this.telaCadServico.getjTextFieldValor().getText();
            float valor = 0;

            if (descricao.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadServico, "A descrição é obrigatória!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }
             if (valorStr.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadServico, "O valor é obrigatório!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }
             try {
                 valor = Float.parseFloat(valorStr.replace(",", "."));
                  if (valor <= 0) {
                     JOptionPane.showMessageDialog(this.telaCadServico, "O valor deve ser positivo!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                     return;
                 }
             } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(this.telaCadServico, "Valor inválido! Use apenas números (ex: 50.00).", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
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

        } else if (e.getSource() == this.telaCadServico.getjButtonCancelar()) {
            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), false);

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
                this.telaCadServico.getjTextFieldValor().setText(String.format("%.2f", servico.getValor()).replace(".", ",")); // Formata valor

                this.telaCadServico.getjTextFieldId().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadServico.getjButtonSair()) {
            this.telaCadServico.dispose();
        }
    }

     private void ativaDesativa(boolean ativado) {
        this.telaCadServico.getjButtonNovo().setEnabled(ativado);
        this.telaCadServico.getjButtonGravar().setEnabled(!ativado);
        this.telaCadServico.getjButtonCancelar().setEnabled(!ativado);
        this.telaCadServico.getjButtonBuscar().setEnabled(ativado);
        this.telaCadServico.getjButtonSair().setEnabled(ativado);
    }
}