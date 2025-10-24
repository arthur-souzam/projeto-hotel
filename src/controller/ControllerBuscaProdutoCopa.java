package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
        
        carregarTabela();
    }
    
    private void carregarTabela() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaBusca.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<ProdutoCopa> listaProdutos = ProdutoCopaService.Carregar(); 

        for (ProdutoCopa produtoAtual : listaProdutos) {
            tabela.addRow(new Object[]{
                produtoAtual.getId(),
                produtoAtual.getDescricao(),
                String.format("%.2f", produtoAtual.getValor()), // Formata valor para exibição
                produtoAtual.getCodigoBarra(),
                produtoAtual.getStatus()
            });
        }
    }


    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaBusca.getjButtonCarregar()) {
            if (this.telaBusca.getjTableDados().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela!");
            } else {
                ControllerCadProdutoCopa.codigo = (int) this.telaBusca.getjTableDados()
                        .getValueAt(this.telaBusca.getjTableDados().getSelectedRow(), 0);
                this.telaBusca.dispose();
            }
        } else if (evento.getSource() == this.telaBusca.getjButtonFiltar()) {
            
            if (this.telaBusca.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
                 return;
            } 
            
            DefaultTableModel tabela = (DefaultTableModel) this.telaBusca.getjTableDados().getModel();
            tabela.setRowCount(0);
            
            String filtroSelecionado = this.telaBusca.getjCBFiltro().getSelectedItem().toString();
            String valorFiltro = this.telaBusca.getjTFFiltro().getText();
            String colunaNoBanco;
            List<ProdutoCopa> listaProdutos = new ArrayList<>();

            switch (filtroSelecionado) {
                 case "ID":
                     try {
                        ProdutoCopa produto = ProdutoCopaService.Carregar(Integer.parseInt(valorFiltro));
                        if (produto != null && produto.getId() != 0 && produto.getStatus() == 'A') {
                           listaProdutos.add(produto);
                        } else if (produto != null && produto.getStatus() == 'I'){
                             JOptionPane.showMessageDialog(null, "Produto encontrado com ID " + valorFiltro + ", mas está inativo.");
                        } else {
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "O filtro de ID deve ser um número.", "Erro de Formato", JOptionPane.WARNING_MESSAGE);
                    }
                    break; 

                case "Descrição":
                    colunaNoBanco = "descricao";
                    listaProdutos = ProdutoCopaService.Carregar(colunaNoBanco, valorFiltro);
                    break;
                    
                case "codigo_barra": // Usa o nome da coluna como está no ComboBox e DAO
                    colunaNoBanco = "codigo_barra";
                    listaProdutos = ProdutoCopaService.Carregar(colunaNoBanco, valorFiltro);
                    break;
                    
                default:
                     JOptionPane.showMessageDialog(null, "Opção de filtro desconhecida: " + filtroSelecionado);
                     return;
            }

            if (listaProdutos.isEmpty() && !filtroSelecionado.equals("ID")) {
                 JOptionPane.showMessageDialog(null, "Nenhum produto ativo encontrado para o filtro informado.");
            } else {
                for (ProdutoCopa produtoAtual : listaProdutos) {
                    tabela.addRow(new Object[]{
                        produtoAtual.getId(),
                        produtoAtual.getDescricao(),
                        String.format("%.2f", produtoAtual.getValor()),
                        produtoAtual.getCodigoBarra(),
                        produtoAtual.getStatus()
                    });
                }
            }
            
        } else if (evento.getSource() == this.telaBusca.getjButtonSair()) {
            this.telaBusca.dispose();
        }
    }
}