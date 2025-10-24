package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
        
        carregarTabela();
    }

    private void carregarTabela() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaMarca.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<Marca> listaMarcas = MarcaService.Carregar();

        for (Marca marcaAtual : listaMarcas) {
            tabela.addRow(new Object[]{
                marcaAtual.getId(),
                marcaAtual.getDescricao(),
                marcaAtual.getStatus()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaBuscaMarca.getjButtonCarregar()) {
             if (this.telaBuscaMarca.getjTableDados().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela!");
            } else {
                ControllerCadMarca.codigo = (int) this.telaBuscaMarca.getjTableDados()
                        .getValueAt(this.telaBuscaMarca.getjTableDados().getSelectedRow(), 0);
                this.telaBuscaMarca.dispose();
            }
        } else if (evento.getSource() == this.telaBuscaMarca.getjButtonFiltar()) {
            if (this.telaBuscaMarca.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                 JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
                 return;
            } 
            
            DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaMarca.getjTableDados().getModel();
            tabela.setRowCount(0);

            String filtroSelecionado = this.telaBuscaMarca.getjCBFiltro().getSelectedItem().toString();
            String valorFiltro = this.telaBuscaMarca.getjTFFiltro().getText();
            String colunaNoBanco;
            List<Marca> listaMarcas = new ArrayList<>();

            if (filtroSelecionado.equalsIgnoreCase("ID")) {
                 try {
                    Marca marca = MarcaService.Carregar(Integer.parseInt(valorFiltro));
                    if (marca != null && marca.getId() != 0) {
                         listaMarcas.add(marca);
                    } else {
                         JOptionPane.showMessageDialog(null, "Nenhuma marca encontrada com o ID " + valorFiltro + ".");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "O filtro de ID deve ser um número.", "Erro de Formato", JOptionPane.WARNING_MESSAGE);
                }
            } else { 
                 if (filtroSelecionado.equalsIgnoreCase("Descrição")) {
                    colunaNoBanco = "descricao";
                } else {
                     JOptionPane.showMessageDialog(null, "Filtro desconhecido: " + filtroSelecionado);
                     return;
                 }
                listaMarcas = MarcaService.Carregar(colunaNoBanco, valorFiltro);
            }
            
            if (listaMarcas.isEmpty() && !filtroSelecionado.equalsIgnoreCase("ID")) {
                 JOptionPane.showMessageDialog(null, "Nenhuma marca ativa encontrada para o filtro informado.");
            } else {
                for (Marca marcaAtual : listaMarcas) {
                    tabela.addRow(new Object[]{
                        marcaAtual.getId(),
                        marcaAtual.getDescricao(),
                        marcaAtual.getStatus()
                    });
                }
            }

        } else if (evento.getSource() == this.telaBuscaMarca.getjButtonSair()) {
            this.telaBuscaMarca.dispose();
        }
    }
}