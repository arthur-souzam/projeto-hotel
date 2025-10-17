package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
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

        /*  ERRO DE MERDA List<Modelo> listaModelos = ModeloService.Carregar();
        for (Modelo modelo : listaModelos) {
            this.telaCadVeiculo.getjComboBoxModelo().addItem(modelo);
        }*/

        utilities.Utilities.ativaDesativa(this.telaCadVeiculo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadVeiculo.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadVeiculo.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadVeiculo.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(this.telaCadVeiculo.getjPanelDados(), true);
            this.telaCadVeiculo.getjTextFieldId().setEnabled(false);

        } else if (e.getSource() == this.telaCadVeiculo.getjButtonGravar()) {
            Veiculo veiculo = new Veiculo();
            veiculo.setPlaca(this.telaCadVeiculo.getjTextFieldPlaca().getText());
            veiculo.setCor(this.telaCadVeiculo.getjTextFieldCor().getText());
            veiculo.setStatus('A');
            veiculo.setModelo((Modelo) this.telaCadVeiculo.getjComboBoxModelo().getSelectedItem());

            if (this.telaCadVeiculo.getjTextFieldId().getText().equalsIgnoreCase("")) {
                VeiculoService.Criar(veiculo);
            } else {
                veiculo.setId(Integer.parseInt(this.telaCadVeiculo.getjTextFieldId().getText()));
                VeiculoService.Atualizar(veiculo);
            }
            
            utilities.Utilities.ativaDesativa(this.telaCadVeiculo.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadVeiculo.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadVeiculo.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadVeiculo.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadVeiculo.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadVeiculo.getjButtonBuscar()) {
            codigo = 0;
            
            TelaBuscaVeiculo telaBuscaVeiculo = new TelaBuscaVeiculo(null, true);
            ControllerBuscaVeiculo controllerBuscaVeiculo = new ControllerBuscaVeiculo(telaBuscaVeiculo);
            telaBuscaVeiculo.setVisible(true);

            if (codigo != 0) {
                Veiculo veiculo = VeiculoService.Carregar(codigo);
                
                utilities.Utilities.ativaDesativa(this.telaCadVeiculo.getjPanelBotoes(), false);
                utilities.Utilities.limpaComponentes(this.telaCadVeiculo.getjPanelDados(), true);

                this.telaCadVeiculo.getjTextFieldId().setText(String.valueOf(veiculo.getId()));
                this.telaCadVeiculo.getjTextFieldPlaca().setText(veiculo.getPlaca());
                this.telaCadVeiculo.getjTextFieldCor().setText(veiculo.getCor());
                
                // Para selecionar o item correto no ComboBox
                for (int i = 0; i < this.telaCadVeiculo.getjComboBoxModelo().getItemCount(); i++) {
                    if (this.telaCadVeiculo.getjComboBoxModelo().getItemAt(i).getId() == veiculo.getModelo().getId()) {
                        this.telaCadVeiculo.getjComboBoxModelo().setSelectedIndex(i);
                        break;
                    }
                }
                
                this.telaCadVeiculo.getjTextFieldId().setEnabled(false);
            }
            
        } else if (e.getSource() == this.telaCadVeiculo.getjButtonSair()) {
            this.telaCadVeiculo.dispose();
        }
    }
}