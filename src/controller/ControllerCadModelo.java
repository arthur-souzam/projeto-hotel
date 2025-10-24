package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import model.bo.Marca;
import model.bo.Modelo;
import service.MarcaService;
import service.ModeloService;
import view.TelaBuscaModelo;
import view.TelaCadastroModelo;

public class ControllerCadModelo implements ActionListener {

    TelaCadastroModelo telaCadModelo;
    public static int codigo;

    public ControllerCadModelo(TelaCadastroModelo telaCadModelo) {
        this.telaCadModelo = telaCadModelo;

        this.telaCadModelo.getjButtonNovo().addActionListener(this);
        this.telaCadModelo.getjButtonGravar().addActionListener(this);
        this.telaCadModelo.getjButtonCancelar().addActionListener(this);
        this.telaCadModelo.getjButtonBuscar().addActionListener(this);
        this.telaCadModelo.getjButtonSair().addActionListener(this);

        carregarMarcas(); // Carrega as marcas no ComboBox

        ativaDesativa(true);
        utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), false);
    }

    private void carregarMarcas(){
        this.telaCadModelo.getjComboBoxMarca().removeAllItems();
        List<Marca> listaMarcas = MarcaService.Carregar();
        for (Marca marca : listaMarcas) {
             if(marca.getStatus() == 'A'){
                 this.telaCadModelo.getjComboBoxMarca().addItem(marca);
             }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadModelo.getjButtonNovo()) {
            ativaDesativa(false);
            utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), true);
            this.telaCadModelo.getjTextFieldId().setEnabled(false);
            this.telaCadModelo.getjComboBoxMarca().setEnabled(true);

        } else if (e.getSource() == this.telaCadModelo.getjButtonGravar()) {
            String descricao = this.telaCadModelo.getjTextFieldDescricao().getText();
            Marca marcaSelecionada = (Marca) this.telaCadModelo.getjComboBoxMarca().getSelectedItem();

             if (descricao.isBlank()) {
                 JOptionPane.showMessageDialog(this.telaCadModelo, "A descrição é obrigatória!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                 return;
             }
             if (marcaSelecionada == null) {
                 JOptionPane.showMessageDialog(this.telaCadModelo, "Selecione uma marca!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                 return;
             }

            Modelo modelo = new Modelo();
            modelo.setDescricao(descricao);
            modelo.setStatus('A');
            modelo.setMarca(marcaSelecionada);

            if (this.telaCadModelo.getjTextFieldId().getText().equalsIgnoreCase("")) {
                ModeloService.Criar(modelo);
            } else {
                modelo.setId(Integer.parseInt(this.telaCadModelo.getjTextFieldId().getText()));
                ModeloService.Atualizar(modelo);
            }

            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadModelo.getjButtonCancelar()) {
            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadModelo.getjButtonBuscar()) {
            codigo = 0;
            TelaBuscaModelo telaBuscaModelo = new TelaBuscaModelo(null, true);
            ControllerBuscaModelo controllerBuscaModelo = new ControllerBuscaModelo(telaBuscaModelo);
            telaBuscaModelo.setVisible(true);

            if (codigo != 0) {
                Modelo modelo = ModeloService.Carregar(codigo);
                ativaDesativa(false);
                utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), true);

                this.telaCadModelo.getjTextFieldId().setText(String.valueOf(modelo.getId()));
                this.telaCadModelo.getjTextFieldDescricao().setText(modelo.getDescricao());

                // Seleciona a marca correta no ComboBox
                Marca marcaDoModelo = modelo.getMarca();
                for (int i = 0; i < this.telaCadModelo.getjComboBoxMarca().getItemCount(); i++) {
                   if (this.telaCadModelo.getjComboBoxMarca().getItemAt(i).getId() == marcaDoModelo.getId()) {
                       this.telaCadModelo.getjComboBoxMarca().setSelectedIndex(i);
                       break;
                   }
                }
                this.telaCadModelo.getjComboBoxMarca().setEnabled(true);

                this.telaCadModelo.getjTextFieldId().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadModelo.getjButtonSair()) {
            this.telaCadModelo.dispose();
        }
    }

    private void ativaDesativa(boolean ativado) {
        this.telaCadModelo.getjButtonNovo().setEnabled(ativado);
        this.telaCadModelo.getjButtonGravar().setEnabled(!ativado);
        this.telaCadModelo.getjButtonCancelar().setEnabled(!ativado);
        this.telaCadModelo.getjButtonBuscar().setEnabled(ativado);
        this.telaCadModelo.getjButtonSair().setEnabled(ativado);
        this.telaCadModelo.getjComboBoxMarca().setEnabled(!ativado);
    }
}