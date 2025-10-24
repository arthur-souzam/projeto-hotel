package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import model.bo.Fornecedor;
import service.FornecedorService;
import view.TelaBuscaFornecedor;
import view.TelaCadastroFornecedor;

public class ControllerCadFornecedor implements ActionListener {

    TelaCadastroFornecedor telaCadFornecedor;
    public static int codigo;

    public ControllerCadFornecedor(TelaCadastroFornecedor telaCadFornecedor) {
        this.telaCadFornecedor = telaCadFornecedor;

        this.telaCadFornecedor.getjButtonNovo().addActionListener(this);
        this.telaCadFornecedor.getjButtonGravar().addActionListener(this);
        this.telaCadFornecedor.getjButtonCancelar().addActionListener(this);
        this.telaCadFornecedor.getjButtonBuscar().addActionListener(this);
        this.telaCadFornecedor.getjButtonSair().addActionListener(this);
        this.telaCadFornecedor.getjButtonExcluir().addActionListener(this);

        ativaDesativa(true);
        utilities.Utilities.limpaComponentes(this.telaCadFornecedor.getjPanelDados(), false);
        this.telaCadFornecedor.getjTextFieldStatus().setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadFornecedor.getjButtonNovo()) {
            ativaDesativa(false);
            utilities.Utilities.limpaComponentes(this.telaCadFornecedor.getjPanelDados(), true);
            this.telaCadFornecedor.getjTextFieldId().setEnabled(false);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            this.telaCadFornecedor.getjFormattedTextFieldDataCadastro().setText(dateFormat.format(new Date()));
            this.telaCadFornecedor.getjFormattedTextFieldDataCadastro().setEnabled(false);

            this.telaCadFornecedor.getjTextFieldStatus().setText("A");
            this.telaCadFornecedor.getjTextFieldStatus().setEnabled(false);
            this.telaCadFornecedor.getjButtonGravar().setEnabled(true);

        } else if (e.getSource() == this.telaCadFornecedor.getjButtonGravar()) {

            String nome = this.telaCadFornecedor.getjTextFieldNome().getText();
            String razaoSocial = this.telaCadFornecedor.getjTextFieldRazaoSocial().getText();
            String cnpj = this.telaCadFornecedor.getjFormattedTextFieldCnpj().getText().replaceAll("[^0-9]", "");
            String fone1 = this.telaCadFornecedor.getjFormattedTextFieldFone1().getText().replaceAll("[^0-9]", "");
            String cidade = this.telaCadFornecedor.getjTextFieldCidade().getText();
            String cep = this.telaCadFornecedor.getjFormattedTextFieldCep().getText().replaceAll("[^0-9]", "");

            if (nome.isBlank()) {
                 JOptionPane.showMessageDialog(this.telaCadFornecedor, "O Nome Fantasia/Contato é obrigatório!");
                 this.telaCadFornecedor.getjTextFieldNome().requestFocus();
                 return;
            }
             if (razaoSocial.isBlank()) {
                 JOptionPane.showMessageDialog(this.telaCadFornecedor, "A Razão Social é obrigatória!");
                 this.telaCadFornecedor.getjTextFieldRazaoSocial().requestFocus();
                 return;
            }
             if (cnpj.isBlank()) {
                 JOptionPane.showMessageDialog(this.telaCadFornecedor, "O CNPJ é obrigatório!");
                 this.telaCadFornecedor.getjFormattedTextFieldCnpj().requestFocus();
                 return;
            }
            if (fone1.isEmpty()) {
                JOptionPane.showMessageDialog(this.telaCadFornecedor, "O Fone1 é obrigatório!");
                this.telaCadFornecedor.getjFormattedTextFieldFone1().requestFocus();
                return;
            }
             if (cep.isEmpty()) { // Validação do CEP adicionada
                JOptionPane.showMessageDialog(this.telaCadFornecedor, "O CEP é obrigatório!");
                this.telaCadFornecedor.getjFormattedTextFieldCep().requestFocus();
                return;
            }
             if (cidade.isBlank()) {
                 JOptionPane.showMessageDialog(this.telaCadFornecedor, "A Cidade é obrigatória!");
                 this.telaCadFornecedor.getjTextFieldCidade().requestFocus();
                 return;
             }

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setNome(nome);
            fornecedor.setRazaoSocial(razaoSocial);
            fornecedor.setCnpj(cnpj);
            fornecedor.setInscricaoEstadual(this.telaCadFornecedor.getjTextFieldInscricaoEstadual().getText());
            fornecedor.setCpf(this.telaCadFornecedor.getjFormattedTextFieldCpf().getText().replaceAll("[^0-9]", ""));
            fornecedor.setRg(this.telaCadFornecedor.getjTextFieldRg().getText());
            fornecedor.setFone1(fone1);
            fornecedor.setFone2(this.telaCadFornecedor.getjFormattedTextFieldFone2().getText().replaceAll("[^0-9]", ""));
            fornecedor.setEmail(this.telaCadFornecedor.getjTextFieldEmail().getText());
            fornecedor.setContato(this.telaCadFornecedor.getjTextFieldContato().getText());
            fornecedor.setCep(cep);
            fornecedor.setCidade(cidade);
            fornecedor.setBairro(this.telaCadFornecedor.getjTextFieldBairro().getText());
            fornecedor.setLogradouro(this.telaCadFornecedor.getjTextFieldLogradouro().getText());
            fornecedor.setComplemento(this.telaCadFornecedor.getjTextFieldComplemento().getText());
            fornecedor.setObs(this.telaCadFornecedor.getjTextFieldObs().getText());
            fornecedor.setStatus('A');

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fornecedor.setDataCadastro(dateFormat.format(new Date()));

            if (this.telaCadFornecedor.getjTextFieldId().getText().equalsIgnoreCase("")) {
                FornecedorService.Criar(fornecedor);
            } else {
                fornecedor.setId(Integer.parseInt(this.telaCadFornecedor.getjTextFieldId().getText()));
                FornecedorService.Atualizar(fornecedor);
            }

            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadFornecedor.getjPanelDados(), false);
            this.telaCadFornecedor.getjTextFieldStatus().setEnabled(false);

        } else if (e.getSource() == this.telaCadFornecedor.getjButtonCancelar()) {
            ativaDesativa(true);
            utilities.Utilities.limpaComponentes(this.telaCadFornecedor.getjPanelDados(), false);
            this.telaCadFornecedor.getjTextFieldStatus().setEnabled(false);

        } else if (e.getSource() == this.telaCadFornecedor.getjButtonBuscar()) {
            codigo = 0;
            TelaBuscaFornecedor telaBuscaFornecedor = new TelaBuscaFornecedor(null, true);
            ControllerBuscaFornecedor controllerBuscaFornecedor = new ControllerBuscaFornecedor(telaBuscaFornecedor);
            telaBuscaFornecedor.setVisible(true);

            if (codigo != 0) {
                Fornecedor fornecedor = FornecedorService.Carregar(codigo);
                ativaDesativa(false);
                utilities.Utilities.limpaComponentes(this.telaCadFornecedor.getjPanelDados(), true);

                this.telaCadFornecedor.getjTextFieldId().setText(String.valueOf(fornecedor.getId()));
                this.telaCadFornecedor.getjTextFieldNome().setText(fornecedor.getNome());
                this.telaCadFornecedor.getjTextFieldRazaoSocial().setText(fornecedor.getRazaoSocial());
                this.telaCadFornecedor.getjFormattedTextFieldCnpj().setText(fornecedor.getCnpj());
                this.telaCadFornecedor.getjTextFieldInscricaoEstadual().setText(fornecedor.getInscricaoEstadual());
                this.telaCadFornecedor.getjFormattedTextFieldCpf().setText(fornecedor.getCpf());
                this.telaCadFornecedor.getjTextFieldRg().setText(fornecedor.getRg());
                this.telaCadFornecedor.getjFormattedTextFieldFone1().setText(fornecedor.getFone1());
                this.telaCadFornecedor.getjFormattedTextFieldFone2().setText(fornecedor.getFone2());
                this.telaCadFornecedor.getjTextFieldEmail().setText(fornecedor.getEmail());
                this.telaCadFornecedor.getjTextFieldContato().setText(fornecedor.getContato());
                this.telaCadFornecedor.getjFormattedTextFieldCep().setText(fornecedor.getCep());
                this.telaCadFornecedor.getjTextFieldCidade().setText(fornecedor.getCidade());
                this.telaCadFornecedor.getjTextFieldBairro().setText(fornecedor.getBairro());
                this.telaCadFornecedor.getjTextFieldLogradouro().setText(fornecedor.getLogradouro());
                this.telaCadFornecedor.getjTextFieldComplemento().setText(fornecedor.getComplemento());
                this.telaCadFornecedor.getjTextFieldObs().setText(fornecedor.getObs());
                this.telaCadFornecedor.getjTextFieldStatus().setText(String.valueOf(fornecedor.getStatus()));
                
                SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    // Tenta converter do formato yyyy-MM-dd (banco) para dd/MM/yyyy (tela)
                    Date data = new SimpleDateFormat("yyyy-MM-dd").parse(fornecedor.getDataCadastro());
                    this.telaCadFornecedor.getjFormattedTextFieldDataCadastro().setText(displayFormat.format(data));
                } catch (Exception ex) {
                    // Se der erro ou data for inválida, tenta mostrar o que veio do banco ou limpa
                    if(fornecedor.getDataCadastro() != null && !fornecedor.getDataCadastro().isEmpty()){
                         this.telaCadFornecedor.getjFormattedTextFieldDataCadastro().setText(fornecedor.getDataCadastro());
                    } else {
                         this.telaCadFornecedor.getjFormattedTextFieldDataCadastro().setText(""); 
                    }
                }


                this.telaCadFornecedor.getjTextFieldId().setEnabled(false);
                this.telaCadFornecedor.getjTextFieldStatus().setEnabled(false);
                this.telaCadFornecedor.getjFormattedTextFieldDataCadastro().setEnabled(false);


                if (fornecedor.getStatus() == 'A') {
                    this.telaCadFornecedor.getjButtonGravar().setEnabled(true);
                    this.telaCadFornecedor.getjButtonExcluir().setEnabled(true);
                } else {
                    this.telaCadFornecedor.getjButtonGravar().setEnabled(false);
                    this.telaCadFornecedor.getjButtonExcluir().setEnabled(false);
                }
            } else {
                 ativaDesativa(true);
                 utilities.Utilities.limpaComponentes(this.telaCadFornecedor.getjPanelDados(), false);
                 this.telaCadFornecedor.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadFornecedor.getjButtonExcluir()) {
            if (this.telaCadFornecedor.getjTextFieldId().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Você precisa buscar um fornecedor antes de excluir.");
                return;
            }
            
            if (this.telaCadFornecedor.getjTextFieldStatus().getText().equalsIgnoreCase("I")) {
                 JOptionPane.showMessageDialog(null, "Este registro já está inativo.");
                 return;
            }
            
            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja INATIVAR este fornecedor?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(Integer.parseInt(this.telaCadFornecedor.getjTextFieldId().getText()));
                
                FornecedorService.Apagar(fornecedor); 
                
                JOptionPane.showMessageDialog(null, "Fornecedor inativado com sucesso!");
                
                ativaDesativa(true);
                utilities.Utilities.limpaComponentes(this.telaCadFornecedor.getjPanelDados(), false);
                this.telaCadFornecedor.getjTextFieldStatus().setEnabled(false);
            }

        } else if (e.getSource() == this.telaCadFornecedor.getjButtonSair()) {
            this.telaCadFornecedor.dispose();
        }
    }

    private void ativaDesativa(boolean estadoInicial) {
        this.telaCadFornecedor.getjButtonNovo().setEnabled(estadoInicial);
        this.telaCadFornecedor.getjButtonGravar().setEnabled(!estadoInicial);
        this.telaCadFornecedor.getjButtonCancelar().setEnabled(!estadoInicial);
        this.telaCadFornecedor.getjButtonBuscar().setEnabled(estadoInicial);
        this.telaCadFornecedor.getjButtonSair().setEnabled(estadoInicial);
        this.telaCadFornecedor.getjButtonExcluir().setEnabled(false);
    }
}