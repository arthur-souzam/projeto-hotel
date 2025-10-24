package view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaCadastroMarca extends javax.swing.JDialog {

    public TelaCadastroMarca(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public JButton getjButtonBuscar() {
        return jButtonBuscar;
    }

    public JButton getjButtonCancelar() {
        return jButtonCancelar;
    }

    public JButton getjButtonGravar() {
        return jButtonGravar;
    }

    public JButton getjButtonNovo() {
        return jButtonNovo;
    }

    public JButton getjButtonSair() {
        return jButtonSair;
    }
    
    public JButton getjButtonExcluir() {
        return jButtonExcluir;
    }

    public JPanel getjPanelBotoes() {
        return jPanelBotoes;
    }

    public JPanel getjPanelDados() {
        return jPanelDados;
    }

    public JTextField getjTextFieldId() {
        return jTextFieldId;
    }

    public JTextField getjTextFieldDescricao() {
        return jTextFieldDescricao;
    }
    
    public JTextField getjTextFieldStatus() {
        return jTextFieldStatus;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanelTitulo = new javax.swing.JPanel();
        jLabelTitulo = new javax.swing.JLabel();
        jPanelBotoes = new javax.swing.JPanel();
        jButtonNovo = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jButtonGravar = new javax.swing.JButton();
        jButtonBuscar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jPanelDados = new javax.swing.JPanel();
        jLabelId = new javax.swing.JLabel();
        jTextFieldId = new javax.swing.JTextField();
        jLabelDescricao = new javax.swing.JLabel();
        jTextFieldDescricao = new javax.swing.JTextField();
        jLabelStatus = new javax.swing.JLabel();
        jTextFieldStatus = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Marca");
        setResizable(false);

        jPanelTitulo.setBackground(new java.awt.Color(204, 255, 204));
        jPanelTitulo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelTitulo.setPreferredSize(new java.awt.Dimension(700, 50));

        jLabelTitulo.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabelTitulo.setForeground(new java.awt.Color(51, 0, 153));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Cadastro de Marca");

        javax.swing.GroupLayout jPanelTituloLayout = new javax.swing.GroupLayout(jPanelTitulo);
        jPanelTitulo.setLayout(jPanelTituloLayout);
        jPanelTituloLayout.setHorizontalGroup(
            jPanelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
        );
        jPanelTituloLayout.setVerticalGroup(
            jPanelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        getContentPane().add(jPanelTitulo, java.awt.BorderLayout.NORTH);

        jPanelBotoes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelBotoes.setPreferredSize(new java.awt.Dimension(700, 50));

        jButtonNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Create.png")));
        jButtonNovo.setText("Novo");
        jButtonNovo.setPreferredSize(new java.awt.Dimension(110, 35));
        jPanelBotoes.add(jButtonNovo);

        jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Cancel.png")));
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.setEnabled(false);
        jButtonCancelar.setPreferredSize(new java.awt.Dimension(110, 35));
        jPanelBotoes.add(jButtonCancelar);

        jButtonGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Go.png")));
        jButtonGravar.setText("Gravar");
        jButtonGravar.setEnabled(false);
        jButtonGravar.setPreferredSize(new java.awt.Dimension(110, 35));
        jPanelBotoes.add(jButtonGravar);

        jButtonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Find.png")));
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.setPreferredSize(new java.awt.Dimension(110, 35));
        jPanelBotoes.add(jButtonBuscar);
        
        jButtonExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Delete.png")));
        jButtonExcluir.setText("Excluir");
        jButtonExcluir.setEnabled(false);
        jButtonExcluir.setPreferredSize(new java.awt.Dimension(110, 35));
        jPanelBotoes.add(jButtonExcluir);

        jButtonSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Exit.png")));
        jButtonSair.setText("Sair");
        jButtonSair.setPreferredSize(new java.awt.Dimension(110, 35));
        jPanelBotoes.add(jButtonSair);

        getContentPane().add(jPanelBotoes, java.awt.BorderLayout.SOUTH);

        jPanelDados.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabelId.setText("ID");

        jTextFieldId.setEnabled(false);

        jLabelDescricao.setText("Descrição");
        
        jLabelStatus.setText("Status");

        jTextFieldStatus.setEnabled(false);

        javax.swing.GroupLayout jPanelDadosLayout = new javax.swing.GroupLayout(jPanelDados);
        jPanelDados.setLayout(jPanelDadosLayout);
        jPanelDadosLayout.setHorizontalGroup(
            jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelId)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                        .addComponent(jLabelDescricao)
                        .addGap(0, 481, Short.MAX_VALUE))
                    .addComponent(jTextFieldDescricao))
                .addGap(18, 18, 18)
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelStatus)
                    .addComponent(jTextFieldStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        jPanelDadosLayout.setVerticalGroup(
            jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelId)
                    .addComponent(jLabelDescricao)
                    .addComponent(jLabelStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        getContentPane().add(jPanelDados, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaCadastroMarca dialog = new TelaCadastroMarca(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonGravar;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JLabel jLabelDescricao;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanelBotoes;
    private javax.swing.JPanel jPanelDados;
    private javax.swing.JPanel jPanelTitulo;
    private javax.swing.JTextField jTextFieldDescricao;
    private javax.swing.JTextField jTextFieldId;
    private javax.swing.JTextField jTextFieldStatus;
}