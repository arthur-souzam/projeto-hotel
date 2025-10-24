package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bo.Quarto;

public class QuartoDAO implements InterfaceDAO<Quarto> {

    @Override
    public void Create(Quarto objeto) {
        String sqlInstrucao = "INSERT INTO quarto (descricao, capacidade_hospedes, metragem, identificacao, andar, flag_animais, obs, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setInt(2, objeto.getCapacidadeHospedes());
            pstm.setFloat(3, objeto.getMetragem());
            pstm.setString(4, objeto.getIdentificacao());
            pstm.setInt(5, objeto.getAndar());
            pstm.setBoolean(6, objeto.isFlagAnimais());
            pstm.setString(7, objeto.getObs());
            pstm.setString(8, String.valueOf(objeto.getStatus()));
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<Quarto> Retrieve() {
        String sqlInstrucao = "SELECT id, descricao, capacidade_hospedes, metragem, identificacao, andar, flag_animais, obs, status FROM quarto WHERE status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Quarto> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            rst = pstm.executeQuery();

            while (rst.next()) {
                Quarto quarto = new Quarto();
                quarto.setId(rst.getInt("id"));
                quarto.setDescricao(rst.getString("descricao"));
                quarto.setCapacidadeHospedes(rst.getInt("capacidade_hospedes"));
                quarto.setMetragem(rst.getFloat("metragem"));
                quarto.setIdentificacao(rst.getString("identificacao"));
                quarto.setAndar(rst.getInt("andar"));
                quarto.setFlagAnimais(rst.getBoolean("flag_animais"));
                quarto.setObs(rst.getString("obs"));
                quarto.setStatus(rst.getString("status").charAt(0));
                lista.add(quarto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public Quarto Retrieve(int id) {
        String sqlInstrucao = "SELECT id, descricao, capacidade_hospedes, metragem, identificacao, andar, flag_animais, obs, status FROM quarto WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Quarto quarto = new Quarto();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                quarto.setId(rst.getInt("id"));
                quarto.setDescricao(rst.getString("descricao"));
                quarto.setCapacidadeHospedes(rst.getInt("capacidade_hospedes"));
                quarto.setMetragem(rst.getFloat("metragem"));
                quarto.setIdentificacao(rst.getString("identificacao"));
                quarto.setAndar(rst.getInt("andar"));
                quarto.setFlagAnimais(rst.getBoolean("flag_animais"));
                quarto.setObs(rst.getString("obs"));
                quarto.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return quarto;
        }
    }

    @Override
    public List<Quarto> Retrieve(String atributo, String valor) {
         String colunaBusca = atributo;
         // Ajuste para nomes de coluna, se necessário (ex: capacidade_hospedes)
         if (atributo.equalsIgnoreCase("identificacao")) {
             colunaBusca = "identificacao";
         } else if (atributo.equalsIgnoreCase("andar")) {
              colunaBusca = "andar";
         } else if (atributo.equalsIgnoreCase("descricao")) {
              colunaBusca = "descricao";
         }


        String sqlInstrucao = "SELECT id, descricao, capacidade_hospedes, metragem, identificacao, andar, flag_animais, obs, status FROM quarto WHERE " + colunaBusca + " LIKE ? AND status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Quarto> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Quarto quarto = new Quarto();
                quarto.setId(rst.getInt("id"));
                quarto.setDescricao(rst.getString("descricao"));
                quarto.setCapacidadeHospedes(rst.getInt("capacidade_hospedes"));
                quarto.setMetragem(rst.getFloat("metragem"));
                quarto.setIdentificacao(rst.getString("identificacao"));
                quarto.setAndar(rst.getInt("andar"));
                quarto.setFlagAnimais(rst.getBoolean("flag_animais"));
                quarto.setObs(rst.getString("obs"));
                quarto.setStatus(rst.getString("status").charAt(0));
                lista.add(quarto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public void Update(Quarto objeto) {
        String sqlInstrucao = "UPDATE quarto SET descricao = ?, capacidade_hospedes = ?, metragem = ?, identificacao = ?, andar = ?, flag_animais = ?, obs = ?, status = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;

        if (conexao == null) {
             System.err.println("ERRO FATAL: Conexão NULA ao tentar atualizar Quarto ID: " + objeto.getId());
             return;
        }

        try {
             originalAutoCommitState = conexao.getAutoCommit();
             if(originalAutoCommitState){
                conexao.setAutoCommit(false);
             }
             
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setInt(2, objeto.getCapacidadeHospedes());
            pstm.setFloat(3, objeto.getMetragem());
            pstm.setString(4, objeto.getIdentificacao());
            pstm.setInt(5, objeto.getAndar());
            pstm.setBoolean(6, objeto.isFlagAnimais());
            pstm.setString(7, objeto.getObs());
            pstm.setString(8, String.valueOf(objeto.getStatus()));
            pstm.setInt(9, objeto.getId());
            
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
    public void Delete(Quarto objeto) {
        String sqlInstrucao = "UPDATE quarto SET status = 'I' WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true; 

        if (conexao == null) {
             System.err.println("ERRO FATAL: Conexão NULA ao tentar deletar Quarto ID: " + objeto.getId());
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