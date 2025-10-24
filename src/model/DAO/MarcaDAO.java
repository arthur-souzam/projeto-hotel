package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.bo.Marca;

public class MarcaDAO implements InterfaceDAO<Marca> {

    @Override
    public void Create(Marca objeto) {
        String sqlInstrucao = "INSERT INTO marca (descricao, status) VALUES (?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getStatus()));
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<Marca> Retrieve() {
        String sqlInstrucao = "SELECT id, descricao, status FROM marca WHERE status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Marca> lista = new ArrayList<>();
        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            rst = pstm.executeQuery();
            while (rst.next()) {
                Marca marca = new Marca();
                marca.setId(rst.getInt("id"));
                marca.setDescricao(rst.getString("descricao"));
                marca.setStatus(rst.getString("status").charAt(0));
                lista.add(marca);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public Marca Retrieve(int id) {
        String sqlInstrucao = "SELECT id, descricao, status FROM marca WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Marca marca = new Marca();
        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();
            if (rst.next()) {
                marca.setId(rst.getInt("id"));
                marca.setDescricao(rst.getString("descricao"));
                marca.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return marca;
        }
    }

    @Override
    public List<Marca> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "SELECT id, descricao, status FROM marca WHERE " + atributo + " LIKE ? AND status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Marca> lista = new ArrayList<>();
        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();
            while (rst.next()) {
                Marca marca = new Marca();
                marca.setId(rst.getInt("id"));
                marca.setDescricao(rst.getString("descricao"));
                marca.setStatus(rst.getString("status").charAt(0));
                lista.add(marca);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public void Update(Marca objeto) {
        String sqlInstrucao = "UPDATE marca SET descricao = ?, status = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getStatus()));
            pstm.setInt(3, objeto.getId());
            pstm.executeUpdate(); 
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }


    @Override
    public void Delete(Marca objeto) {
        String sqlInstrucao = "UPDATE marca SET status = 'I' WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true; 

        if (conexao == null) {
             System.err.println("ERRO FATAL: Conexão NULA ao tentar deletar Marca ID: " + objeto.getId());
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