package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.bo.Funcionario;
import service.FuncionarioService;
import view.TelaBuscaFuncionario;
import view.TelaCadastroFuncionario;

public class ControllerCadFuncionario implements ActionListener {

    TelaCadastroFuncionario telaCadFuncionario;
    public static int codigo;

    public ControllerCadFuncionario(TelaCadastroFuncionario telaCadFuncionario) {
        this.telaCadFuncionario = telaCadFuncionario;

        this.telaCadFuncionario.getjButtonNovo().addActionListener(this);
        this.telaCadFuncionario.getjButtonGravar().addActionListener(this);
        this.telaCadFuncionario.getjButtonCancelar().addActionListener(this);
        this.telaCadFuncionario.getjButtonBuscar().addActionListener(this);
        this.telaCadFuncionario.getjButtonSair().addActionListener(this);

        utilities.Utilities.ativaDesativa(this.telaCadFuncionario.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaCadFuncionario.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadFuncionario.getjButtonNovo()) {
            utilities.Utilities.ativaDesativa(this.telaCadFuncionario.getjPanelBotoes(), false);
            utilities.Utilities.limpaComponentes(this.telaCadFuncionario.getjPanelDados(), true);
            this.telaCadFuncionario.getjTextFieldId().setEnabled(false);

        } else if (e.getSource() == this.telaCadFuncionario.getjButtonGravar()) {
            Funcionario funcionario = new Funcionario();
            
            funcionario.setNome(this.telaCadFuncionario.getjTextFieldNome().getText());
            funcionario.setCpf(this.telaCadFuncionario.getjFormattedTextFieldCpf().getText());
            funcionario.setRg(this.telaCadFuncionario.getjTextFieldRg().getText());
            funcionario.setFone1(this.telaCadFuncionario.getjTextFieldFone1().getText());
            funcionario.setFone2(this.telaCadFuncionario.getjTextFieldFone2().getText());
            funcionario.setEmail(this.telaCadFuncionario.getjTextFieldEmail().getText());
            funcionario.setCep(this.telaCadFuncionario.getjFormattedTextFieldCep().getText());
            funcionario.setLogradouro(this.telaCadFuncionario.getjTextFieldLogradouro().getText());
            funcionario.setBairro(this.telaCadFuncionario.getjTextFieldBairro().getText());
            funcionario.setCidade(this.telaCadFuncionario.getjTextFieldCidade().getText());
            funcionario.setComplemento(this.telaCadFuncionario.getjTextFieldComplemento().getText());
            funcionario.setObs(this.telaCadFuncionario.getjTextFieldObs().getText());
            funcionario.setUsuario(this.telaCadFuncionario.getjTextFieldUsuario().getText());
            funcionario.setSenha(new String(this.telaCadFuncionario.getjPasswordFieldSenha().getPassword()));
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            funcionario.setDataCadastro(dateFormat.format(new Date()));
            
            funcionario.setStatus('A');

            if (this.telaCadFuncionario.getjTextFieldId().getText().equalsIgnoreCase("")) {
                FuncionarioService.Criar(funcionario);
            } else {
                funcionario.setId(Integer.parseInt(this.telaCadFuncionario.getjTextFieldId().getText()));
                FuncionarioService.Atualizar(funcionario);
            }
            
            utilities.Utilities.ativaDesativa(this.telaCadFuncionario.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadFuncionario.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadFuncionario.getjButtonCancelar()) {
            utilities.Utilities.ativaDesativa(this.telaCadFuncionario.getjPanelBotoes(), true);
            utilities.Utilities.limpaComponentes(this.telaCadFuncionario.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadFuncionario.getjButtonBuscar()) {
            codigo = 0;
            
            TelaBuscaFuncionario telaBuscaFuncionario = new TelaBuscaFuncionario(null, true);
            ControllerBuscaFuncionario controllerBuscaFuncionario = new ControllerBuscaFuncionario(telaBuscaFuncionario);
            telaBuscaFuncionario.setVisible(true);

            if (codigo != 0) {
                Funcionario funcionario;
                funcionario = FuncionarioService.Carregar(codigo);
                
                utilities.Utilities.ativaDesativa(this.telaCadFuncionario.getjPanelBotoes(), false);
                utilities.Utilities.limpaComponentes(this.telaCadFuncionario.getjPanelDados(), true);

                this.telaCadFuncionario.getjTextFieldId().setText(String.valueOf(funcionario.getId()));
                this.telaCadFuncionario.getjTextFieldNome().setText(funcionario.getNome());
                this.telaCadFuncionario.getjFormattedTextFieldCpf().setText(funcionario.getCpf());
                this.telaCadFuncionario.getjTextFieldRg().setText(funcionario.getRg());
                this.telaCadFuncionario.getjTextFieldFone1().setText(funcionario.getFone1());
                this.telaCadFuncionario.getjTextFieldFone2().setText(funcionario.getFone2());
                this.telaCadFuncionario.getjTextFieldEmail().setText(funcionario.getEmail());
                this.telaCadFuncionario.getjFormattedTextFieldCep().setText(funcionario.getCep());
                this.telaCadFuncionario.getjTextFieldLogradouro().setText(funcionario.getLogradouro());
                this.telaCadFuncionario.getjTextFieldBairro().setText(funcionario.getBairro());
                this.telaCadFuncionario.getjTextFieldCidade().setText(funcionario.getCidade());
                this.telaCadFuncionario.getjTextFieldComplemento().setText(funcionario.getComplemento());
                this.telaCadFuncionario.getjTextFieldObs().setText(funcionario.getObs());
                this.telaCadFuncionario.getjTextFieldUsuario().setText(funcionario.getUsuario());
                this.telaCadFuncionario.getjPasswordFieldSenha().setText(funcionario.getSenha());
                
                this.telaCadFuncionario.getjTextFieldId().setEnabled(false);
            }
            
        } else if (e.getSource() == this.telaCadFuncionario.getjButtonSair()) {
            this.telaCadFuncionario.dispose();
        }
    }
}