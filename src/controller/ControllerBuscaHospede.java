package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Hospede;
import service.HospedeService; 
import view.TelaBuscaHospede;

public class ControllerBuscaHospede implements ActionListener {

    TelaBuscaHospede telaBuscaHospede;

    public ControllerBuscaHospede(TelaBuscaHospede telaBuscaHospede) {

        this.telaBuscaHospede = telaBuscaHospede;

        this.telaBuscaHospede.getjButtonCarregar().addActionListener(this);
        this.telaBuscaHospede.getjButtonFiltar().addActionListener(this);
        this.telaBuscaHospede.getjButtonSair().addActionListener(this);

        carregarTabela(); 
    }
    
    private void carregarTabela() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaHospede.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<Hospede> listaHospedes = HospedeService.Carregar(); 

        for (Hospede hospedeAtual : listaHospedes) {
            tabela.addRow(new Object[]{
                hospedeAtual.getId(),
                hospedeAtual.getNome(),
                hospedeAtual.getCpf(),
                hospedeAtual.getStatus()});
        }
    }


    @Override
    public void actionPerformed(ActionEvent evento) {

        if (evento.getSource() == this.telaBuscaHospede.getjButtonCarregar()) {
            if (this.telaBuscaHospede.getjTableDados().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela!");
            } else {
                ControllerCadHospede.codigo = (int) this.telaBuscaHospede.getjTableDados().
                        getValueAt(this.telaBuscaHospede.getjTableDados().getSelectedRow(), 0);
                this.telaBuscaHospede.dispose();
            }
        } else if (evento.getSource() == this.telaBuscaHospede.getjButtonFiltar()) {
            
            if (this.telaBuscaHospede.getjTFFiltro().getText().trim().equalsIgnoreCase("")) {
                 JOptionPane.showMessageDialog(null, "Informe o valor do filtro.");
                 return; 
            } 
            
            DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaHospede.getjTableDados().getModel();
            tabela.setRowCount(0);

            String filtroSelecionado = this.telaBuscaHospede.getjCBFiltro().getSelectedItem().toString();
            String valorFiltro = this.telaBuscaHospede.getjTFFiltro().getText();
            String colunaNoBanco;
            List<Hospede> listaHospedes = new ArrayList<>();

            switch (filtroSelecionado) {
                 case "Id":
                     try {
                        Hospede hospede = HospedeService.Carregar(Integer.parseInt(valorFiltro));
                        if (hospede != null && hospede.getId() != 0) {
                           listaHospedes.add(hospede);
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhum hóspede encontrado com o ID " + valorFiltro + ".");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "O filtro de ID deve ser um número.", "Erro de Formato", JOptionPane.WARNING_MESSAGE);
                    }
                    break; 

                case "Nome":
                    colunaNoBanco = "nome"; 
                    listaHospedes = HospedeService.Carregar(colunaNoBanco, valorFiltro);
                    break;
                    
                case "CPF":
                    colunaNoBanco = "cpf"; 
                    listaHospedes = HospedeService.Carregar(colunaNoBanco, valorFiltro);
                    break;
                    
                default:
                     JOptionPane.showMessageDialog(null, "Opção de filtro desconhecida: " + filtroSelecionado);
                     return; 
            }

            if (listaHospedes.isEmpty() && !filtroSelecionado.equalsIgnoreCase("Id")) {
                 JOptionPane.showMessageDialog(null, "Nenhum hóspede ativo encontrado para o filtro informado.");
            } else {
                for (Hospede hospedeAtualDaLista : listaHospedes) {
                    tabela.addRow(new Object[]{
                        hospedeAtualDaLista.getId(),
                        hospedeAtualDaLista.getNome(),
                        hospedeAtualDaLista.getCpf(),
                        hospedeAtualDaLista.getStatus()});
                }
            }
            
        } else if (evento.getSource() == this.telaBuscaHospede.getjButtonSair()) {
            this.telaBuscaHospede.dispose();
        }
    }
}