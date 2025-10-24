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
            if (this.telaBuscaMarca.getjTableDados().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Carregar!");
            } else {
                ControllerCadMarca.codigo = (int) this.telaBuscaMarca.getjTableDados()
                        .getValueAt(this.telaBuscaMarca.getjTableDados().getSelectedRow(), 0);
                this.telaBuscaMarca.dispose();
            }
        } else if (evento.getSource() == this.telaBuscaMarca.getjButtonFiltar()) {
            if (this.telaBuscaMarca.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                carregarTabela();
            } else {
                DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaMarca.getjTableDados().getModel();
                tabela.setRowCount(0);

                String filtroSelecionado = this.telaBuscaMarca.getjCBFiltro().getSelectedItem().toString();
                String valorFiltro = this.telaBuscaMarca.getjTFFiltro().getText();

                if (filtroSelecionado.equalsIgnoreCase("ID")) {
                     try {
                        Marca marca = MarcaService.Carregar(Integer.parseInt(valorFiltro));
                        if (marca != null && marca.getStatus() == 'A') { // Only show active even if searching by ID
                             tabela.addRow(new Object[]{
                                marca.getId(),
                                marca.getDescricao(),
                                marca.getStatus()
                            });
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "O filtro de ID deve ser um número.", "Erro de Formato", JOptionPane.WARNING_MESSAGE);
                    }
                } else { 
                    String colunaNoBanco;
                     if (filtroSelecionado.equalsIgnoreCase("Descrição")) {
                        colunaNoBanco = "descricao";
                    } else {
                         JOptionPane.showMessageDialog(null, "Filtro desconhecido: " + filtroSelecionado);
                         return;
                     }
                    
                    List<Marca> listaMarcas = MarcaService.Carregar(colunaNoBanco, valorFiltro);

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