package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Marca;
import service.MarcaService;
import view.TelaBuscaMarca;

public class ControllerBuscaMarca implements ActionListener {

    TelaBuscaMarca telaBuscaMarca;

    public ControllerBuscaMarca(TelaBuscaMarca telaBuscaMarca) {
        this.telaBuscaMarca = telaBuscaMarca;

        this.telaBuscaMarca.getjButtonCarregar().addActionListener(this);
        this.telaBuscaMarca.getjButtonFiltar().addActionListener(this);
        this.telaBuscaMarca.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaBuscaMarca.getjButtonCarregar()) {
            if (this.telaBuscaMarca.getjTableDados().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Carregar!");
            } else {
                ControllerCadMarca.codigo = (int) this.telaBuscaMarca.getjTableDados()
                        .getValueAt(this.telaBuscaMarca.getjTableDados().getSelectedRow(), 0);
                this.telaBuscaMarca.dispose();
            }
        } else if (evento.getSource() == this.telaBuscaMarca.getjButtonFiltar()) {
            if (this.telaBuscaMarca.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
            } else {
                DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaMarca.getjTableDados().getModel();
                tabela.setRowCount(0);

                if (this.telaBuscaMarca.getjCBFiltro().getSelectedIndex() == 0) { // ID
                    Marca marca = MarcaService.Carregar(Integer.parseInt(this.telaBuscaMarca.getjTFFiltro().getText()));
                    if (marca != null) {
                         tabela.addRow(new Object[]{
                            marca.getId(),
                            marca.getDescricao(),
                            marca.getStatus()
                        });
                    }
                } else { // Descrição
                    String filtro = this.telaBuscaMarca.getjCBFiltro().getSelectedItem().toString().toLowerCase();
                    List<Marca> listaMarcas = MarcaService.Carregar(filtro, this.telaBuscaMarca.getjTFFiltro().getText());

                    for (Marca marcaAtual : listaMarcas) {
                        tabela.addRow(new Object[]{
                            marcaAtual.getId(),
                            marcaAtual.getDescricao(),
                            marcaAtual.getStatus()
                        });
                    }
                }
            }
        } else if (evento.getSource() == this.telaBuscaMarca.getjButtonSair()) {
            this.telaBuscaMarca.dispose();
        }
    }
}