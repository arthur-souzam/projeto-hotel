package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bo.Servico;

public class ServicoDAO implements InterfaceDAO<Servico> {

    @Override
    public void Create(Servico objeto) {
        String sqlInstrucao = "INSERT INTO servico (descricao, valor, status) VALUES (?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setFloat(2, objeto.getValor());
            pstm.setString(3, String.valueOf(objeto.getStatus()));
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<Servico> Retrieve() {
        String sqlInstrucao = "SELECT id, descricao, valor, status FROM servico";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Servico> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            rst = pstm.executeQuery();

            while (rst.next()) {
                Servico servico = new Servico();
                servico.setId(rst.getInt("id"));
                servico.setDescricao(rst.getString("descricao"));
                servico.setValor(rst.getFloat("valor"));
                servico.setStatus(rst.getString("status").charAt(0));
                lista.add(servico);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public Servico Retrieve(int id) {
        String sqlInstrucao = "SELECT id, descricao, valor, status FROM servico WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Servico servico = new Servico();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                servico.setId(rst.getInt("id"));
                servico.setDescricao(rst.getString("descricao"));
                servico.setValor(rst.getFloat("valor"));
                servico.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return servico;
        }
    }

    @Override
    public List<Servico> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "SELECT id, descricao, valor, status FROM servico WHERE " + atributo + " LIKE ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Servico> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Servico servico = new Servico();
                servico.setId(rst.getInt("id"));
                servico.setDescricao(rst.getString("descricao"));
                servico.setValor(rst.getFloat("valor"));
                servico.setStatus(rst.getString("status").charAt(0));
                lista.add(servico);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public void Update(Servico objeto) {
        String sqlInstrucao = "UPDATE servico SET descricao = ?, valor = ?, status = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setFloat(2, objeto.getValor());
            pstm.setString(3, String.valueOf(objeto.getStatus()));
            pstm.setInt(4, objeto.getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Servico objeto) {
        String sqlInstrucao = "UPDATE servico SET status = 'I' WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, objeto.getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }
}