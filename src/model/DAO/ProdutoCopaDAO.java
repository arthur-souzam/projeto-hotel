package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.List;
import model.bo.ProdutoCopa;

public class ProdutoCopaDAO implements InterfaceDAO<ProdutoCopa> {

    @Override
    public void Create(ProdutoCopa objeto) {
        String sqlInstrucao = "INSERT INTO produto_copa (decricao, valor, codigo_barra, status) VALUES (?, ?, ?, ?)"; // Corrigido decricao
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setFloat(2, objeto.getValor());
            pstm.setString(3, objeto.getCodigoBarra());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<ProdutoCopa> Retrieve() {
        String sqlInstrucao = "SELECT id, decricao, valor, codigo_barra, status FROM produto_copa WHERE status = 'A'"; // Corrigido decricao
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<ProdutoCopa> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            rst = pstm.executeQuery();

            while (rst.next()) {
                ProdutoCopa produto = new ProdutoCopa();
                produto.setId(rst.getInt("id"));
                produto.setDescricao(rst.getString("decricao")); // Corrigido decricao
                produto.setValor(rst.getFloat("valor"));
                produto.setCodigoBarra(rst.getString("codigo_barra"));
                produto.setStatus(rst.getString("status").charAt(0));
                lista.add(produto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public ProdutoCopa Retrieve(int id) {
        String sqlInstrucao = "SELECT id, decricao, valor, codigo_barra, status FROM produto_copa WHERE id = ?"; // Corrigido decricao
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        ProdutoCopa produto = new ProdutoCopa();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                produto.setId(rst.getInt("id"));
                produto.setDescricao(rst.getString("decricao")); // Corrigido decricao
                produto.setValor(rst.getFloat("valor"));
                produto.setCodigoBarra(rst.getString("codigo_barra"));
                produto.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return produto;
        }
    }

    @Override
    public List<ProdutoCopa> Retrieve(String atributo, String valor) {
        String colunaBusca = atributo;
         if (atributo.equalsIgnoreCase("descricao")) { // Se buscar por descrição
             colunaBusca = "decricao"; // Usa o nome correto da coluna
         } else if (atributo.equalsIgnoreCase("codigo_barra")) {
             colunaBusca = "codigo_barra";
         }

        String sqlInstrucao = "SELECT id, decricao, valor, codigo_barra, status FROM produto_copa WHERE " + colunaBusca + " LIKE ? AND status = 'A'"; // Corrigido decricao
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<ProdutoCopa> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                ProdutoCopa produto = new ProdutoCopa();
                produto.setId(rst.getInt("id"));
                produto.setDescricao(rst.getString("decricao")); // Corrigido decricao
                produto.setValor(rst.getFloat("valor"));
                produto.setCodigoBarra(rst.getString("codigo_barra"));
                produto.setStatus(rst.getString("status").charAt(0));
                lista.add(produto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public void Update(ProdutoCopa objeto) {
        String sqlInstrucao = "UPDATE produto_copa SET decricao = ?, valor = ?, codigo_barra = ?, status = ? WHERE id = ?"; // Corrigido decricao
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
         boolean originalAutoCommitState = true;

        if (conexao == null) {
            System.err.println("ERRO FATAL: Conexão NULA ao tentar atualizar ProdutoCopa ID: " + objeto.getId());
            return;
        }


        try {
             originalAutoCommitState = conexao.getAutoCommit();
             if(originalAutoCommitState){
                conexao.setAutoCommit(false);
             }
             
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setFloat(2, objeto.getValor());
            pstm.setString(3, objeto.getCodigoBarra());
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
    public void Delete(ProdutoCopa objeto) {
        String sqlInstrucao = "UPDATE produto_copa SET status = 'I' WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true; 

        if (conexao == null) {
             System.err.println("ERRO FATAL: Conexão NULA ao tentar deletar ProdutoCopa ID: " + objeto.getId());
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