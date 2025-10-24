package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Vaga; 
import service.VagaService; 
import view.TelaBuscaVaga; 

public class ControllerBuscaVaga implements ActionListener {

    TelaBuscaVaga telaBuscaVaga; 

    public ControllerBuscaVaga(TelaBuscaVaga telaBuscaVaga) {
        this.telaBuscaVaga = telaBuscaVaga;

        this.telaBuscaVaga.getjButtonCarregar().addActionListener(this);
        this.telaBuscaVaga.getjButtonFiltar().addActionListener(this);
        this.telaBuscaVaga.getjButtonSair().addActionListener(this);
        
        carregarTabela();
    }
    
    private void carregarTabela() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaVaga.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<Vaga> listaVagas = VagaService.Carregar(); 

        for (Vaga vagaAtual : listaVagas) { 
            tabela.addRow(new Object[]{
                vagaAtual.getId(),
                vagaAtual.getDescricao(),
                String.format("%.2f", vagaAtual.getMetragemVaga()),
                vagaAtual.getStatus()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaBuscaVaga.getjButtonCarregar()) {
             if (this.telaBuscaVaga.getjTableDados().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela!");
            } else {
                ControllerCadVaga.codigo = (int) this.telaBuscaVaga.getjTableDados() 
                        .getValueAt(this.telaBuscaVaga.getjTableDados().getSelectedRow(), 0);
                this.telaBuscaVaga.dispose();
            }
        } else if (evento.getSource() == this.telaBuscaVaga.getjButtonFiltar()) {
            
            if (this.telaBuscaVaga.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                 JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
                 return; 
            } 
            
            DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaVaga.getjTableDados().getModel();
            tabela.setRowCount(0);
            
            String filtroSelecionado = this.telaBuscaVaga.getjCBFiltro().getSelectedItem().toString();
            String valorFiltro = this.telaBuscaVaga.getjTFFiltro().getText();
            String colunaNoBanco;
            List<Vaga> listaVagas = new ArrayList<>(); 

            switch (filtroSelecionado) {
                 case "ID":
                     try {
                        Vaga vaga = VagaService.Carregar(Integer.parseInt(valorFiltro)); 
                        if (vaga != null && vaga.getId() != 0) {
                           listaVagas.add(vaga);
                        } else {
                             JOptionPane.showMessageDialog(null, "Nenhuma vaga encontrada com o ID " + valorFiltro + ".");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "O filtro de ID deve ser um número.", "Erro de Formato", JOptionPane.WARNING_MESSAGE);
                    }
                    break; 

                case "Descrição":
                    colunaNoBanco = "descricao";
                    listaVagas = VagaService.Carregar(colunaNoBanco, valorFiltro); 
                    break;
                    
                default:
                     JOptionPane.showMessageDialog(null, "Opção de filtro desconhecida: " + filtroSelecionado);
                     return;
            }

            if (listaVagas.isEmpty() && !filtroSelecionado.equalsIgnoreCase("ID")) {
                 JOptionPane.showMessageDialog(null, "Nenhuma vaga ativa encontrada para o filtro informado.");
            } else {
                for (Vaga vagaAtual : listaVagas) { 
                    tabela.addRow(new Object[]{
                        vagaAtual.getId(),
                        vagaAtual.getDescricao(),
                        String.format("%.2f", vagaAtual.getMetragemVaga()),
                        vagaAtual.getStatus()
                    });
                }
            }
            
        } else if (evento.getSource() == this.telaBuscaVaga.getjButtonSair()) {
            this.telaBuscaVaga.dispose();
        }
    }
}