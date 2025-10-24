package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import javax.swing.JOptionPane;
import model.bo.Hospede;
import service.HospedeService;
import view.TelaBuscaHospede;
import view.TelaCadastroHospede;

public class ControllerCadHospede implements ActionListener {

    TelaCadastroHospede telaCadastroHospede;
    public static int codigo;

    public ControllerCadHospede(TelaCadastroHospede telaCadastroHospede) {

        this.telaCadastroHospede = telaCadastroHospede;

        this.telaCadastroHospede.getjButtonNovo().addActionListener(this);
        this.telaCadastroHospede.getjButtonCancelar().addActionListener(this);
        this.telaCadastroHospede.getjButtonGravar().addActionListener(this);
        this.telaCadastroHospede.getjButtonBuscar().addActionListener(this);
        this.telaCadastroHospede.getjButtonSair().addActionListener(this);
        this.telaCadastroHospede.getjButtonExcluir().addActionListener(this);

        utilities.Utilities.ativaDesativa(this.telaCadastroHospede.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadastroHospede.getjPanelDados(), false);
        
        this.telaCadastroHospede.getjButtonExcluir().setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaCadastroHospede.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroHospede.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(this.telaCadastroHospede.getjPanelDados(), true);
            this.telaCadastroHospede.getjTextFieldId().setEnabled(false);
            
            java.util.Date dataAtual = new Date();
            SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
            String novaData = dataFormatada.format(dataAtual);
            this.telaCadastroHospede.getjFormattedTextFieldDataCadastro().setText(novaData);
            this.telaCadastroHospede.getjFormattedTextFieldDataCadastro().setEnabled(false);

            this.telaCadastroHospede.getjTextFieldNomeFantasia().requestFocus();
            this.telaCadastroHospede.getjButtonExcluir().setEnabled(false);

        } else if (evento.getSource() == this.telaCadastroHospede.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadastroHospede.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadastroHospede.getjPanelDados(), false);
            this.telaCadastroHospede.getjButtonExcluir().setEnabled(false);
            
        } else if (evento.getSource() == this.telaCadastroHospede.getjButtonGravar()) {

            if (this.telaCadastroHospede.getjTextFieldNomeFantasia().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O Atributo Nome é Obrigatório....");
                this.telaCadastroHospede.getjTextFieldNomeFantasia().requestFocus();
                return;
            }
            
            String cpf = this.telaCadastroHospede.getjFormattedTextFieldCpf().getText().replaceAll("[^0-9]", "");
            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(null, "O Atributo CPF é Obrigatório....");
                this.telaCadastroHospede.getjFormattedTextFieldCpf().requestFocus();
                return;
            }
            
            String fone1 = this.telaCadastroHospede.getjFormattedTextFieldFone1().getText().replaceAll("[^0-9]", "");
            if (fone1.isEmpty()) {
                JOptionPane.showMessageDialog(null, "O Atributo Fone1 é Obrigatório....");
                this.telaCadastroHospede.getjFormattedTextFieldFone1().requestFocus();
                return;
            }

            Hospede hospede = new Hospede();
            
            hospede.setNome(this.telaCadastroHospede.getjTextFieldNomeFantasia().getText());
            hospede.setRazaoSocial(this.telaCadastroHospede.getjTextFieldRazaoSocial().getText());
            hospede.setCpf(cpf);
            hospede.setFone1(fone1);
            hospede.setRg(this.telaCadastroHospede.getjTextFieldRg().getText());
            hospede.setCnpj(this.telaCadastroHospede.getjFormattedTextFieldCnpj().getText().replaceAll("[^0-9]", ""));
            hospede.setInscricaoEstadual(this.telaCadastroHospede.getjTextFieldInscricaoEstadual().getText());
            hospede.setFone2(this.telaCadastroHospede.getjFormattedTextFieldFone2().getText().replaceAll("[^0-9]", ""));
            hospede.setEmail(this.telaCadastroHospede.getjTextFieldEmail().getText());
            hospede.setCep(this.telaCadastroHospede.getjFormattedTextFieldCep().getText().replaceAll("[^0-9]", ""));
            hospede.setCidade(this.telaCadastroHospede.getjTextFieldCidade().getText());
            hospede.setBairro(this.telaCadastroHospede.getjTextFieldBairro().getText());
            hospede.setLogradouro(this.telaCadastroHospede.getjTextFieldLogradouro().getText());
            hospede.setComplemento(this.telaCadastroHospede.getjTextFieldComplemento().getText());
            hospede.setContato(this.telaCadastroHospede.getjTextFieldContato().getText());
            hospede.setObs(this.telaCadastroHospede.getjTextFieldObs().getText());

            if (this.telaCadastroHospede.getjTextFieldId().getText().trim().equalsIgnoreCase("")) {
                hospede.setStatus('A');
                service.HospedeService.Criar(hospede);
            } else {
                hospede.setId(Integer.parseInt(this.telaCadastroHospede.getjTextFieldId().getText()));
                
                Hospede hospedeExistente = service.HospedeService.Carregar(hospede.getId());
                hospede.setStatus(hospedeExistente.getStatus());
                
                service.HospedeService.Atualizar(hospede);
            }

            utilities.Utilities.ativaDesativa(this.telaCadastroHospede.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadastroHospede.getjPanelDados(), false);
            this.telaCadastroHospede.getjButtonExcluir().setEnabled(false);
            
        } else if (evento.getSource() == this.telaCadastroHospede.getjButtonBuscar()) {

            codigo = 0;

            TelaBuscaHospede telaBuscaHospede = new TelaBuscaHospede(null, true);
            ControllerBuscaHospede controllerBuscaHospede = new ControllerBuscaHospede(telaBuscaHospede);
            telaBuscaHospede.setVisible(true);

            if (codigo != 0) {
                utilities.Utilities.ativaDesativa(this.telaCadastroHospede.getjPanelBotoes(), false);
                utilities.Utilities.limpaComponentes(this.telaCadastroHospede.getjPanelDados(), true);

                this.telaCadastroHospede.getjTextFieldId().setText(codigo + "");
                this.telaCadastroHospede.getjTextFieldId().setEnabled(false);

                Hospede hospede = new Hospede();
                hospede = service.HospedeService.Carregar(codigo);

                this.telaCadastroHospede.getjTextFieldNomeFantasia().setText(hospede.getNome());
                this.telaCadastroHospede.getjTextFieldRazaoSocial().setText(hospede.getRazaoSocial());
                this.telaCadastroHospede.getjTextFieldRg().setText(hospede.getRg());
                this.telaCadastroHospede.getjFormattedTextFieldCpf().setText(hospede.getCpf());
                this.telaCadastroHospede.getjTextFieldInscricaoEstadual().setText(hospede.getInscricaoEstadual());
                this.telaCadastroHospede.getjFormattedTextFieldCnpj().setText(hospede.getCnpj());
                this.telaCadastroHospede.getjFormattedTextFieldFone1().setText(hospede.getFone1());
                this.telaCadastroHospede.getjFormattedTextFieldFone2().setText(hospede.getFone2());
                this.telaCadastroHospede.getjTextFieldEmail().setText(hospede.getEmail());
                this.telaCadastroHospede.getjFormattedTextFieldCep().setText(hospede.getCep());
                this.telaCadastroHospede.getjTextFieldCidade().setText(hospede.getCidade());
                this.telaCadastroHospede.getjTextFieldBairro().setText(hospede.getBairro());
                this.telaCadastroHospede.getjTextFieldLogradouro().setText(hospede.getLogradouro());
                this.telaCadastroHospede.getjTextFieldComplemento().setText(hospede.getComplemento());
                this.telaCadastroHospede.getjTextFieldContato().setText(hospede.getContato());
                this.telaCadastroHospede.getjTextFieldObs().setText(hospede.getObs());

                this.telaCadastroHospede.getjTextFieldNomeFantasia().requestFocus();
                this.telaCadastroHospede.getjButtonExcluir().setEnabled(true);
            }
        
        } else if (evento.getSource() == this.telaCadastroHospede.getjButtonExcluir()) {
            if (this.telaCadastroHospede.getjTextFieldId().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Você precisa buscar um hóspede antes de excluir.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja INATIVAR este hóspede?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                Hospede hospede = new Hospede();
                hospede.setId(Integer.parseInt(this.telaCadastroHospede.getjTextFieldId().getText()));
                
                HospedeService.Apagar(hospede); 
                
                JOptionPane.showMessageDialog(null, "Hóspede inativado com sucesso!");
                
                utilities.Utilities.ativaDesativa(this.telaCadastroHospede.getjPanelBotoes(), true);
                utilities.Utilities.limpaComponentes(this.telaCadastroHospede.getjPanelDados(), false);
                this.telaCadastroHospede.getjButtonExcluir().setEnabled(false);
            }

        } else if (evento.getSource() == this.telaCadastroHospede.getjButtonSair()) {
            this.telaCadastroHospede.dispose();
        }
    }
}