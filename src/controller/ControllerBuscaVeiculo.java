package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
            if (this.telaBuscaVeiculo.getjTableDados().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela!");
            } else {
                ControllerCadVeiculo.codigo = (int) this.telaBuscaVeiculo.getjTableDados()
                        .getValueAt(this.telaBuscaVeiculo.getjTableDados().getSelectedRow(), 0);
                this.telaBuscaVeiculo.dispose();
            }
        } else if (evento.getSource() == this.telaBuscaVeiculo.getjButtonFiltar()) {
            
            if (this.telaBuscaVeiculo.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                 JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
                 return; 
            } 
            
            DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaVeiculo.getjTableDados().getModel();
            tabela.setRowCount(0);

            String filtroSelecionado = this.telaBuscaVeiculo.getjCBFiltro().getSelectedItem().toString();
            String valorFiltro = this.telaBuscaVeiculo.getjTFFiltro().getText();
            String colunaNoBanco;
            List<Veiculo> listaVeiculos = new ArrayList<>();

            switch (filtroSelecionado) {
                case "ID":
                     try {
                        Veiculo veiculo = VeiculoService.Carregar(Integer.parseInt(valorFiltro));
                        if (veiculo != null && veiculo.getId() != 0) {
                             listaVeiculos.add(veiculo);
                        } else {
                             JOptionPane.showMessageDialog(null, "Nenhum veículo encontrado com o ID " + valorFiltro + ".");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "O filtro de ID deve ser um número.", "Erro de Formato", JOptionPane.WARNING_MESSAGE);
                    }
                    break; 

                case "Placa":
                    colunaNoBanco = "placa";
                    listaVeiculos = VeiculoService.Carregar(colunaNoBanco, valorFiltro);
                    break;
                case "Cor":
                    colunaNoBanco = "cor";
                    listaVeiculos = VeiculoService.Carregar(colunaNoBanco, valorFiltro);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção de filtro desconhecida: " + filtroSelecionado);
                    return;
            }

            if (listaVeiculos.isEmpty() && !filtroSelecionado.equalsIgnoreCase("ID")) {
                JOptionPane.showMessageDialog(null, "Nenhum veículo ativo encontrado para o filtro informado.");
            } else {
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