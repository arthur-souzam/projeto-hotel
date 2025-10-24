package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.bo.Quarto;
import service.QuartoService;
import view.TelaBuscaQuarto; 
import view.TelaCadastroQuarto;

public class ControllerCadQuarto implements ActionListener {

    TelaCadastroQuarto telaCadQuarto;
    public static int codigo;

    public ControllerCadQuarto(TelaCadastroQuarto telaCadQuarto) {
        this.telaCadQuarto = telaCadQuarto;

        this.telaCadQuarto.getjButtonNovo().addActionListener(this);
        this.telaCadQuarto.getjButtonGravar().addActionListener(this);
        this.telaCadQuarto.getjButtonCancelar().addActionListener(this);
        this.telaCadQuarto.getjButtonBuscar().addActionListener(this);
        this.telaCadQuarto.getjButtonSair().addActionListener(this);
        this.telaCadQuarto.getjButtonExcluir().addActionListener(this);

        ativaDesativa(true);
        utilities.Utilities.limpaComponentes(this.telaCadQuarto.getjPanelDados(), false);
        this.telaCadQuarto.getjTextFieldStatus().setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadQuarto.getjButtonNovo()) {
            ativaDesativa(false);
            utilities.Utilities.limpaComponentes(this.telaCadQuarto.getjPanelDados(), true);
            this.telaCadQuarto.getjTextFieldId().setEnabled(false);
            this.telaCadQuarto.getjTextFieldStatus().setText("A");
            this.telaCadQuarto.getjTextFieldStatus().setEnabled(false);
            this.telaCadQuarto.getjButtonGravar().setEnabled(true);

        } else if (e.getSource() == this.telaCadQuarto.getjButtonGravar()) {
            
            String descricao = this.telaCadQuarto.getjTextFieldDescricao().getText();
            String identificacao = this.telaCadQuarto.getjTextFieldIdentificacao().getText();
            String andarStr = this.telaCadQuarto.getjTextFieldAndar().getText();
            String capacidadeStr = this.telaCadQuarto.getjTextFieldCapacidade().getText();
            String metragemStr = this.telaCadQuarto.getjTextFieldMetragem().getText();
            int andar = 0;
            int capacidade = 0;
            float metragem = 0;

            if (descricao.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadQuarto, "A descrição é obrigatória!");
                 this.telaCadQuarto.getjTextFieldDescricao().requestFocus();
                return;
            }
            if (identificacao.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadQuarto, "A identificação (Nº Quarto) é obrigatória!");
                 this.telaCadQuarto.getjTextFieldIdentificacao().requestFocus();
                return;
            }
             if (andarStr.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadQuarto, "O andar é obrigatório!");
                 this.telaCadQuarto.getjTextFieldAndar().requestFocus();
                return;
            }
             if (capacidadeStr.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadQuarto, "A capacidade é obrigatória!");
                this.telaCadQuarto.getjTextFieldCapacidade().requestFocus();
                return;
            }
            
            try { andar = Integer.parseInt(andarStr); } 
            catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(this.telaCadQuarto, "Andar inválido! Digite apenas números inteiros.");
                 this.telaCadQuarto.getjTextFieldAndar().requestFocus();
                 return;
             }
             
            try { capacidade = Integer.parseInt(capacidadeStr); }
            catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(this.telaCadQuarto, "Capacidade inválida! Digite apenas números inteiros.");
                 this.telaCadQuarto.getjTextFieldCapacidade().requestFocus();
                 return;
             }
             
            try { metragem = Float.parseFloat(metragemStr.replace(",", ".")); }
            catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(this.telaCadQuarto, "Metragem inválida! Digite apenas números (ex: 25,5).");
                 this.telaCadQuarto.getjTextFieldMetragem().requestFocus();
                 return;
             }
            
            if (andar <= 0) { JOptionPane.showMessageDialog(this.telaCadQuarto, "Andar deve ser positivo!"); this.telaCadQuarto.getjTextFieldAndar().requestFocus(); return; }
            if (capacidade <= 0) { JOptionPane.showMessageDialog(this.telaCadQuarto, "Capacidade deve ser positiva!"); this.telaCadQuarto.getjTextFieldCapacidade().requestFocus(); return; }
            if (metragem <= 0) { JOptionPane.showMessageDialog(this.telaCadQuarto, "Metragem deve ser positiva!"); this.telaCadQuarto.getjTextFieldMetragem().requestFocus(); return; }

            Quarto quarto = new Quarto();
            quarto.setDescricao(descricao);
            quarto.setIdentificacao(identificacao);
            quarto.setAndar(andar);
            quarto.setCapacidadeHospedes(capacidade);
            quarto.setMetragem(metragem);
            quarto.setFlagAnimais(this.telaCadQuarto.getjCheckBoxFlagAnimais().isSelected());
            quarto.setObs(this.telaCadQuarto.getjTextFieldObs().getText());
            quarto.setStatus('A');

            if (this.telaCadQuarto.getjTextFieldId().getText().equalsIgnoreCase("")) {
                QuartoService.Criar(quarto);
            } else {
                quarto.setId(Integer.parseInt(this.telaCadQuarto.getjTextFieldId().getText()));
                QuartoService.Atualizar(quarto);
            }

            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadQuarto.getjPanelDados(), false);
            this.telaCadQuarto.getjTextFieldStatus().setEnabled(false);

        } else if (e.getSource() == this.telaCadQuarto.getjButtonCancelar()) {
            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadQuarto.getjPanelDados(), false);
            this.telaCadQuarto.getjTextFieldStatus().setEnabled(false);

        } else if (e.getSource() == this.telaCadQuarto.getjButtonBuscar()) {
            codigo = 0;
            TelaBuscaQuarto telaBusca = new TelaBuscaQuarto(null, true); 
            ControllerBuscaQuarto controllerBuscaQuarto = new ControllerBuscaQuarto(telaBusca); 
            telaBusca.setVisible(true);

            if (codigo != 0) {
                Quarto quarto = QuartoService.Carregar(codigo);
                ativaDesativa(false);
                utilities.Utilities.limpaComponentes(this.telaCadQuarto.getjPanelDados(), true);

                this.telaCadQuarto.getjTextFieldId().setText(String.valueOf(quarto.getId()));
                this.telaCadQuarto.getjTextFieldDescricao().setText(quarto.getDescricao());
                this.telaCadQuarto.getjTextFieldIdentificacao().setText(quarto.getIdentificacao());
                this.telaCadQuarto.getjTextFieldAndar().setText(String.valueOf(quarto.getAndar()));
                this.telaCadQuarto.getjTextFieldCapacidade().setText(String.valueOf(quarto.getCapacidadeHospedes()));
                this.telaCadQuarto.getjTextFieldMetragem().setText(String.format("%.2f", quarto.getMetragem()).replace(".", ","));
                this.telaCadQuarto.getjCheckBoxFlagAnimais().setSelected(quarto.isFlagAnimais());
                this.telaCadQuarto.getjTextFieldObs().setText(quarto.getObs());
                this.telaCadQuarto.getjTextFieldStatus().setText(String.valueOf(quarto.getStatus()));

                this.telaCadQuarto.getjTextFieldId().setEnabled(false);
                this.telaCadQuarto.getjTextFieldStatus().setEnabled(false);

                if (quarto.getStatus() == 'A') {
                    this.telaCadQuarto.getjButtonGravar().setEnabled(true);
                    this.telaCadQuarto.getjButtonExcluir().setEnabled(true);
                } else { 
                    this.telaCadQuarto.getjButtonGravar().setEnabled(false);
                    this.telaCadQuarto.getjButtonExcluir().setEnabled(false);
                }
            } else {
                 ativaDesativa(true);
                 utilities.Utilities.limpaComponentes(this.telaCadQuarto.getjPanelDados(), false);
                 this.telaCadQuarto.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadQuarto.getjButtonExcluir()) {
             if (this.telaCadQuarto.getjTextFieldId().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Você precisa buscar um quarto antes de excluir.");
                return;
            }
            
            if (this.telaCadQuarto.getjTextFieldStatus().getText().equalsIgnoreCase("I")) {
                 JOptionPane.showMessageDialog(null, "Este registro já está inativo.");
                 return;
            }
            
            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja INATIVAR este quarto?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                Quarto quarto = new Quarto();
                quarto.setId(Integer.parseInt(this.telaCadQuarto.getjTextFieldId().getText()));
                
                QuartoService.Apagar(quarto); 
                
                JOptionPane.showMessageDialog(null, "Quarto inativado com sucesso!");
                
                ativaDesativa(true);
                utilities.Utilities.limpaComponentes(this.telaCadQuarto.getjPanelDados(), false);
                this.telaCadQuarto.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadQuarto.getjButtonSair()) {
            this.telaCadQuarto.dispose();
        }
    }

    private void ativaDesativa(boolean estadoInicial) {
        this.telaCadQuarto.getjButtonNovo().setEnabled(estadoInicial);
        this.telaCadQuarto.getjButtonBuscar().setEnabled(estadoInicial);
        this.telaCadQuarto.getjButtonSair().setEnabled(estadoInicial);
        
        this.telaCadQuarto.getjButtonGravar().setEnabled(!estadoInicial);
        this.telaCadQuarto.getjButtonCancelar().setEnabled(!estadoInicial);
        
        this.telaCadQuarto.getjButtonExcluir().setEnabled(false);
    }
}