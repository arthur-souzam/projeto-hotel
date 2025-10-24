package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.List;
import model.bo.Vaga;

public class VagaDAO implements InterfaceDAO<Vaga> {

    @Override
    public void Create(Vaga objeto) {
        String sqlInstrucao = "INSERT INTO vaga_estacionamento (descricao, obs, metragem_vaga, status) VALUES (?, ?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, objeto.getObs());
            pstm.setFloat(3, objeto.getMetragemVaga());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<Vaga> Retrieve() {
        String sqlInstrucao = "SELECT id, descricao, obs, metragem_vaga, status FROM vaga_estacionamento WHERE status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Vaga> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            rst = pstm.executeQuery();

            while (rst.next()) {
                Vaga vaga = new Vaga();
                fillEntityFromResultSet(rst, vaga);
                lista.add(vaga);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public Vaga Retrieve(int id) {
        String sqlInstrucao = "SELECT id, descricao, obs, metragem_vaga, status FROM vaga_estacionamento WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Vaga vaga = new Vaga();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                fillEntityFromResultSet(rst, vaga);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return vaga;
        }
    }

    @Override
    public List<Vaga> Retrieve(String atributo, String valor) {
        String colunaBusca = atributo;
         if (atributo.equalsIgnoreCase("metragemVaga")) {
             colunaBusca = "metragem_vaga";
         }

        String sqlInstrucao = "SELECT id, descricao, obs, metragem_vaga, status FROM vaga_estacionamento WHERE " + colunaBusca + " LIKE ? AND status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Vaga> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Vaga vaga = new Vaga();
                 fillEntityFromResultSet(rst, vaga);
                lista.add(vaga);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public void Update(Vaga objeto) {
        String sqlInstrucao = "UPDATE vaga_estacionamento SET descricao = ?, obs = ?, metragem_vaga = ?, status = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;

        if (conexao == null) {
             System.err.println("ERRO FATAL: Conexão NULA ao tentar atualizar VagaEstacionamento ID: " + objeto.getId());
             return;
        }

        try {
             originalAutoCommitState = conexao.getAutoCommit();
             if(originalAutoCommitState){
                conexao.setAutoCommit(false);
             }

            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, objeto.getObs());
            pstm.setFloat(3, objeto.getMetragemVaga());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.setInt(5, objeto.getId());
            
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
    public void Delete(Vaga objeto) {
        String sqlInstrucao = "UPDATE vaga_estacionamento SET status = 'I' WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true; 

        if (conexao == null) {
             System.err.println("ERRO FATAL: Conexão NULA ao tentar deletar VagaEstacionamento ID: " + objeto.getId());
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

    private void fillEntityFromResultSet(ResultSet rst, Vaga vaga) throws SQLException {
        vaga.setId(rst.getInt("id"));
        vaga.setDescricao(rst.getString("descricao"));
        vaga.setObs(rst.getString("obs"));
        vaga.setMetragemVaga(rst.getFloat("metragem_vaga"));
        vaga.setStatus(rst.getString("status").charAt(0));
    }
}