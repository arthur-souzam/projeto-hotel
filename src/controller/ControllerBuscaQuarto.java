package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Quarto;
import service.QuartoService;
import view.TelaBuscaQuarto;

public class ControllerBuscaQuarto implements ActionListener {

    TelaBuscaQuarto telaBuscaQuarto;

    public ControllerBuscaQuarto(TelaBuscaQuarto telaBuscaQuarto) {
        this.telaBuscaQuarto = telaBuscaQuarto;

        this.telaBuscaQuarto.getjButtonCarregar().addActionListener(this);
        this.telaBuscaQuarto.getjButtonFiltar().addActionListener(this);
        this.telaBuscaQuarto.getjButtonSair().addActionListener(this);
        
        carregarTabela();
    }
    
    private void carregarTabela() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaQuarto.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<Quarto> listaQuartos = QuartoService.Carregar(); 

        for (Quarto quartoAtual : listaQuartos) {
            tabela.addRow(new Object[]{
                quartoAtual.getId(),
                quartoAtual.getDescricao(),
                quartoAtual.getIdentificacao(),
                quartoAtual.getAndar(),
                quartoAtual.getStatus()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaBuscaQuarto.getjButtonCarregar()) {
             if (this.telaBuscaQuarto.getjTableDados().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela!");
            } else {
                ControllerCadQuarto.codigo = (int) this.telaBuscaQuarto.getjTableDados()
                        .getValueAt(this.telaBuscaQuarto.getjTableDados().getSelectedRow(), 0);
                this.telaBuscaQuarto.dispose();
            }
        } else if (evento.getSource() == this.telaBuscaQuarto.getjButtonFiltar()) {
            
            if (this.telaBuscaQuarto.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                 JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
                 return; 
            } 
            
            DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaQuarto.getjTableDados().getModel();
            tabela.setRowCount(0);
            
            String filtroSelecionado = this.telaBuscaQuarto.getjCBFiltro().getSelectedItem().toString();
            String valorFiltro = this.telaBuscaQuarto.getjTFFiltro().getText();
            String colunaNoBanco;
            List<Quarto> listaQuartos = new ArrayList<>();

            switch (filtroSelecionado) {
                 case "ID":
                     try {
                        Quarto quarto = QuartoService.Carregar(Integer.parseInt(valorFiltro));
                        if (quarto != null && quarto.getId() != 0) {
                           listaQuartos.add(quarto);
                        } else {
                             JOptionPane.showMessageDialog(null, "Nenhum quarto encontrado com o ID " + valorFiltro + ".");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "O filtro de ID deve ser um número.", "Erro de Formato", JOptionPane.WARNING_MESSAGE);
                    }
                    break; 

                case "Descrição":
                    colunaNoBanco = "descricao";
                    listaQuartos = QuartoService.Carregar(colunaNoBanco, valorFiltro);
                    break;
                    
                case "Identificação":
                    colunaNoBanco = "identificacao";
                    listaQuartos = QuartoService.Carregar(colunaNoBanco, valorFiltro);
                    break;

                case "Andar":
                     colunaNoBanco = "andar";
                     listaQuartos = QuartoService.Carregar(colunaNoBanco, valorFiltro);
                     break;
                    
                default:
                     JOptionPane.showMessageDialog(null, "Opção de filtro desconhecida: " + filtroSelecionado);
                     return;
            }

            if (listaQuartos.isEmpty() && !filtroSelecionado.equalsIgnoreCase("ID")) {
                 JOptionPane.showMessageDialog(null, "Nenhum quarto ativo encontrado para o filtro informado.");
            } else {
                for (Quarto quartoAtual : listaQuartos) {
                    tabela.addRow(new Object[]{
                        quartoAtual.getId(),
                        quartoAtual.getDescricao(),
                        quartoAtual.getIdentificacao(),
                        quartoAtual.getAndar(),
                        quartoAtual.getStatus()
                    });
                }
            }
            
        } else if (evento.getSource() == this.telaBuscaQuarto.getjButtonSair()) {
            this.telaBuscaQuarto.dispose();
        }
    }
}