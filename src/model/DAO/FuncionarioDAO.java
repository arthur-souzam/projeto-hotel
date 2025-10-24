package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.bo.Funcionario;

public class FuncionarioDAO implements InterfaceDAO<Funcionario> {

    @Override
    public void Create(Funcionario objeto) {
        String sqlInstrucao = "INSERT INTO funcionario "
                + "(nome, fone, fone2, email, cep, logradouro, bairro, cidade, complemento, data_cadastro, cpf, rg, obs, status, usuario, senha) " // Corrigido login para usuario
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            pstm.setString(15, objeto.getUsuario()); // Corrigido getLogin para getUsuario
            pstm.setString(16, objeto.getSenha());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<Funcionario> Retrieve() {
        String sqlInstrucao = "SELECT * FROM funcionario WHERE status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Funcionario> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            rst = pstm.executeQuery();

            while (rst.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rst.getInt("id"));
                funcionario.setNome(rst.getString("nome"));
                funcionario.setFone1(rst.getString("fone"));
                funcionario.setFone2(rst.getString("fone2"));
                funcionario.setEmail(rst.getString("email"));
                funcionario.setCep(rst.getString("cep"));
                funcionario.setLogradouro(rst.getString("logradouro"));
                funcionario.setBairro(rst.getString("bairro"));
                funcionario.setCidade(rst.getString("cidade"));
                funcionario.setComplemento(rst.getString("complemento"));
                funcionario.setDataCadastro(rst.getString("data_cadastro"));
                funcionario.setCpf(rst.getString("cpf"));
                funcionario.setRg(rst.getString("rg"));
                funcionario.setObs(rst.getString("obs"));
                funcionario.setStatus(rst.getString("status").charAt(0));
                funcionario.setUsuario(rst.getString("usuario")); // Corrigido login para usuario
                funcionario.setSenha(rst.getString("senha"));
                lista.add(funcionario);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public Funcionario Retrieve(int id) {
        String sqlInstrucao = "SELECT * FROM funcionario WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Funcionario funcionario = new Funcionario();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                funcionario.setId(rst.getInt("id"));
                funcionario.setNome(rst.getString("nome"));
                funcionario.setFone1(rst.getString("fone"));
                funcionario.setFone2(rst.getString("fone2"));
                funcionario.setEmail(rst.getString("email"));
                funcionario.setCep(rst.getString("cep"));
                funcionario.setLogradouro(rst.getString("logradouro"));
                funcionario.setBairro(rst.getString("bairro"));
                funcionario.setCidade(rst.getString("cidade"));
                funcionario.setComplemento(rst.getString("complemento"));
                funcionario.setDataCadastro(rst.getString("data_cadastro"));
                funcionario.setCpf(rst.getString("cpf"));
                funcionario.setRg(rst.getString("rg"));
                funcionario.setObs(rst.getString("obs"));
                funcionario.setStatus(rst.getString("status").charAt(0));
                funcionario.setUsuario(rst.getString("usuario")); 
                funcionario.setSenha(rst.getString("senha"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return funcionario;
        }
    }

    @Override
    public List<Funcionario> Retrieve(String atributo, String valor) {
       
        String colunaBusca = atributo;
        if (atributo.equalsIgnoreCase("login")) {
            colunaBusca = "usuario";
        }
    
        String sqlInstrucao = "SELECT * FROM funcionario WHERE " + colunaBusca + " LIKE ? AND status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Funcionario> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rst.getInt("id"));
                funcionario.setNome(rst.getString("nome"));
                funcionario.setFone1(rst.getString("fone"));
                funcionario.setFone2(rst.getString("fone2"));
                funcionario.setEmail(rst.getString("email"));
                funcionario.setCep(rst.getString("cep"));
                funcionario.setLogradouro(rst.getString("logradouro"));
                funcionario.setBairro(rst.getString("bairro"));
                funcionario.setCidade(rst.getString("cidade"));
                funcionario.setComplemento(rst.getString("complemento"));
                funcionario.setDataCadastro(rst.getString("data_cadastro"));
                funcionario.setCpf(rst.getString("cpf"));
                funcionario.setRg(rst.getString("rg"));
                funcionario.setObs(rst.getString("obs"));
                funcionario.setStatus(rst.getString("status").charAt(0));
                funcionario.setUsuario(rst.getString("usuario")); 
                funcionario.setSenha(rst.getString("senha"));
                lista.add(funcionario);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public void Update(Funcionario objeto) {
        String sqlInstrucao = "UPDATE funcionario SET "
                + "nome = ?, fone = ?, fone2 = ?, email = ?, cep = ?, "
                + "logradouro = ?, bairro = ?, cidade = ?, complemento = ?, obs = ?, status = ?, "
                + "usuario = ?, senha = ? WHERE id = ?"; // Corrigido login para usuario
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
            pstm.setString(10, objeto.getObs());
            pstm.setString(11, String.valueOf(objeto.getStatus()));
            pstm.setString(12, objeto.getUsuario()); // Corrigido getLogin para getUsuario
            pstm.setString(13, objeto.getSenha());
            pstm.setInt(14, objeto.getId());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Funcionario objeto) {
        String sqlInstrucao = "UPDATE funcionario SET status = 'I' WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;

        if (conexao == null) {
            System.err.println("ERRO FATAL: Conexão NULA ao tentar deletar Funcionario ID: " + objeto.getId());
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
}