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

        carregarTabela();
    }

    private void carregarTabela() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaVeiculo.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<Veiculo> listaVeiculos = VeiculoService.Carregar();

        for (Veiculo veiculoAtual : listaVeiculos) {
             tabela.addRow(new Object[]{
                veiculoAtual.getId(),
                veiculoAtual.getPlaca(),
                veiculoAtual.getCor(),
                (veiculoAtual.getModelo() != null ? veiculoAtual.getModelo().getDescricao() : ""),
                veiculoAtual.getStatus()
            });
        }
    }


    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaBuscaVeiculo.getjButtonCarregar()) {
            if (this.telaBuscaVeiculo.getjTableDados().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Não Existem Dados Selecionados para Carregar!");
            } else {
                 try {
                    ControllerCadVeiculo.codigo = (int) this.telaBuscaVeiculo.getjTableDados()
                            .getValueAt(this.telaBuscaVeiculo.getjTableDados().getSelectedRow(), 0);
                    this.telaBuscaVeiculo.dispose();
                 } catch (ArrayIndexOutOfBoundsException ex) {
                     JOptionPane.showMessageDialog(null, "Selecione um registro na tabela!");
                 }
            }
        } else if (evento.getSource() == this.telaBuscaVeiculo.getjButtonFiltar()) {
            if (this.telaBuscaVeiculo.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                carregarTabela();
            } else {
                DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaVeiculo.getjTableDados().getModel();
                tabela.setRowCount(0);

                String filtroSelecionado = this.telaBuscaVeiculo.getjCBFiltro().getSelectedItem().toString();
                String valorFiltro = this.telaBuscaVeiculo.getjTFFiltro().getText();
                String colunaNoBanco;

                switch (filtroSelecionado) {
                    case "ID":
                         try {
                            Veiculo veiculo = VeiculoService.Carregar(Integer.parseInt(valorFiltro));
                            if (veiculo != null && veiculo.getId() != 0 && veiculo.getStatus() == 'A') {
                                 tabela.addRow(new Object[]{
                                    veiculo.getId(),
                                    veiculo.getPlaca(),
                                    veiculo.getCor(),
                                    (veiculo.getModelo() != null ? veiculo.getModelo().getDescricao() : ""),
                                    veiculo.getStatus()
                                });
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "O filtro de ID deve ser um número.", "Erro de Formato", JOptionPane.WARNING_MESSAGE);
                        }
                        return;

                    case "Placa":
                        colunaNoBanco = "placa";
                        break;
                    case "Cor":
                        colunaNoBanco = "cor";
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção de filtro desconhecida: " + filtroSelecionado);
                        return;
                }

                List<Veiculo> listaVeiculos = VeiculoService.Carregar(colunaNoBanco, valorFiltro);

                for (Veiculo veiculoAtual : listaVeiculos) {
                    tabela.addRow(new Object[]{
                        veiculoAtual.getId(),
                        veiculoAtual.getPlaca(),
                        veiculoAtual.getCor(),
                        (veiculoAtual.getModelo() != null ? veiculoAtual.getModelo().getDescricao() : ""),
                        veiculoAtual.getStatus()
                    });
                }
            }
        } else if (evento.getSource() == this.telaBuscaVeiculo.getjButtonSair()) {
            this.telaBuscaVeiculo.dispose();
        }
    }
}