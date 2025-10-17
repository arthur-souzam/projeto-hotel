package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.ProdutoCopa;
import service.ProdutoCopaService;
import view.TelaBuscaProdutoCopa;

public class ControllerBuscaProdutoCopa implements ActionListener {

    TelaBuscaProdutoCopa telaBusca;

    public ControllerBuscaProdutoCopa(TelaBuscaProdutoCopa telaBusca) {
        this.telaBusca = telaBusca;

        this.telaBusca.getjButtonCarregar().addActionListener(this);
        this.telaBusca.getjButtonFiltar().addActionListener(this);
        this.telaBusca.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaBusca.getjButtonCarregar()) {
            if (this.telaBusca.getjTableDados().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Carregar!");
            } else {
                ControllerCadProdutoCopa.codigo = (int) this.telaBusca.getjTableDados()
                        .getValueAt(this.telaBusca.getjTableDados().getSelectedRow(), 0);
                this.telaBusca.dispose();
            }
        } else if (evento.getSource() == this.telaBusca.getjButtonFiltar()) {
            if (this.telaBusca.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
            } else {
                DefaultTableModel tabela = (DefaultTableModel) this.telaBusca.getjTableDados().getModel();
                tabela.setRowCount(0);

                if (this.telaBusca.getjCBFiltro().getSelectedIndex() == 0) { // ID
                    ProdutoCopa produto = ProdutoCopaService.Carregar(Integer.parseInt(this.telaBusca.getjTFFiltro().getText()));
                    if (produto != null && produto.getId() != 0) {
                         tabela.addRow(new Object[]{
                            produto.getId(),
                            produto.getDescricao(),
                            produto.getValor(),
                            produto.getCodigoBarra(),
                            produto.getStatus()
                        });
                    }
                } else { // Descrição ou Cód. Barras
                    String filtro = this.telaBusca.getjCBFiltro().getSelectedItem().toString().toLowerCase();
                    List<ProdutoCopa> lista = ProdutoCopaService.Carregar(filtro, this.telaBusca.getjTFFiltro().getText());

                    for (ProdutoCopa produtoAtual : lista) {
                        tabela.addRow(new Object[]{
                            produtoAtual.getId(),
                            produtoAtual.getDescricao(),
                            produtoAtual.getValor(),
                            produtoAtual.getCodigoBarra(),
                            produtoAtual.getStatus()
                        });
                    }
                }
            }
        } else if (evento.getSource() == this.telaBusca.getjButtonSair()) {
            this.telaBusca.dispose();
        }
    }
}