package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.List;
import model.bo.Fornecedor;

public class FornecedorDAO implements InterfaceDAO<Fornecedor> {

    @Override
    public void Create(Fornecedor objeto) {
        String sqlInstrucao = "INSERT INTO fornecedor "
                + "(nome, fone, fone2, email, cep, logradouro, bairro, cidade, complemento, data_cadastro, cpf, rg, obs, status, razao_social, cnpj, inscricao_estadual, contato) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getNome());
            pstm.setString(2, objeto.getFone1());
            pstm.setString(3, objeto.getFone2());
            pstm.setString(4, objeto.getEmail());
            pstm.setString(5, objeto.getCep());
            pstm.setString(6, objeto.getLogradouro());
            pstm.setString(7, objeto.getBairro());
            pstm.setString(8, objeto.getCidade());
            pstm.setString(9, objeto.getComplemento());
            pstm.setString(10, objeto.getDataCadastro());
            pstm.setString(11, objeto.getCpf());
            pstm.setString(12, objeto.getRg());
            pstm.setString(13, objeto.getObs());
            pstm.setString(14, String.valueOf(objeto.getStatus()));
            pstm.setString(15, objeto.getRazaoSocial());
            pstm.setString(16, objeto.getCnpj());
            pstm.setString(17, objeto.getInscricaoEstadual());
            pstm.setString(18, objeto.getContato());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<Fornecedor> Retrieve() {
        String sqlInstrucao = "SELECT * FROM fornecedor WHERE status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Fornecedor> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            rst = pstm.executeQuery();

            while (rst.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fillEntityFromResultSet(rst, fornecedor);
                lista.add(fornecedor);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public Fornecedor Retrieve(int id) {
        String sqlInstrucao = "SELECT * FROM fornecedor WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Fornecedor fornecedor = new Fornecedor();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                fillEntityFromResultSet(rst, fornecedor);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return fornecedor;
        }
    }

    @Override
    public List<Fornecedor> Retrieve(String atributo, String valor) {
        String colunaBusca = atributo;
        // Mapeamento de atributos do BO/Tela para colunas do DB, se necessário
        if (atributo.equalsIgnoreCase("razaoSocial")) {
            colunaBusca = "razao_social";
        } else if (atributo.equalsIgnoreCase("inscricaoEstadual")) {
            colunaBusca = "inscricao_estadual";
        }
    
        String sqlInstrucao = "SELECT * FROM fornecedor WHERE " + colunaBusca + " LIKE ? AND status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Fornecedor> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fillEntityFromResultSet(rst, fornecedor);
                lista.add(fornecedor);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public void Update(Fornecedor objeto) {
        String sqlInstrucao = "UPDATE fornecedor SET "
                + "nome = ?, fone = ?, fone2 = ?, email = ?, cep = ?, logradouro = ?, bairro = ?, cidade = ?, "
                + "complemento = ?, data_cadastro = ?, cpf = ?, rg = ?, obs = ?, status = ?, "
                + "razao_social = ?, cnpj = ?, inscricao_estadual = ?, contato = ? "
                + " WHERE id = ? ";

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;

        if (conexao == null) {
             System.err.println("ERRO FATAL: Conexão NULA ao tentar atualizar Fornecedor ID: " + objeto.getId());
             return;
        }

        try {
            originalAutoCommitState = conexao.getAutoCommit();
             if(originalAutoCommitState){
                conexao.setAutoCommit(false);
             }

            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getNome());
            pstm.setString(2, objeto.getFone1());
            pstm.setString(3, objeto.getFone2());
            pstm.setString(4, objeto.getEmail());
            pstm.setString(5, objeto.getCep());
            pstm.setString(6, objeto.getLogradouro());
            pstm.setString(7, objeto.getBairro());
            pstm.setString(8, objeto.getCidade());
            pstm.setString(9, objeto.getComplemento());
            pstm.setString(10, objeto.getDataCadastro());
            pstm.setString(11, objeto.getCpf());
            pstm.setString(12, objeto.getRg());
            pstm.setString(13, objeto.getObs());
            pstm.setString(14, String.valueOf(objeto.getStatus()));
            pstm.setString(15, objeto.getRazaoSocial());
            pstm.setString(16, objeto.getCnpj());
            pstm.setString(17, objeto.getInscricaoEstadual());
            pstm.setString(18, objeto.getContato());
            pstm.setInt(19, objeto.getId());
            
            int rowsAffected = pstm.executeUpdate();

            if (rowsAffected > 0) {
                 conexao.commit();
            } else {
                 conexao.rollback();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
             try { if (conexao != null) { conexao.rollback(); } } catch (SQLException rbEx) { rbEx.printStackTrace(); }
        } finally {
             try { if (conexao != null && !conexao.isClosed()) { if(conexao.getAutoCommit() != originalAutoCommitState){ conexao.setAutoCommit(originalAutoCommitState);} } } catch (SQLException acEx) { acEx.printStackTrace(); }
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Fornecedor objeto) {
        String sqlInstrucao = "UPDATE fornecedor SET status = 'I' WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true; 

        if (conexao == null) {
             System.err.println("ERRO FATAL: Conexão NULA ao tentar deletar Fornecedor ID: " + objeto.getId());
            return; 
        }

        try {
            originalAutoCommitState = conexao.getAutoCommit(); 
            if (originalAutoCommitState) {
                conexao.setAutoCommit(false); 
            }

            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, objeto.getId());

            int rowsAffected = pstm.executeUpdate(); 

            if (rowsAffected > 0) {
                 conexao.commit(); 
            } else {
                 conexao.rollback(); 
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                if (conexao != null) {
                    conexao.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (conexao != null && !conexao.isClosed()) {
                     if (conexao.getAutoCommit() != originalAutoCommitState) {
                         conexao.setAutoCommit(originalAutoCommitState);
                     }
                }
            } catch (SQLException autoCommitEx) {
                autoCommitEx.printStackTrace();
            }
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }
    
    // Método auxiliar para preencher o objeto Fornecedor a partir do ResultSet
    private void fillEntityFromResultSet(ResultSet rst, Fornecedor fornecedor) throws SQLException {
        fornecedor.setId(rst.getInt("id"));
        fornecedor.setNome(rst.getString("nome"));
        fornecedor.setFone1(rst.getString("fone"));
        fornecedor.setFone2(rst.getString("fone2"));
        fornecedor.setEmail(rst.getString("email"));
        fornecedor.setCep(rst.getString("cep"));
        fornecedor.setLogradouro(rst.getString("logradouro"));
        fornecedor.setBairro(rst.getString("bairro"));
        fornecedor.setCidade(rst.getString("cidade"));
        fornecedor.setComplemento(rst.getString("complemento"));
        fornecedor.setDataCadastro(rst.getString("data_cadastro"));
        fornecedor.setCpf(rst.getString("cpf"));
        fornecedor.setRg(rst.getString("rg"));
        fornecedor.setObs(rst.getString("obs"));
        fornecedor.setStatus(rst.getString("status").charAt(0));
        fornecedor.setRazaoSocial(rst.getString("razao_social"));
        fornecedor.setCnpj(rst.getString("cnpj"));
        fornecedor.setInscricaoEstadual(rst.getString("inscricao_estadual"));
        fornecedor.setContato(rst.getString("contato"));
    }
}