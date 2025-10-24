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
        this.telaCadModelo.getjButtonExcluir().addActionListener(this);

        carregarMarcas(); 

        ativaDesativa(true);
        utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), false);
        this.telaCadModelo.getjTextFieldStatus().setEnabled(false);
    }

    private void carregarMarcas(){
        this.telaCadModelo.getjComboBoxMarca().removeAllItems();
        List<Marca> listaMarcas = MarcaService.Carregar();
        for (Marca marca : listaMarcas) {
             this.telaCadModelo.getjComboBoxMarca().addItem(marca);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadModelo.getjButtonNovo()) {
            ativaDesativa(false);
            utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), true);
            this.telaCadModelo.getjTextFieldId().setEnabled(false);
            this.telaCadModelo.getjComboBoxMarca().setEnabled(true);
            this.telaCadModelo.getjTextFieldStatus().setText("A");
            this.telaCadModelo.getjTextFieldStatus().setEnabled(false);
            this.telaCadModelo.getjButtonGravar().setEnabled(true);

        } else if (e.getSource() == this.telaCadModelo.getjButtonGravar()) {
            String descricao = this.telaCadModelo.getjTextFieldDescricao().getText();
            Marca marcaSelecionada = (Marca) this.telaCadModelo.getjComboBoxMarca().getSelectedItem();

             if (descricao.isBlank()) {
                 JOptionPane.showMessageDialog(this.telaCadModelo, "A descrição é obrigatória!");
                 this.telaCadModelo.getjTextFieldDescricao().requestFocus();
                 return;
             }
             if (marcaSelecionada == null) {
                 JOptionPane.showMessageDialog(this.telaCadModelo, "Selecione uma marca!");
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
            this.telaCadModelo.getjTextFieldStatus().setEnabled(false);

        } else if (e.getSource() == this.telaCadModelo.getjButtonCancelar()) {
            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), false);
            this.telaCadModelo.getjTextFieldStatus().setEnabled(false);

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
                this.telaCadModelo.getjTextFieldStatus().setText(String.valueOf(modelo.getStatus()));

                Marca marcaDoModelo = modelo.getMarca();
                for (int i = 0; i < this.telaCadModelo.getjComboBoxMarca().getItemCount(); i++) {
                   if (this.telaCadModelo.getjComboBoxMarca().getItemAt(i) != null && 
                       this.telaCadModelo.getjComboBoxMarca().getItemAt(i).getId() == marcaDoModelo.getId()) {
                       this.telaCadModelo.getjComboBoxMarca().setSelectedIndex(i);
                       break;
                   }
                }
                
                this.telaCadModelo.getjTextFieldId().setEnabled(false);
                this.telaCadModelo.getjTextFieldStatus().setEnabled(false);
                
                if (modelo.getStatus() == 'A') {
                    this.telaCadModelo.getjComboBoxMarca().setEnabled(true);
                    this.telaCadModelo.getjButtonGravar().setEnabled(true);
                    this.telaCadModelo.getjButtonExcluir().setEnabled(true);
                } else {
                    this.telaCadModelo.getjComboBoxMarca().setEnabled(false);
                    this.telaCadModelo.getjButtonGravar().setEnabled(false);
                    this.telaCadModelo.getjButtonExcluir().setEnabled(false);
                }
            } else {
                 ativaDesativa(true);
                 utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), false);
                 this.telaCadModelo.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadModelo.getjButtonExcluir()) {
             if (this.telaCadModelo.getjTextFieldId().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Você precisa buscar um modelo antes de excluir.");
                return;
            }
            
            if (this.telaCadModelo.getjTextFieldStatus().getText().equalsIgnoreCase("I")) {
                 JOptionPane.showMessageDialog(null, "Este registro já está inativo.");
                 return;
            }
            
            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja INATIVAR este modelo?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                Modelo modelo = new Modelo();
                modelo.setId(Integer.parseInt(this.telaCadModelo.getjTextFieldId().getText()));
                
                ModeloService.Apagar(modelo); 
                
                JOptionPane.showMessageDialog(null, "Modelo inativado com sucesso!");
                
                ativaDesativa(true);
                utilities.Utilities.limpaComponentes(this.telaCadModelo.getjPanelDados(), false);
                this.telaCadModelo.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadModelo.getjButtonSair()) {
            this.telaCadModelo.dispose();
        }
    }

    private void ativaDesativa(boolean estadoInicial) {
        this.telaCadModelo.getjButtonNovo().setEnabled(estadoInicial);
        this.telaCadModelo.getjButtonBuscar().setEnabled(estadoInicial);
        this.telaCadModelo.getjButtonSair().setEnabled(estadoInicial);
        
        this.telaCadModelo.getjButtonGravar().setEnabled(!estadoInicial);
        this.telaCadModelo.getjButtonCancelar().setEnabled(!estadoInicial);
        this.telaCadModelo.getjComboBoxMarca().setEnabled(!estadoInicial);
        
        this.telaCadModelo.getjButtonExcluir().setEnabled(false);
    }
}