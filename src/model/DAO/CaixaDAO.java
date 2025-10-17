package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bo.Caixa;

public class CaixaDAO implements InterfaceDAO<Caixa> {

    @Override
    public void Create(Caixa objeto) {
        String sqlInstrucao = "INSERT INTO caixa "
                + "(data_abertura, data_fechamento, valor_abertura, valor_fechamento, observacao, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDataAbertura());
            pstm.setString(2, objeto.getDataFechamento());
            pstm.setFloat(3, objeto.getValorAbertura());
            pstm.setObject(4, objeto.getValorFechamento()); // Usar setObject para permitir nulos
            pstm.setString(5, objeto.getObservacao());
            pstm.setString(6, String.valueOf(objeto.getStatus()));
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }


    @Override
    public Caixa Retrieve(int id) {
        String sqlInstrucao = "SELECT * FROM caixa WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Caixa caixa = new Caixa();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                caixa.setId(rst.getInt("id"));
                caixa.setDataAbertura(rst.getString("data_abertura"));
                caixa.setDataFechamento(rst.getString("data_fechamento"));
                caixa.setValorAbertura(rst.getFloat("valor_abertura"));
                caixa.setValorFechamento(rst.getFloat("valor_fechamento"));
                caixa.setObservacao(rst.getString("observacao"));
                caixa.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return caixa;
        }
    }

    @Override
    public List<Caixa> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "SELECT * FROM caixa WHERE " + atributo + " LIKE ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Caixa> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Caixa caixa = new Caixa();
                caixa.setId(rst.getInt("id"));
                caixa.setDataAbertura(rst.getString("data_abertura"));
                caixa.setDataFechamento(rst.getString("data_fechamento"));
                caixa.setValorAbertura(rst.getFloat("valor_abertura"));
                caixa.setValorFechamento(rst.getFloat("valor_fechamento"));
                caixa.setObservacao(rst.getString("observacao"));
                caixa.setStatus(rst.getString("status").charAt(0));
                lista.add(caixa);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public void Update(Caixa objeto) {
        String sqlInstrucao = "UPDATE caixa SET data_abertura = ?, data_fechamento = ?, valor_abertura = ?, "
                + "valor_fechamento = ?, observacao = ?, status = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDataAbertura());
            pstm.setString(2, objeto.getDataFechamento());
            pstm.setFloat(3, objeto.getValorAbertura());
            pstm.setFloat(4, objeto.getValorFechamento());
            pstm.setString(5, objeto.getObservacao());
            pstm.setString(6, String.valueOf(objeto.getStatus()));
            pstm.setInt(7, objeto.getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Caixa objeto) {
        String sqlInstrucao = "UPDATE caixa SET status = 'I' WHERE id = ?";
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