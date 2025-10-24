package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; 
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
        
        carregarTabela(); // Adicionado para carregar ao iniciar
    }
    
    
    private void carregarTabela() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaBusca.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<Servico> listaServicos = ServicoService.Carregar(); // DAO deve filtrar por 'A'

        for (Servico servicoAtual : listaServicos) {
            tabela.addRow(new Object[]{
                servicoAtual.getId(),
                servicoAtual.getDescricao(),
                String.format("%.2f", servicoAtual.getValor()), 
                servicoAtual.getStatus()
            });
        }
    }


    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaBusca.getjButtonCarregar()) {
            if (this.telaBusca.getjTableDados().getSelectedRow() == -1) { 
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela!");
            } else {
                ControllerCadServico.codigo = (int) this.telaBusca.getjTableDados()
                        .getValueAt(this.telaBusca.getjTableDados().getSelectedRow(), 0);
                this.telaBusca.dispose();
            }
        } else if (evento.getSource() == this.telaBusca.getjButtonFiltar()) {
            
            if (this.telaBusca.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                 JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
                 return; // Para se o filtro estiver vazio
            } 
            
            DefaultTableModel tabela = (DefaultTableModel) this.telaBusca.getjTableDados().getModel();
            tabela.setRowCount(0);
            
            String filtroSelecionado = this.telaBusca.getjCBFiltro().getSelectedItem().toString();
            String valorFiltro = this.telaBusca.getjTFFiltro().getText();
            String colunaNoBanco;
            List<Servico> listaServicos = new ArrayList<>();

            switch(filtroSelecionado){
                case "ID":
                     try {
                        Servico servico = ServicoService.Carregar(Integer.parseInt(valorFiltro));
                        if (servico != null && servico.getId() != 0) { 
                           listaServicos.add(servico); 
                           
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhum serviço encontrado com o ID " + valorFiltro + ".");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "O filtro de ID deve ser um número.", "Erro de Formato", JOptionPane.WARNING_MESSAGE);
                    }
                    break; 

                case "Descrição":
                    colunaNoBanco = "descricao";
                    listaServicos = ServicoService.Carregar(colunaNoBanco, valorFiltro); // DAO filtra por 'A'
                    break;
                    
                 default:
                     JOptionPane.showMessageDialog(null, "Opção de filtro desconhecida: " + filtroSelecionado);
                     return; // Sai se o filtro não for reconhecido
            }


            if (listaServicos.isEmpty() && !filtroSelecionado.equalsIgnoreCase("ID")) {
                 JOptionPane.showMessageDialog(null, "Nenhum serviço ativo encontrado para o filtro informado.");
            } else {
                 for (Servico servicoAtual : listaServicos) {
                    tabela.addRow(new Object[]{
                        servicoAtual.getId(),
                        servicoAtual.getDescricao(),
                        String.format("%.2f", servicoAtual.getValor()),
                        servicoAtual.getStatus()
                    });
                }
            }
            
        } else if (evento.getSource() == this.telaBusca.getjButtonSair()) {
            this.telaBusca.dispose();
        }
    }
}