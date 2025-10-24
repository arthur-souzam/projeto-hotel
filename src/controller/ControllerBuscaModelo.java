package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
        
        carregarTabela();
    }
    
    private void carregarTabela() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaModelo.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<Modelo> listaModelos = ModeloService.Carregar(); 

        for (Modelo modeloAtual : listaModelos) {
            tabela.addRow(new Object[]{
                modeloAtual.getId(),
                modeloAtual.getDescricao(),
                (modeloAtual.getMarca() != null ? modeloAtual.getMarca().getDescricao() : ""),
                modeloAtual.getStatus()
            });
        }
    }


    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaBuscaModelo.getjButtonCarregar()) {
            if (this.telaBuscaModelo.getjTableDados().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela!");
            } else {
                ControllerCadModelo.codigo = (int) this.telaBuscaModelo.getjTableDados()
                        .getValueAt(this.telaBuscaModelo.getjTableDados().getSelectedRow(), 0);
                this.telaBuscaModelo.dispose();
            }
        } else if (evento.getSource() == this.telaBuscaModelo.getjButtonFiltar()) {
            
            if (this.telaBuscaModelo.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                 JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
                 return;
            } 
            
            DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaModelo.getjTableDados().getModel();
            tabela.setRowCount(0);
            
            String filtroSelecionado = this.telaBuscaModelo.getjCBFiltro().getSelectedItem().toString();
            String valorFiltro = this.telaBuscaModelo.getjTFFiltro().getText();
            String colunaNoBanco;
            List<Modelo> listaModelos = new ArrayList<>();

            switch (filtroSelecionado) {
                 case "ID":
                     try {
                        Modelo modelo = ModeloService.Carregar(Integer.parseInt(valorFiltro));
                        if (modelo != null && modelo.getId() != 0) {
                           listaModelos.add(modelo);
                        } else {
                             JOptionPane.showMessageDialog(null, "Nenhum modelo encontrado com o ID " + valorFiltro + ".");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "O filtro de ID deve ser um número.", "Erro de Formato", JOptionPane.WARNING_MESSAGE);
                    }
                    break; 

                case "Descrição":
                    colunaNoBanco = "descricao";
                    listaModelos = ModeloService.Carregar(colunaNoBanco, valorFiltro);
                    break;
                    
                default:
                     JOptionPane.showMessageDialog(null, "Opção de filtro desconhecida: " + filtroSelecionado);
                     return;
            }

            if (listaModelos.isEmpty() && !filtroSelecionado.equalsIgnoreCase("ID")) {
                 JOptionPane.showMessageDialog(null, "Nenhum modelo ativo encontrado para o filtro informado.");
            } else {
                for (Modelo modeloAtual : listaModelos) {
                    tabela.addRow(new Object[]{
                        modeloAtual.getId(),
                        modeloAtual.getDescricao(),
                         (modeloAtual.getMarca() != null ? modeloAtual.getMarca().getDescricao() : ""),
                        modeloAtual.getStatus()
                    });
                }
            }
            
        } else if (evento.getSource() == this.telaBuscaModelo.getjButtonSair()) {
            this.telaBuscaModelo.dispose();
        }
    }
}