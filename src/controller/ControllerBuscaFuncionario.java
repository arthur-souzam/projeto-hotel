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
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaBuscaFuncionario.getjButtonCarregar()) {
            if (this.telaBuscaFuncionario.getjTableDados().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Carregar!");
            } else {
                ControllerCadFuncionario.codigo = (int) this.telaBuscaFuncionario.getjTableDados()
                        .getValueAt(this.telaBuscaFuncionario.getjTableDados().getSelectedRow(), 0);
                this.telaBuscaFuncionario.dispose();
            }
        } else if (evento.getSource() == this.telaBuscaFuncionario.getjButtonFiltar()) {
            if (this.telaBuscaFuncionario.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
            } else {
                DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaFuncionario.getjTableDados().getModel();
                tabela.setRowCount(0);

                if (this.telaBuscaFuncionario.getjCBFiltro().getSelectedIndex() == 0) { // ID
                    Funcionario funcionario = FuncionarioService.Carregar(Integer.parseInt(this.telaBuscaFuncionario.getjTFFiltro().getText()));
                    if (funcionario != null) {
                         tabela.addRow(new Object[]{
                            funcionario.getId(),
                            funcionario.getNome(),
                            funcionario.getCpf(),
                            funcionario.getUsuario(),
                            funcionario.getStatus()
                        });
                    }
                } else {
                    String filtro = this.telaBuscaFuncionario.getjCBFiltro().getSelectedItem().toString().toLowerCase();
                    if (filtro.equalsIgnoreCase("usuário")) {
                        filtro = "login";
                    }
                    
                    List<Funcionario> listaFuncionarios = FuncionarioService.Carregar(filtro, this.telaBuscaFuncionario.getjTFFiltro().getText());

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
            }
        } else if (evento.getSource() == this.telaBuscaFuncionario.getjButtonSair()) {
            this.telaBuscaFuncionario.dispose();
        }
    }
}