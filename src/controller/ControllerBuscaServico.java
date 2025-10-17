package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Servico;
import service.ServicoService;
import view.TelaBuscaServico;

public class ControllerBuscaServico implements ActionListener {

    TelaBuscaServico telaBusca;

    public ControllerBuscaServico(TelaBuscaServico telaBusca) {
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
                ControllerCadServico.codigo = (int) this.telaBusca.getjTableDados()
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
                    Servico servico = ServicoService.Carregar(Integer.parseInt(this.telaBusca.getjTFFiltro().getText()));
                    if (servico != null && servico.getId() != 0) {
                         tabela.addRow(new Object[]{
                            servico.getId(),
                            servico.getDescricao(),
                            servico.getValor(),
                            servico.getStatus()
                        });
                    }
                } else { // Descrição
                    String filtro = this.telaBusca.getjCBFiltro().getSelectedItem().toString().toLowerCase();
                    List<Servico> lista = ServicoService.Carregar(filtro, this.telaBusca.getjTFFiltro().getText());

                    for (Servico servicoAtual : lista) {
                        tabela.addRow(new Object[]{
                            servicoAtual.getId(),
                            servicoAtual.getDescricao(),
                            servicoAtual.getValor(),
                            servicoAtual.getStatus()
                        });
                    }
                }
            }
        } else if (evento.getSource() == this.telaBusca.getjButtonSair()) {
            this.telaBusca.dispose();
        }
    }
}