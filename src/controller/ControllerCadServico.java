package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.bo.Servico;
import service.ServicoService;
import view.TelaBuscaServico;
import view.TelaCadastroServico;

public class ControllerCadServico implements ActionListener {

    TelaCadastroServico telaCadServico;
    public static int codigo;

    public ControllerCadServico(TelaCadastroServico telaCadServico) {
        this.telaCadServico = telaCadServico;

        this.telaCadServico.getjButtonNovo().addActionListener(this);
        this.telaCadServico.getjButtonGravar().addActionListener(this);
        this.telaCadServico.getjButtonCancelar().addActionListener(this);
        this.telaCadServico.getjButtonBuscar().addActionListener(this);
        this.telaCadServico.getjButtonSair().addActionListener(this);

        utilities.Utilities.ativaDesativa(this.telaCadServico.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadServico.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadServico.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), true);
            this.telaCadServico.getjTextFieldId().setEnabled(false);

        } else if (e.getSource() == this.telaCadServico.getjButtonGravar()) {
            Servico servico = new Servico();
            servico.setDescricao(this.telaCadServico.getjTextFieldDescricao().getText());
            servico.setValor(Float.parseFloat(this.telaCadServico.getjTextFieldValor().getText()));
            servico.setStatus('A');

            if (this.telaCadServico.getjTextFieldId().getText().equalsIgnoreCase("")) {
                ServicoService.Criar(servico);
            } else {
                servico.setId(Integer.parseInt(this.telaCadServico.getjTextFieldId().getText()));
                ServicoService.Atualizar(servico);
            }
            
            utilities.Utilities.ativaDesativa(this.telaCadServico.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadServico.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadServico.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadServico.getjButtonBuscar()) {
            codigo = 0;
            
            TelaBuscaServico telaBusca = new TelaBuscaServico(null, true);
            /*ERRO DE MERDA ControllerBuscaServico controllerBusca = new ControllerBuscaServico(telaBusca);*/
            telaBusca.setVisible(true);

            if (codigo != 0) {
                Servico servico = ServicoService.Carregar(codigo);
                
                utilities.Utilities.ativaDesativa(this.telaCadServico.getjPanelBotoes(), false);
                utilities.Utilities.limpaComponentes(this.telaCadServico.getjPanelDados(), true);

                this.telaCadServico.getjTextFieldId().setText(String.valueOf(servico.getId()));
                this.telaCadServico.getjTextFieldDescricao().setText(servico.getDescricao());
                this.telaCadServico.getjTextFieldValor().setText(String.valueOf(servico.getValor()));
                
                this.telaCadServico.getjTextFieldId().setEnabled(false);
            }
            
        } else if (e.getSource() == this.telaCadServico.getjButtonSair()) {
            this.telaCadServico.dispose();
        }
    }
}