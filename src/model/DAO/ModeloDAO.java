package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bo.Marca;
import model.bo.Modelo;

public class ModeloDAO implements InterfaceDAO<Modelo> {

    @Override
    public void Create(Modelo objeto) {
        String sqlInstrucao = "INSERT INTO modelo (descricao, status, marca_id) VALUES (?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getStatus()));
            pstm.setInt(3, objeto.getMarca().getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public Modelo Retrieve(int id) {
        String sqlInstrucao = "SELECT m.id, m.descricao, m.status, m.marca_id, ma.descricao as marcaDescricao " +
                              "FROM modelo m " +
                              "JOIN marca ma ON m.marca_id = ma.id WHERE m.id = ?";
        
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Modelo modelo = new Modelo();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                modelo.setId(rst.getInt("id"));
                modelo.setDescricao(rst.getString("descricao"));
                modelo.setStatus(rst.getString("status").charAt(0));

                Marca marca = new Marca();
                marca.setId(rst.getInt("marca_id"));
                marca.setDescricao(rst.getString("marcaDescricao"));

                modelo.setMarca(marca);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return modelo;
        }
    }

    @Override
    public List<Modelo> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "SELECT m.id, m.descricao, m.status, m.marca_id, ma.descricao as marcaDescricao " +
                              "FROM modelo m " +
                              "JOIN marca ma ON m.marca_id = ma.id WHERE m." + atributo + " LIKE ?";
        
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Modelo> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Modelo modelo = new Modelo();
                modelo.setId(rst.getInt("id"));
                modelo.setDescricao(rst.getString("descricao"));
                modelo.setStatus(rst.getString("status").charAt(0));

                Marca marca = new Marca();
                marca.setId(rst.getInt("marca_id"));
                marca.setDescricao(rst.getString("marcaDescricao"));

                modelo.setMarca(marca);
                lista.add(modelo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public void Update(Modelo objeto) {
        String sqlInstrucao = "UPDATE modelo SET descricao = ?, status = ?, marca_id = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getStatus()));
            pstm.setInt(3, objeto.getMarca().getId());
            pstm.setInt(4, objeto.getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Modelo objeto) {
        String sqlInstrucao = "UPDATE modelo SET status = 'I' WHERE id = ?";
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