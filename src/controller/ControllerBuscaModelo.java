package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Modelo;
import service.ModeloService;
import view.TelaBuscaModelo;

public class ControllerBuscaModelo implements ActionListener {

    TelaBuscaModelo telaBuscaModelo;

    public ControllerBuscaModelo(TelaBuscaModelo telaBuscaModelo) {
        this.telaBuscaModelo = telaBuscaModelo;

        this.telaBuscaModelo.getjButtonCarregar().addActionListener(this);
        this.telaBuscaModelo.getjButtonFiltar().addActionListener(this);
        this.telaBuscaModelo.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaBuscaModelo.getjButtonCarregar()) {
            if (this.telaBuscaModelo.getjTableDados().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Carregar!");
            } else {
                ControllerCadModelo.codigo = (int) this.telaBuscaModelo.getjTableDados()
                        .getValueAt(this.telaBuscaModelo.getjTableDados().getSelectedRow(), 0);
                this.telaBuscaModelo.dispose();
            }
        } else if (evento.getSource() == this.telaBuscaModelo.getjButtonFiltar()) {
            if (this.telaBuscaModelo.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
            } else {
                DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaModelo.getjTableDados().getModel();
                tabela.setRowCount(0);

                if (this.telaBuscaModelo.getjCBFiltro().getSelectedIndex() == 0) { // ID
                    Modelo modelo = ModeloService.Carregar(Integer.parseInt(this.telaBuscaModelo.getjTFFiltro().getText()));
                    if (modelo != null && modelo.getId() != 0) {
                         tabela.addRow(new Object[]{
                            modelo.getId(),
                            modelo.getDescricao(),
                            modelo.getMarca().getDescricao(),
                            modelo.getStatus()
                        });
                    }
                } else { // Descrição
                    String filtro = this.telaBuscaModelo.getjCBFiltro().getSelectedItem().toString().toLowerCase();
                    List<Modelo> listaModelos = ModeloService.Carregar(filtro, this.telaBuscaModelo.getjTFFiltro().getText());

                    for (Modelo modeloAtual : listaModelos) {
                        tabela.addRow(new Object[]{
                            modeloAtual.getId(),
                            modeloAtual.getDescricao(),
                            modeloAtual.getMarca().getDescricao(),
                            modeloAtual.getStatus()
                        });
                    }
                }
            }
        } else if (evento.getSource() == this.telaBuscaModelo.getjButtonSair()) {
            this.telaBuscaModelo.dispose();
        }
    }
}