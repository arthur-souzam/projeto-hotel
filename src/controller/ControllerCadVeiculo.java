package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import model.bo.Modelo;
import model.bo.Veiculo;
import service.ModeloService;
import service.VeiculoService;
import view.TelaBuscaVeiculo;
import view.TelaCadastroVeiculo;

public class ControllerCadVeiculo implements ActionListener {

    TelaCadastroVeiculo telaCadVeiculo;
    public static int codigo;

    public ControllerCadVeiculo(TelaCadastroVeiculo telaCadVeiculo) {
        this.telaCadVeiculo = telaCadVeiculo;

        this.telaCadVeiculo.getjButtonNovo().addActionListener(this);
        this.telaCadVeiculo.getjButtonGravar().addActionListener(this);
        this.telaCadVeiculo.getjButtonCancelar().addActionListener(this);
        this.telaCadVeiculo.getjButtonBuscar().addActionListener(this);
        this.telaCadVeiculo.getjButtonSair().addActionListener(this);
        this.telaCadVeiculo.getjButtonExcluir().addActionListener(this);

        carregarModelos();

        ativaDesativa(true);
        utilities.Utilities.limpaComponentes(this.telaCadVeiculo.getjPanelDados(), false);
        this.telaCadVeiculo.getjTextFieldStatus().setEnabled(false);
    }

