package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
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

        /* Carregar a lista de Marcas no ComboBox(ERRO MALDITO)
        List<Marca> listaMarcas = MarcaService.Carregar();
        for (Marca marca : listaMarcas) {
            this.telaCadModelo.getjComboBoxMarca().addItem(marca);
        }*/

        utilities.Utilities.ativaDesativa(this.telaCadModelo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadModelo.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadModelo.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), true);
            this.telaCadModelo.getjTextFieldId().setEnabled(false);

        } else if (e.getSource() == this.telaCadModelo.getjButtonGravar()) {
            Modelo modelo = new Modelo();
            modelo.setDescricao(this.telaCadModelo.getjTextFieldDescricao().getText());
            modelo.setStatus('A');
            modelo.setMarca((Marca) this.telaCadModelo.getjComboBoxMarca().getSelectedItem());

            if (this.telaCadModelo.getjTextFieldId().getText().equalsIgnoreCase("")) {
                ModeloService.Criar(modelo);
            } else {
                modelo.setId(Integer.parseInt(this.telaCadModelo.getjTextFieldId().getText()));
                ModeloService.Atualizar(modelo);
            }
            
            utilities.Utilities.ativaDesativa(this.telaCadModelo.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadModelo.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadModelo.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadModelo.getjButtonBuscar()) {
            codigo = 0;
            
            TelaBuscaModelo telaBuscaModelo = new TelaBuscaModelo(null, true);
            ControllerBuscaModelo controllerBuscaModelo = new ControllerBuscaModelo(telaBuscaModelo);
            telaBuscaModelo.setVisible(true);

            if (codigo != 0) {
                Modelo modelo = ModeloService.Carregar(codigo);
                
                utilities.Utilities.ativaDesativa(this.telaCadModelo.getjPanelBotoes(), false);
                utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), true);

                this.telaCadModelo.getjTextFieldId().setText(String.valueOf(modelo.getId()));
                this.telaCadModelo.getjTextFieldDescricao().setText(modelo.getDescricao());
                this.telaCadModelo.getjComboBoxMarca().setSelectedItem(modelo.getMarca());
                
                this.telaCadModelo.getjTextFieldId().setEnabled(false);
            }
            
        } else if (e.getSource() == this.telaCadModelo.getjButtonSair()) {
            this.telaCadModelo.dispose();
        }
    }
}