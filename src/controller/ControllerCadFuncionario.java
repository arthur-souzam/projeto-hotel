package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
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
        this.telaCadFuncionario.getjButtonExcluir().addActionListener(this);

        ativaDesativa(true); 
        utilities.Utilities.limpaComponentes(this.telaCadFuncionario.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadFuncionario.getjButtonNovo()) {
            ativaDesativa(false);
            utilities.Utilities.limpaComponentes(this.telaCadFuncionario.getjPanelDados(), true);
            this.telaCadFuncionario.getjTextFieldId().setEnabled(false);

        } else if (e.getSource() == this.telaCadFuncionario.getjButtonGravar()) {
            
            String nome = this.telaCadFuncionario.getjTextFieldNome().getText();
            String usuario = this.telaCadFuncionario.getjTextFieldUsuario().getText();
            String senha = new String(this.telaCadFuncionario.getjPasswordFieldSenha().getPassword());

            if (nome.isBlank()) {
                 JOptionPane.showMessageDialog(this.telaCadFuncionario, "O nome é obrigatório!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                 this.telaCadFuncionario.getjTextFieldNome().requestFocus();
                 return;
            }
            
            String cpf = this.telaCadFuncionario.getjFormattedTextFieldCpf().getText().replaceAll("[^0-9]", "");
            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this.telaCadFuncionario, "O CPF é obrigatório!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                this.telaCadFuncionario.getjFormattedTextFieldCpf().requestFocus();
                return;
            }
            
            String fone1 = this.telaCadFuncionario.getjTextFieldFone1().getText().replaceAll("[^0-9]", "");
            if (fone1.isEmpty()) {
                JOptionPane.showMessageDialog(this.telaCadFuncionario, "O Fone1 é obrigatório!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                this.telaCadFuncionario.getjTextFieldFone1().requestFocus();
                return;
            }

             if (usuario.isBlank()) {
                 JOptionPane.showMessageDialog(this.telaCadFuncionario, "O usuário é obrigatório!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                 this.telaCadFuncionario.getjTextFieldUsuario().requestFocus();
                 return;
            }
            
             if (senha.isBlank() && this.telaCadFuncionario.getjTextFieldId().getText().equalsIgnoreCase("")) {
                 JOptionPane.showMessageDialog(this.telaCadFuncionario, "A senha é obrigatória no cadastro!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                 this.telaCadFuncionario.getjPasswordFieldSenha().requestFocus();
                 return;
            }
            
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(nome);
            funcionario.setUsuario(usuario);
            funcionario.setCpf(cpf);
            funcionario.setRg(this.telaCadFuncionario.getjTextFieldRg().getText());
            funcionario.setFone1(fone1);
            funcionario.setFone2(this.telaCadFuncionario.getjTextFieldFone2().getText());
            funcionario.setEmail(this.telaCadFuncionario.getjTextFieldEmail().getText());
            funcionario.setCep(this.telaCadFuncionario.getjFormattedTextFieldCep().getText().replaceAll("[^0-9]", ""));
            funcionario.setLogradouro(this.telaCadFuncionario.getjTextFieldLogradouro().getText());
            funcionario.setBairro(this.telaCadFuncionario.getjTextFieldBairro().getText());
            funcionario.setCidade(this.telaCadFuncionario.getjTextFieldCidade().getText());
            funcionario.setComplemento(this.telaCadFuncionario.getjTextFieldComplemento().getText());
            funcionario.setObs(this.telaCadFuncionario.getjTextFieldObs().getText());
            funcionario.setStatus('A');
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            funcionario.setDataCadastro(dateFormat.format(new Date()));


            if (this.telaCadFuncionario.getjTextFieldId().getText().equalsIgnoreCase("")) {
                 funcionario.setSenha(senha);
                FuncionarioService.Criar(funcionario);
            } else {
                funcionario.setId(Integer.parseInt(this.telaCadFuncionario.getjTextFieldId().getText()));
                 if (senha.isBlank()) {
                     Funcionario funcExistente = FuncionarioService.Carregar(funcionario.getId());
                     funcionario.setSenha(funcExistente.getSenha());
                 } else {
                     funcionario.setSenha(senha); 
                 }
                FuncionarioService.Atualizar(funcionario);
            }

            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadFuncionario.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadFuncionario.getjButtonCancelar()) {
            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadFuncionario.getjPanelDados(), false);

        } else if (e.getSource() == this.telaCadFuncionario.getjButtonBuscar()) {
            codigo = 0;
            TelaBuscaFuncionario telaBuscaFuncionario = new TelaBuscaFuncionario(null, true);
            ControllerBuscaFuncionario controllerBuscaFuncionario = new ControllerBuscaFuncionario(telaBuscaFuncionario);
            telaBuscaFuncionario.setVisible(true);

            if (codigo != 0) {
                Funcionario funcionario = FuncionarioService.Carregar(codigo);
                ativaDesativa(false);
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
                this.telaCadFuncionario.getjPasswordFieldSenha().setText("");

                this.telaCadFuncionario.getjButtonExcluir().setEnabled(true);
                this.telaCadFuncionario.getjTextFieldId().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadFuncionario.getjButtonExcluir()) {
            if (this.telaCadFuncionario.getjTextFieldId().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Você precisa buscar um funcionário antes de excluir.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja INATIVAR este funcionário?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(Integer.parseInt(this.telaCadFuncionario.getjTextFieldId().getText()));
                
                FuncionarioService.Apagar(funcionario); 
                
                JOptionPane.showMessageDialog(null, "Funcionário inativado com sucesso!");
                
                ativaDesativa(true);
                utilities.Utilities.limpaComponentes(this.telaCadFuncionario.getjPanelDados(), false);
            }

        } else if (e.getSource() == this.telaCadFuncionario.getjButtonSair()) {
            this.telaCadFuncionario.dispose();
        }
    }

    private void ativaDesativa(boolean ativado) {
        this.telaCadFuncionario.getjButtonNovo().setEnabled(ativado);
        this.telaCadFuncionario.getjButtonGravar().setEnabled(!ativado);
        this.telaCadFuncionario.getjButtonCancelar().setEnabled(!ativado);
        this.telaCadFuncionario.getjButtonBuscar().setEnabled(ativado);
        this.telaCadFuncionario.getjButtonSair().setEnabled(ativado);
        this.telaCadFuncionario.getjButtonExcluir().setEnabled(false);
    }
}