     private void carregarModelos(){
        this.telaCadVeiculo.getjComboBoxModelo().removeAllItems();
        List<Modelo> listaModelos = ModeloService.Carregar();
        for (Modelo modelo : listaModelos) {
            this.telaCadVeiculo.getjComboBoxModelo().addItem(modelo);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadVeiculo.getjButtonNovo()) {
            ativaDesativa(false);
            utilities.Utilities.limpaComponentes(this.telaCadVeiculo.getjPanelDados(), true);
            this.telaCadVeiculo.getjTextFieldId().setEnabled(false);
            this.telaCadVeiculo.getjComboBoxModelo().setEnabled(true);
            this.telaCadVeiculo.getjTextFieldStatus().setText("A");
            this.telaCadVeiculo.getjTextFieldStatus().setEnabled(false);
            this.telaCadVeiculo.getjButtonGravar().setEnabled(true);

        } else if (e.getSource() == this.telaCadVeiculo.getjButtonGravar()) {
            String placa = this.telaCadVeiculo.getjTextFieldPlaca().getText();
            String cor = this.telaCadVeiculo.getjTextFieldCor().getText();
            Modelo modeloSelecionado = (Modelo) this.telaCadVeiculo.getjComboBoxModelo().getSelectedItem();

             if (placa.isBlank()) {
                 JOptionPane.showMessageDialog(this.telaCadVeiculo, "A placa é obrigatória!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                 this.telaCadVeiculo.getjTextFieldPlaca().requestFocus();
                 return;
             }
              if (cor.isBlank()) {
                 JOptionPane.showMessageDialog(this.telaCadVeiculo, "A cor é obrigatória!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                 this.telaCadVeiculo.getjTextFieldCor().requestFocus();
                 return;
             }
             if (modeloSelecionado == null) {
                 JOptionPane.showMessageDialog(this.telaCadVeiculo, "Selecione um modelo!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                 return;
             }

            Veiculo veiculo = new Veiculo();
            veiculo.setPlaca(placa);
            veiculo.setCor(cor);
            veiculo.setStatus('A');
            veiculo.setModelo(modeloSelecionado);

            if (this.telaCadVeiculo.getjTextFieldId().getText().equalsIgnoreCase("")) {
                VeiculoService.Criar(veiculo);
            } else {
                veiculo.setId(Integer.parseInt(this.telaCadVeiculo.getjTextFieldId().getText()));
                VeiculoService.Atualizar(veiculo);
            }

            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadVeiculo.getjPanelDados(), false);
            this.telaCadVeiculo.getjTextFieldStatus().setEnabled(false);

        } else if (e.getSource() == this.telaCadVeiculo.getjButtonCancelar()) {
            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadVeiculo.getjPanelDados(), false);
            this.telaCadVeiculo.getjTextFieldStatus().setEnabled(false);

        } else if (e.getSource() == this.telaCadVeiculo.getjButtonBuscar()) {
            codigo = 0;
            TelaBuscaVeiculo telaBuscaVeiculo = new TelaBuscaVeiculo(null, true);
            ControllerBuscaVeiculo controllerBuscaVeiculo = new ControllerBuscaVeiculo(telaBuscaVeiculo);
            telaBuscaVeiculo.setVisible(true);

            if (codigo != 0) {
                Veiculo veiculo = VeiculoService.Carregar(codigo);
                
                ativaDesativa(false);
                utilities.Utilities.limpaComponentes(this.telaCadVeiculo.getjPanelDados(), true);

                this.telaCadVeiculo.getjTextFieldId().setText(String.valueOf(veiculo.getId()));
                this.telaCadVeiculo.getjTextFieldPlaca().setText(veiculo.getPlaca());
                this.telaCadVeiculo.getjTextFieldCor().setText(veiculo.getCor());
                this.telaCadVeiculo.getjTextFieldStatus().setText(String.valueOf(veiculo.getStatus()));

                Modelo modeloDoVeiculo = veiculo.getModelo();
                for (int i = 0; i < this.telaCadVeiculo.getjComboBoxModelo().getItemCount(); i++) {
                    if (this.telaCadVeiculo.getjComboBoxModelo().getItemAt(i) != null &&
                        this.telaCadVeiculo.getjComboBoxModelo().getItemAt(i).getId() == modeloDoVeiculo.getId()) {
                        this.telaCadVeiculo.getjComboBoxModelo().setSelectedIndex(i);
                        break;
                    }
                }
                
                this.telaCadVeiculo.getjTextFieldId().setEnabled(false);
                this.telaCadVeiculo.getjTextFieldStatus().setEnabled(false);
                this.telaCadVeiculo.getjComboBoxModelo().setEnabled(true);

                if (veiculo.getStatus() == 'A') {
                    this.telaCadVeiculo.getjButtonGravar().setEnabled(true);
                    this.telaCadVeiculo.getjButtonExcluir().setEnabled(true);
                } else {
                    this.telaCadVeiculo.getjButtonGravar().setEnabled(false);
                    this.telaCadVeiculo.getjButtonExcluir().setEnabled(false);
                    this.telaCadVeiculo.getjComboBoxModelo().setEnabled(false);
                }
                
            } else {
                 ativaDesativa(true);
                 utilities.Utilities.limpaComponentes(this.telaCadVeiculo.getjPanelDados(), false);
                 this.telaCadVeiculo.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadVeiculo.getjButtonExcluir()) {
            if (this.telaCadVeiculo.getjTextFieldId().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Você precisa buscar um veículo antes de excluir.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (this.telaCadVeiculo.getjTextFieldStatus().getText().equalsIgnoreCase("I")) {
                 JOptionPane.showMessageDialog(null, "Este registro já está inativo.", "Atenção", JOptionPane.INFORMATION_MESSAGE);
                 return;
            }
            
            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja INATIVAR este veículo?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                Veiculo veiculo = new Veiculo();
                veiculo.setId(Integer.parseInt(this.telaCadVeiculo.getjTextFieldId().getText()));
                
                VeiculoService.Apagar(veiculo);
                
                JOptionPane.showMessageDialog(null, "Veículo inativado com sucesso!");
                
                ativaDesativa(true);
                utilities.Utilities.limpaComponentes(this.telaCadVeiculo.getjPanelDados(), false);
                this.telaCadVeiculo.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadVeiculo.getjButtonSair()) {
            this.telaCadVeiculo.dispose();
        }
    }

    private void ativaDesativa(boolean estadoInicial) {
        this.telaCadVeiculo.getjButtonNovo().setEnabled(estadoInicial);
        this.telaCadVeiculo.getjButtonBuscar().setEnabled(estadoInicial);
        this.telaCadVeiculo.getjButtonSair().setEnabled(estadoInicial);
        
        this.telaCadVeiculo.getjButtonGravar().setEnabled(!estadoInicial);
        this.telaCadVeiculo.getjButtonCancelar().setEnabled(!estadoInicial);
        this.telaCadVeiculo.getjComboBoxModelo().setEnabled(!estadoInicial);
        
        this.telaCadVeiculo.getjButtonExcluir().setEnabled(false);
    }
}