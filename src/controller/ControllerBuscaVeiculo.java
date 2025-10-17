package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Veiculo;
import service.VeiculoService;
import view.TelaBuscaVeiculo;

public class ControllerBuscaVeiculo implements ActionListener {

    TelaBuscaVeiculo telaBuscaVeiculo;

    public ControllerBuscaVeiculo(TelaBuscaVeiculo telaBuscaVeiculo) {
        this.telaBuscaVeiculo = telaBuscaVeiculo;

        this.telaBuscaVeiculo.getjButtonCarregar().addActionListener(this);
        this.telaBuscaVeiculo.getjButtonFiltar().addActionListener(this);
        this.telaBuscaVeiculo.getjButtonSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaBuscaVeiculo.getjButtonCarregar()) {
            if (this.telaBuscaVeiculo.getjTableDados().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Carregar!");
            } else {
                ControllerCadVeiculo.codigo = (int) this.telaBuscaVeiculo.getjTableDados()
                        .getValueAt(this.telaBuscaVeiculo.getjTableDados().getSelectedRow(), 0);
                this.telaBuscaVeiculo.dispose();
            }
        } else if (evento.getSource() == this.telaBuscaVeiculo.getjButtonFiltar()) {
            if (this.telaBuscaVeiculo.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
            } else {
                DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaVeiculo.getjTableDados().getModel();
                tabela.setRowCount(0);

                if (this.telaBuscaVeiculo.getjCBFiltro().getSelectedIndex() == 0) { // ID
                    Veiculo veiculo = VeiculoService.Carregar(Integer.parseInt(this.telaBuscaVeiculo.getjTFFiltro().getText()));
                    if (veiculo != null && veiculo.getId() != 0) {
                         tabela.addRow(new Object[]{
                            veiculo.getId(),
                            veiculo.getPlaca(),
                            veiculo.getCor(),
                            veiculo.getModelo().getDescricao(),
                            veiculo.getStatus()
                        });
                    }
                } else { // Placa, Cor
                    String filtro = this.telaBuscaVeiculo.getjCBFiltro().getSelectedItem().toString().toLowerCase();
                    List<Veiculo> listaVeiculos = VeiculoService.Carregar(filtro, this.telaBuscaVeiculo.getjTFFiltro().getText());

                    for (Veiculo veiculoAtual : listaVeiculos) {
                        tabela.addRow(new Object[]{
                            veiculoAtual.getId(),
                            veiculoAtual.getPlaca(),
                            veiculoAtual.getCor(),
                            veiculoAtual.getModelo().getDescricao(),
                            veiculoAtual.getStatus()
                        });
                    }
                }
            }
        } else if (evento.getSource() == this.telaBuscaVeiculo.getjButtonSair()) {
            this.telaBuscaVeiculo.dispose();
        }
    }
}