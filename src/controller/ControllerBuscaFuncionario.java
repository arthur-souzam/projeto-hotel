package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Funcionario;
import service.FuncionarioService;
import view.TelaBuscaFuncionario;

public class ControllerBuscaFuncionario implements ActionListener {

    TelaBuscaFuncionario telaBuscaFuncionario;

    public ControllerBuscaFuncionario(TelaBuscaFuncionario telaBuscaFuncionario) {
        this.telaBuscaFuncionario = telaBuscaFuncionario;

        this.telaBuscaFuncionario.getjButtonCarregar().addActionListener(this);
        this.telaBuscaFuncionario.getjButtonFiltar().addActionListener(this);
        this.telaBuscaFuncionario.getjButtonSair().addActionListener(this);
        
        carregarTabela();
    }
    
    private void carregarTabela() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaFuncionario.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<Funcionario> listaFuncionarios = FuncionarioService.Carregar(); 

        for (Funcionario funcionarioAtual : listaFuncionarios) {
            tabela.addRow(new Object[]{
                funcionarioAtual.getId(),
                funcionarioAtual.getNome(),
                funcionarioAtual.getCpf(),
                funcionarioAtual.getUsuario(),
                funcionarioAtual.getStatus()
            });
        }
    }


    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaBuscaFuncionario.getjButtonCarregar()) {
             if (this.telaBuscaFuncionario.getjTableDados().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela!");
            } else {
                ControllerCadFuncionario.codigo = (int) this.telaBuscaFuncionario.getjTableDados()
                        .getValueAt(this.telaBuscaFuncionario.getjTableDados().getSelectedRow(), 0);
                this.telaBuscaFuncionario.dispose();
            }
        } else if (evento.getSource() == this.telaBuscaFuncionario.getjButtonFiltar()) {
            
            if (this.telaBuscaFuncionario.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                 JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
                 return; 
            } 
            
            DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaFuncionario.getjTableDados().getModel();
            tabela.setRowCount(0);
            
            String filtroSelecionado = this.telaBuscaFuncionario.getjCBFiltro().getSelectedItem().toString();
            String valorFiltro = this.telaBuscaFuncionario.getjTFFiltro().getText();
            String colunaNoBanco;
            List<Funcionario> listaFuncionarios = new ArrayList<>();

            switch (filtroSelecionado) {
                 case "ID":
                     try {
                        Funcionario funcionario = FuncionarioService.Carregar(Integer.parseInt(valorFiltro));
                        if (funcionario != null && funcionario.getId() != 0) {
                           listaFuncionarios.add(funcionario);
                        } else {
                           JOptionPane.showMessageDialog(null, "Nenhum funcionário encontrado com o ID " + valorFiltro + ".");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "O filtro de ID deve ser um número.", "Erro de Formato", JOptionPane.WARNING_MESSAGE);
                    }
                    break; 

                case "Nome":
                    colunaNoBanco = "nome";
                    listaFuncionarios = FuncionarioService.Carregar(colunaNoBanco, valorFiltro);
                    break;
                    
                case "CPF":
                    colunaNoBanco = "cpf";
                    listaFuncionarios = FuncionarioService.Carregar(colunaNoBanco, valorFiltro);
                    break;
                    
                case "Usuário": 
                    colunaNoBanco = "usuario";
                    listaFuncionarios = FuncionarioService.Carregar(colunaNoBanco, valorFiltro);
                    break;
                    
                default:
                     JOptionPane.showMessageDialog(null, "Opção de filtro desconhecida: " + filtroSelecionado);
                     return;
            }

            if (listaFuncionarios.isEmpty() && !filtroSelecionado.equalsIgnoreCase("ID")) {
                 JOptionPane.showMessageDialog(null, "Nenhum funcionário ativo encontrado para o filtro informado.");
            } else {
                for (Funcionario funcionarioAtual : listaFuncionarios) {
                    tabela.addRow(new Object[]{
                        funcionarioAtual.getId(),
                        funcionarioAtual.getNome(),
                        funcionarioAtual.getCpf(),
                        funcionarioAtual.getUsuario(),
                        funcionarioAtual.getStatus()
                    });
                }
            }
            
        } else if (evento.getSource() == this.telaBuscaFuncionario.getjButtonSair()) {
            this.telaBuscaFuncionario.dispose();
        }
    }
}