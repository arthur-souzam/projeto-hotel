package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Hospede;
import service.HospedeService; // Necessário importar o Service
import view.TelaBuscaHospede;

public class ControllerBuscaHospede implements ActionListener {

    TelaBuscaHospede telaBuscaHospede;

    public ControllerBuscaHospede(TelaBuscaHospede telaBuscaHospede) {

        this.telaBuscaHospede = telaBuscaHospede;

        this.telaBuscaHospede.getjButtonCarregar().addActionListener(this);
        this.telaBuscaHospede.getjButtonFiltar().addActionListener(this);
        this.telaBuscaHospede.getjButtonSair().addActionListener(this);

        carregarTabela(); // Carrega dados ao iniciar
    }
    
    private void carregarTabela() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaHospede.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<Hospede> listaHospedes = HospedeService.Carregar(); // Busca todos ativos (DAO deve filtrar)

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
                 return; // Para se o filtro estiver vazio
            } 
            
            DefaultTableModel tabela = (DefaultTableModel) this.telaBuscaHospede.getjTableDados().getModel();
            tabela.setRowCount(0);

            String filtroSelecionado = this.telaBuscaHospede.getjCBFiltro().getSelectedItem().toString();
            String valorFiltro = this.telaBuscaHospede.getjTFFiltro().getText();
            String colunaNoBanco;
            List<Hospede> listaHospedes = new ArrayList<>();

            switch (filtroSelecionado) {
                 case "Id": // Alterado para 'Id' (como está no ComboBox)
                     try {
                        Hospede hospede = HospedeService.Carregar(Integer.parseInt(valorFiltro));
                        if (hospede != null && hospede.getId() != 0 && hospede.getStatus() == 'A') {
                           listaHospedes.add(hospede); // Adiciona à lista para usar o loop comum
                        } else if (hospede != null && hospede.getStatus() == 'I'){
                             JOptionPane.showMessageDialog(null, "Hóspede encontrado com ID " + valorFiltro + ", mas está inativo.");
                        } else {
                            // Não precisa de mensagem aqui, o loop abaixo tratará lista vazia
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "O filtro de ID deve ser um número.", "Erro de Formato", JOptionPane.WARNING_MESSAGE);
                    }
                    break; // Continua para o loop que preenche a tabela

                case "Nome": // Alterado para 'Nome' (como está no ComboBox)
                    colunaNoBanco = "nome"; // Assumindo que a coluna se chama 'nome'
                    listaHospedes = HospedeService.Carregar(colunaNoBanco, valorFiltro);
                    break;
                    
                case "CPF": // Alterado para 'CPF' (como está no ComboBox)
                    colunaNoBanco = "cpf"; // Assumindo que a coluna se chama 'cpf'
                    // Remove máscara antes de buscar, se necessário (depende do DAO)
                    // valorFiltro = valorFiltro.replaceAll("[^0-9]", ""); 
                    listaHospedes = HospedeService.Carregar(colunaNoBanco, valorFiltro);
                    break;
                    
                default:
                     JOptionPane.showMessageDialog(null, "Opção de filtro desconhecida: " + filtroSelecionado);
                     return; // Sai se o filtro não for reconhecido
            }

            // Preenche a tabela com os resultados (se houver)
            if (listaHospedes.isEmpty() && !filtroSelecionado.equals("Id")) { // Não mostra para ID se já mostrou inativo
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