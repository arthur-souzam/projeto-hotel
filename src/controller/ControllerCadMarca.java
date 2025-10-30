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
        this.telaCadMarca.getjButtonExcluir().addActionListener(this);

        ativaDesativa(this.telaCadMarca, true);
        utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), false);
        this.telaCadMarca.getjTextFieldStatus().setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadMarca.getjButtonNovo()) {
            ativaDesativa(this.telaCadMarca, false);
            utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), true);
            this.telaCadMarca.getjTextFieldId().setEnabled(false);
            this.telaCadMarca.getjTextFieldStatus().setText("A");
            this.telaCadMarca.getjTextFieldStatus().setEnabled(false);
            this.telaCadMarca.getjButtonGravar().setEnabled(true);

        } else if (e.getSource() == this.telaCadMarca.getjButtonGravar()) {
            String descricao = this.telaCadMarca.getjTextFieldDescricao().getText();
            
            if(descricao.isEmpty() || descricao.isBlank()) {
                JOptionPane.showMessageDialog(this.telaCadMarca, "A descrição é obrigatória", "Erro", JOptionPane.ERROR_MESSAGE);
                this.telaCadMarca.getjTextFieldDescricao().requestFocus();
                return;
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
            this.telaCadMarca.getjTextFieldStatus().setEnabled(false);

        } else if (e.getSource() == this.telaCadMarca.getjButtonCancelar()) {
            ativaDesativa(this.telaCadMarca, true);
            utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), false);
            this.telaCadMarca.getjTextFieldStatus().setEnabled(false);

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
                
                
                this.telaCadMarca.getjTextFieldStatus().setText(String.valueOf(marca.getStatus()));

                this.telaCadMarca.getjTextFieldId().setEnabled(false);
                
                this.telaCadMarca.getjTextFieldStatus().setEnabled(false); 

                if (marca.getStatus() == 'A') {
                    this.telaCadMarca.getjButtonGravar().setEnabled(true);
                    this.telaCadMarca.getjButtonExcluir().setEnabled(true);
                } else { 
                    this.telaCadMarca.getjButtonGravar().setEnabled(false);
                    this.telaCadMarca.getjButtonExcluir().setEnabled(false);
                }
            } else {
                 ativaDesativa(this.telaCadMarca, true);
                 utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), false);
                 this.telaCadMarca.getjTextFieldStatus().setEnabled(false);
            }
            
        } else if (e.getSource() == this.telaCadMarca.getjButtonExcluir()) {
            if (this.telaCadMarca.getjTextFieldId().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Você precisa buscar uma marca antes de excluir.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (this.telaCadMarca.getjTextFieldStatus().getText().equalsIgnoreCase("I")) {
                 JOptionPane.showMessageDialog(null, "Este registro já está inativo.", "Atenção", JOptionPane.INFORMATION_MESSAGE);
                 return;
            }
            
            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja INATIVAR esta marca?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                Marca marca = new Marca();
                marca.setId(Integer.parseInt(this.telaCadMarca.getjTextFieldId().getText()));
                
                MarcaService.Apagar(marca);
                
                JOptionPane.showMessageDialog(null, "Marca inativada com sucesso!");
                
                ativaDesativa(this.telaCadMarca, true);
                utilities.Utilities.limpaComponentes(this.telaCadMarca.getjPanelDados(), false);
                this.telaCadMarca.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadMarca.getjButtonSair()) {
            this.telaCadMarca.dispose();
        }
    }

    private void ativaDesativa(TelaCadastroMarca telaCadMarca, boolean estadoInicial) {
        this.telaCadMarca.getjButtonNovo().setEnabled(estadoInicial);
        this.telaCadMarca.getjButtonBuscar().setEnabled(estadoInicial);
        this.telaCadMarca.getjButtonSair().setEnabled(estadoInicial);
        
        this.telaCadMarca.getjButtonGravar().setEnabled(!estadoInicial);
        this.telaCadMarca.getjButtonCancelar().setEnabled(!estadoInicial);
        
        this.telaCadMarca.getjButtonExcluir().setEnabled(false);
    }
}