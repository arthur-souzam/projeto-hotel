package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.bo.Vaga; // Atualizado
import service.VagaService; // Atualizado
import view.TelaBuscaVaga; // Atualizado
import view.TelaCadastroVaga; // Atualizado

public class ControllerCadVaga implements ActionListener { // Renomeado

    TelaCadastroVaga telaCadVaga; // Atualizado
    public static int codigo;

    public ControllerCadVaga(TelaCadastroVaga telaCadVaga) { // Renomeado e Atualizado
        this.telaCadVaga = telaCadVaga;

        this.telaCadVaga.getjButtonNovo().addActionListener(this);
        this.telaCadVaga.getjButtonGravar().addActionListener(this);
        this.telaCadVaga.getjButtonCancelar().addActionListener(this);
        this.telaCadVaga.getjButtonBuscar().addActionListener(this);
        this.telaCadVaga.getjButtonSair().addActionListener(this);
        this.telaCadVaga.getjButtonExcluir().addActionListener(this);

        ativaDesativa(true);
        utilities.Utilities.limpaComponentes(this.telaCadVaga.getjPanelDados(), false);
        this.telaCadVaga.getjTextFieldStatus().setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadVaga.getjButtonNovo()) {
            ativaDesativa(false);
            utilities.Utilities.limpaComponentes(this.telaCadVaga.getjPanelDados(), true);
            this.telaCadVaga.getjTextFieldId().setEnabled(false);
            this.telaCadVaga.getjTextFieldStatus().setText("A");
            this.telaCadVaga.getjTextFieldStatus().setEnabled(false);
            this.telaCadVaga.getjButtonGravar().setEnabled(true);

        } else if (e.getSource() == this.telaCadVaga.getjButtonGravar()) {
            
            String descricao = this.telaCadVaga.getjTextFieldDescricao().getText();
            String metragemStr = this.telaCadVaga.getjTextFieldMetragem().getText();
            float metragem = 0;

            if (descricao.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadVaga, "A descrição é obrigatória!");
                 this.telaCadVaga.getjTextFieldDescricao().requestFocus();
                return;
            }
             if (metragemStr.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadVaga, "A metragem é obrigatória!");
                 this.telaCadVaga.getjTextFieldMetragem().requestFocus();
                return;
            }
            
            try { metragem = Float.parseFloat(metragemStr.replace(",", ".")); }
            catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(this.telaCadVaga, "Metragem inválida! Digite apenas números (ex: 12,5).");
                 this.telaCadVaga.getjTextFieldMetragem().requestFocus();
                 return;
             }
             
            if (metragem <= 0) { JOptionPane.showMessageDialog(this.telaCadVaga, "Metragem deve ser positiva!"); this.telaCadVaga.getjTextFieldMetragem().requestFocus(); return; }

            Vaga vaga = new Vaga(); // Atualizado
            vaga.setDescricao(descricao);
            vaga.setMetragemVaga(metragem);
            vaga.setObs(this.telaCadVaga.getjTextFieldObs().getText());
            vaga.setStatus('A');

            if (this.telaCadVaga.getjTextFieldId().getText().equalsIgnoreCase("")) {
                VagaService.Criar(vaga); // Atualizado
            } else {
                vaga.setId(Integer.parseInt(this.telaCadVaga.getjTextFieldId().getText()));
                VagaService.Atualizar(vaga); // Atualizado
            }

            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadVaga.getjPanelDados(), false);
            this.telaCadVaga.getjTextFieldStatus().setEnabled(false);

        } else if (e.getSource() == this.telaCadVaga.getjButtonCancelar()) {
            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadVaga.getjPanelDados(), false);
            this.telaCadVaga.getjTextFieldStatus().setEnabled(false);

        } else if (e.getSource() == this.telaCadVaga.getjButtonBuscar()) {
            codigo = 0;
            TelaBuscaVaga telaBusca = new TelaBuscaVaga(null, true); // Atualizado
            ControllerBuscaVaga controllerBusca = new ControllerBuscaVaga(telaBusca); // Atualizado
            telaBusca.setVisible(true);

            if (codigo != 0) {
                Vaga vaga = VagaService.Carregar(codigo); // Atualizado
                ativaDesativa(false);
                utilities.Utilities.limpaComponentes(this.telaCadVaga.getjPanelDados(), true);

                this.telaCadVaga.getjTextFieldId().setText(String.valueOf(vaga.getId()));
                this.telaCadVaga.getjTextFieldDescricao().setText(vaga.getDescricao());
                this.telaCadVaga.getjTextFieldMetragem().setText(String.format("%.2f", vaga.getMetragemVaga()).replace(".", ","));
                this.telaCadVaga.getjTextFieldObs().setText(vaga.getObs());
                this.telaCadVaga.getjTextFieldStatus().setText(String.valueOf(vaga.getStatus()));

                this.telaCadVaga.getjTextFieldId().setEnabled(false);
                this.telaCadVaga.getjTextFieldStatus().setEnabled(false);

                if (vaga.getStatus() == 'A') {
                    this.telaCadVaga.getjButtonGravar().setEnabled(true);
                    this.telaCadVaga.getjButtonExcluir().setEnabled(true);
                } else { 
                    this.telaCadVaga.getjButtonGravar().setEnabled(false);
                    this.telaCadVaga.getjButtonExcluir().setEnabled(false);
                }
            } else {
                 ativaDesativa(true);
                 utilities.Utilities.limpaComponentes(this.telaCadVaga.getjPanelDados(), false);
                 this.telaCadVaga.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadVaga.getjButtonExcluir()) {
             if (this.telaCadVaga.getjTextFieldId().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Você precisa buscar uma vaga antes de excluir.");
                return;
            }
            
            if (this.telaCadVaga.getjTextFieldStatus().getText().equalsIgnoreCase("I")) {
                 JOptionPane.showMessageDialog(null, "Este registro já está inativo.");
                 return;
            }
            
            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja INATIVAR esta vaga?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                Vaga vaga = new Vaga(); // Atualizado
                vaga.setId(Integer.parseInt(this.telaCadVaga.getjTextFieldId().getText()));
                
                VagaService.Apagar(vaga); // Atualizado
                
                JOptionPane.showMessageDialog(null, "Vaga inativada com sucesso!");
                
                ativaDesativa(true);
                utilities.Utilities.limpaComponentes(this.telaCadVaga.getjPanelDados(), false);
                this.telaCadVaga.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadVaga.getjButtonSair()) {
            this.telaCadVaga.dispose();
        }
    }

    private void ativaDesativa(boolean estadoInicial) {
        this.telaCadVaga.getjButtonNovo().setEnabled(estadoInicial);
        this.telaCadVaga.getjButtonBuscar().setEnabled(estadoInicial);
        this.telaCadVaga.getjButtonSair().setEnabled(estadoInicial);
        
        this.telaCadVaga.getjButtonGravar().setEnabled(!estadoInicial);
        this.telaCadVaga.getjButtonCancelar().setEnabled(!estadoInicial);
        
        this.telaCadVaga.getjButtonExcluir().setEnabled(false);
    }
}