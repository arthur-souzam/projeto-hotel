package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.bo.Marca;
import service.MarcaService;
import view.TelaBuscaMarca;
import view.TelaCadastroMarca;

public class ControllerCadMarca implements ActionListener {

    TelaCadastroMarca telaCadMarca;
    public static int codigo;

    public ControllerCadMarca(TelaCadastroMarca telaCadMarca) {
        this.telaCadMarca = telaCadMarca;

        this.telaCadMarca.getjButtonNovo().addActionListener(this);
        this.telaCadMarca.getjButtonGravar().addActionListener(this);
        this.telaCadMarca.getjButtonCancelar().addActionListener(this);
        this.telaCadMarca.getjButtonBuscar().addActionListener(this);
        this.telaCadMarca.getjButtonSair().addActionListener(this);

        utilities.Utilities.ativaDesativa(this.telaCadMarca.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadMarca.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadMarca.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), true);
            this.telaCadMarca.getjTextFieldId().setEnabled(false);

        } else if (e.getSource() == this.telaCadMarca.getjButtonGravar()) {
            Marca marca = new Marca();
            marca.setDescricao(this.telaCadMarca.getjTextFieldDescricao().getText());
            marca.setStatus('A');

            if (this.telaCadMarca.getjTextFieldId().getText().equalsIgnoreCase("")) {
                MarcaService.Criar(marca);
            } else {
                marca.setId(Integer.parseInt(this.telaCadMarca.getjTextFieldId().getText()));
                MarcaService.Atualizar(marca);
            }
            
            utilities.Utilities.ativaDesativa(this.telaCadMarca.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadMarca.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadMarca.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadMarca.getjButtonBuscar()) {
            codigo = 0;
            
            TelaBuscaMarca telaBuscaMarca = new TelaBuscaMarca(null, true);
            ControllerBuscaMarca controllerBuscaMarca = new ControllerBuscaMarca(telaBuscaMarca);
            telaBuscaMarca.setVisible(true);

            if (codigo != 0) {
                Marca marca;
                marca = MarcaService.Carregar(codigo);
                
                utilities.Utilities.ativaDesativa(this.telaCadMarca.getjPanelBotoes(), false);
                utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), true);

                this.telaCadMarca.getjTextFieldId().setText(String.valueOf(marca.getId()));
                this.telaCadMarca.getjTextFieldDescricao().setText(marca.getDescricao());
                
                this.telaCadMarca.getjTextFieldId().setEnabled(false);
            }
            
        } else if (e.getSource() == this.telaCadMarca.getjButtonSair()) {
            this.telaCadMarca.dispose();
        }
    }
}