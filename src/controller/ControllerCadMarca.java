package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
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

        ativaDesativa(this.telaCadMarca, true);
        utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadMarca.getjButtonNovo()) {
            ativaDesativa(this.telaCadMarca, false);
            utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), true);
            this.telaCadMarca.getjTextFieldId().setEnabled(false);

        } else if (e.getSource() == this.telaCadMarca.getjButtonGravar()) {
            String descricao = this.telaCadMarca.getjTextFieldDescricao().getText();
            
            if(descricao.isEmpty() || descricao.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadMarca, "A descrição é obrigatória", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
            Marca marca = new Marca();
            marca.setDescricao(descricao);
            marca.setStatus('A');

            if (this.telaCadMarca.getjTextFieldId().getText().equalsIgnoreCase("")) {
                MarcaService.Criar(marca);
            } else {
                marca.setId(Integer.parseInt(this.telaCadMarca.getjTextFieldId().getText()));
                MarcaService.Atualizar(marca);
            }
            
            ativaDesativa(this.telaCadMarca, true);
            utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadMarca.getjButtonCancelar()) {
            ativaDesativa(this.telaCadMarca, true);
            utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadMarca.getjButtonBuscar()) {
            codigo = 0;
            
            TelaBuscaMarca telaBuscaMarca = new TelaBuscaMarca(null, true);
            ControllerBuscaMarca controllerBuscaMarca = new ControllerBuscaMarca(telaBuscaMarca);
            telaBuscaMarca.setVisible(true);

            if (codigo != 0) {
                Marca marca;
                marca = MarcaService.Carregar(codigo);
                
                ativaDesativa(this.telaCadMarca, false);
                utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), true);

                this.telaCadMarca.getjTextFieldId().setText(String.valueOf(marca.getId()));
                this.telaCadMarca.getjTextFieldDescricao().setText(marca.getDescricao());
                
                this.telaCadMarca.getjTextFieldId().setEnabled(false);
            }
            
        } else if (e.getSource() == this.telaCadMarca.getjButtonSair()) {
            this.telaCadMarca.dispose();
        }
    }

    private void ativaDesativa(TelaCadastroMarca telaCadMarca, boolean ativado) {
        this.telaCadMarca.getjButtonNovo().setEnabled(ativado);
        this.telaCadMarca.getjButtonGravar().setEnabled(!ativado);
        this.telaCadMarca.getjButtonCancelar().setEnabled(!ativado);
        this.telaCadMarca.getjButtonBuscar().setEnabled(ativado);
        this.telaCadMarca.getjButtonSair().setEnabled(ativado);
    }
}