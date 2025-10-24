package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bo.Marca;
import model.bo.Modelo;
import model.bo.Veiculo;

public class VeiculoDAO implements InterfaceDAO<Veiculo> {

    @Override
    public void Create(Veiculo objeto) {
        String sqlInstrucao = "INSERT INTO veiculo (placa, cor, status, modelo_id) VALUES (?, ?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getPlaca());
            pstm.setString(2, objeto.getCor());
            pstm.setString(3, String.valueOf(objeto.getStatus()));
            pstm.setInt(4, objeto.getModelo().getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }
    
    @Override
    public List<Veiculo> Retrieve() {
        String sqlInstrucao = "SELECT v.id, v.placa, v.cor, v.status, v.modelo_id, " +
                              "mo.descricao as modeloDescricao, ma.id as marcaId, ma.descricao as marcaDescricao " +
                              "FROM veiculo v " +
                              "JOIN modelo mo ON v.modelo_id = mo.id " +
                              "JOIN marca ma ON mo.marca_id = ma.id";

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Veiculo> lista = new ArrayList<>(); 

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            rst = pstm.executeQuery();

           
            while (rst.next()) {
                Veiculo veiculo = new Veiculo(); 
                veiculo.setId(rst.getInt("id"));
                veiculo.setPlaca(rst.getString("placa"));
                veiculo.setCor(rst.getString("cor"));
                veiculo.setStatus(rst.getString("status").charAt(0));

                Marca marca = new Marca();
                marca.setId(rst.getInt("marcaId"));
                marca.setDescricao(rst.getString("marcaDescricao"));

                Modelo modelo = new Modelo();
                modelo.setId(rst.getInt("modelo_id"));
                modelo.setDescricao(rst.getString("modeloDescricao"));
                modelo.setMarca(marca);

                veiculo.setModelo(modelo);
                lista.add(veiculo); 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista; 
        }
    }


    @Override
    public Veiculo Retrieve(int id) {
        String sqlInstrucao = "SELECT v.id, v.placa, v.cor, v.status, v.modelo_id, " +
                              "mo.descricao as modeloDescricao, ma.id as marcaId, ma.descricao as marcaDescricao " +
                              "FROM veiculo v " +
                              "JOIN modelo mo ON v.modelo_id = mo.id " +
                              "JOIN marca ma ON mo.marca_id = ma.id WHERE v.id = ?";
        
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Veiculo veiculo = new Veiculo();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                veiculo.setId(rst.getInt("id"));
                veiculo.setPlaca(rst.getString("placa"));
                veiculo.setCor(rst.getString("cor"));
                veiculo.setStatus(rst.getString("status").charAt(0));

                Marca marca = new Marca();
                marca.setId(rst.getInt("marcaId"));
                marca.setDescricao(rst.getString("marcaDescricao"));

                Modelo modelo = new Modelo();
                modelo.setId(rst.getInt("modelo_id"));
                modelo.setDescricao(rst.getString("modeloDescricao"));
                modelo.setMarca(marca);
                
                veiculo.setModelo(modelo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return veiculo;
        }
    }

    @Override
    public List<Veiculo> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "SELECT v.id, v.placa, v.cor, v.status, v.modelo_id, " +
                              "mo.descricao as modeloDescricao, ma.id as marcaId, ma.descricao as marcaDescricao " +
                              "FROM veiculo v " +
                              "JOIN modelo mo ON v.modelo_id = mo.id " +
                              "JOIN marca ma ON mo.marca_id = ma.id WHERE v." + atributo + " LIKE ?";
        
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Veiculo> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setId(rst.getInt("id"));
                veiculo.setPlaca(rst.getString("placa"));
                veiculo.setCor(rst.getString("cor"));
                veiculo.setStatus(rst.getString("status").charAt(0));

                Marca marca = new Marca();
                marca.setId(rst.getInt("marcaId"));
                marca.setDescricao(rst.getString("marcaDescricao"));

                Modelo modelo = new Modelo();
                modelo.setId(rst.getInt("modelo_id"));
                modelo.setDescricao(rst.getString("modeloDescricao"));
                modelo.setMarca(marca);
                
                veiculo.setModelo(modelo);
                lista.add(veiculo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public void Update(Veiculo objeto) {
        String sqlInstrucao = "UPDATE veiculo SET placa = ?, cor = ?, status = ?, modelo_id = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getPlaca());
            pstm.setString(2, objeto.getCor());
            pstm.setString(3, String.valueOf(objeto.getStatus()));
            pstm.setInt(4, objeto.getModelo().getId());
            pstm.setInt(5, objeto.getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Veiculo objeto) {
        String sqlInstrucao = "UPDATE veiculo SET status = 'I' WHERE id = ?";
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