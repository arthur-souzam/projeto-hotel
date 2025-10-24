package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Fornecedor;
import service.FornecedorService;
import view.TelaBuscaFornecedor;

public class ControllerBuscaFornecedor implements ActionListener {

    TelaBuscaFornecedor telaBuscaFornecedor;

    public ControllerBuscaFornecedor(TelaBuscaFornecedor telaBuscaFornecedor) {
        this.telaBuscaFornecedor = telaBuscaFornecedor;

        this.telaBuscaFornecedor.getjButtonCarregar().addActionListener(this);
        this.telaBuscaFornecedor.getjButtonFiltar().addActionListener(this);
        this.telaBuscaFornecedor.getjButtonSair().addActionListener(this);
        
        carregarTabela();
    }
    
    private void carregarTabela() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaFornecedor.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<Fornecedor> listaFornecedores = FornecedorService.Carregar(); 

        for (Fornecedor fornecedorAtual : listaFornecedores) {
            tabela.addRow(new Object[]{
                fornecedorAtual.getId(),
                fornecedorAtual.getNome(),
                fornecedorAtual.getRazaoSocial(),
                fornecedorAtual.getCnpj(),
                fornecedorAtual.getStatus()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaBuscaFornecedor.getjButtonCarregar()) {
             if (this.telaBuscaFornecedor.getjTableDados().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela!");
            } else {
                ControllerCadFornecedor.codigo = (int) this.telaBuscaFornecedor.getjTableDados()
                        .getValueAt(this.telaBuscaFornecedor.getjTableDados().getSelectedRow(), 0);
                this.telaBuscaFornecedor.dispose();
            }
        } else if (evento.getSource() == this.telaBuscaFornecedor.getjButtonFiltar()) {
            
            if (this.telaBuscaFornecedor.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                 JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
                 return; 
            } 
            
            DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaFornecedor.getjTableDados().getModel();
            tabela.setRowCount(0);
            
            String filtroSelecionado = this.telaBuscaFornecedor.getjCBFiltro().getSelectedItem().toString();
            String valorFiltro = this.telaBuscaFornecedor.getjTFFiltro().getText();
            String colunaNoBanco;
            List<Fornecedor> listaFornecedores = new ArrayList<>();

            switch (filtroSelecionado) {
                 case "ID":
                     try {
                        Fornecedor fornecedor = FornecedorService.Carregar(Integer.parseInt(valorFiltro));
                        if (fornecedor != null && fornecedor.getId() != 0) {
                           listaFornecedores.add(fornecedor);
                            if (fornecedor.getStatus() == 'I'){
                                 JOptionPane.showMessageDialog(null, "Fornecedor encontrado com ID " + valorFiltro + ", mas está inativo.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhum fornecedor encontrado com o ID " + valorFiltro + ".");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "O filtro de ID deve ser um número.", "Erro de Formato", JOptionPane.WARNING_MESSAGE);
                    }
                    break; 

                case "Nome":
                    colunaNoBanco = "nome";
                    listaFornecedores = FornecedorService.Carregar(colunaNoBanco, valorFiltro);
                    break;
                    
                case "Razao Social":
                    colunaNoBanco = "razao_social"; // Mapear para nome correto da coluna
                    listaFornecedores = FornecedorService.Carregar(colunaNoBanco, valorFiltro);
                    break;

                case "CNPJ":
                     colunaNoBanco = "cnpj";
                     listaFornecedores = FornecedorService.Carregar(colunaNoBanco, valorFiltro);
                     break;
                    
                default:
                     JOptionPane.showMessageDialog(null, "Opção de filtro desconhecida: " + filtroSelecionado);
                     return;
            }

            if (listaFornecedores.isEmpty() && !filtroSelecionado.equalsIgnoreCase("ID")) {
                 JOptionPane.showMessageDialog(null, "Nenhum fornecedor ativo encontrado para o filtro informado.");
            } else {
                for (Fornecedor fornecedorAtual : listaFornecedores) {
                    tabela.addRow(new Object[]{
                        fornecedorAtual.getId(),
                        fornecedorAtual.getNome(),
                        fornecedorAtual.getRazaoSocial(),
                        fornecedorAtual.getCnpj(),
                        fornecedorAtual.getStatus()
                    });
                }
            }
            
        } else if (evento.getSource() == this.telaBuscaFornecedor.getjButtonSair()) {
            this.telaBuscaFornecedor.dispose();
        }
    }
}