package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bo.CopaQuarto;
import model.bo.CheckQuarto;
import model.bo.ProdutoCopa;

public class CopaQuartoDAO implements InterfaceDAO<CopaQuarto> {

    @Override
    public void Create(CopaQuarto objeto) {
        String sqlInstrucao = "INSERT INTO copa_quarto "
                + "(qtd_produto, valor_unitario, status, produto_copa_id, check_quarto_id) "
                + "VALUES (?, ?, ?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, objeto.getQtdProduto());
            pstm.setFloat(2, objeto.getValorUnitario());
            pstm.setString(3, String.valueOf(objeto.getStatus()));
            pstm.setInt(4, objeto.getProdutoCopa().getId());
            pstm.setInt(5, objeto.getCheckQuarto().getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }


    @Override
    public CopaQuarto Retrieve(int id) {
        String sqlInstrucao = "SELECT * FROM copa_quarto WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        CopaQuarto copaQuarto = new CopaQuarto();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                copaQuarto.setId(rst.getInt("id"));
                copaQuarto.setQtdProduto(rst.getInt("qtd_produto"));
                copaQuarto.setValorUnitario(rst.getFloat("valor_unitario"));
                copaQuarto.setStatus(rst.getString("status").charAt(0));

                ProdutoCopaDAO produtoDAO = new ProdutoCopaDAO();
                copaQuarto.setProdutoCopa(produtoDAO.Retrieve(rst.getInt("produto_copa_id")));

                CheckQuarto checkQuarto = new CheckQuarto();
                checkQuarto.setId(rst.getInt("check_quarto_id"));
                copaQuarto.setCheckQuarto(checkQuarto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return copaQuarto;
        }
    }

    @Override
    public List<CopaQuarto> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "SELECT * FROM copa_quarto WHERE " + atributo + " LIKE ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<CopaQuarto> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();
            
            // A lógica de preenchimento seria igual ao Retrieve()
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public void Update(CopaQuarto objeto) {
        String sqlInstrucao = "UPDATE copa_quarto SET qtd_produto = ?, valor_unitario = ?, status = ?, "
                + "produto_copa_id = ?, check_quarto_id = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, objeto.getQtdProduto());
            pstm.setFloat(2, objeto.getValorUnitario());
            pstm.setString(3, String.valueOf(objeto.getStatus()));
            pstm.setInt(4, objeto.getProdutoCopa().getId());
            pstm.setInt(5, objeto.getCheckQuarto().getId());
            pstm.setInt(6, objeto.getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(CopaQuarto objeto) {
        String sqlInstrucao = "UPDATE copa_quarto SET status = 'I' WHERE id = ?";
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