package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.bo.CheckQuarto;
import model.bo.Quarto;

public class CheckQuartoDAO implements InterfaceDAO<CheckQuarto> {

    @Override
    public void Create(CheckQuarto objeto) {
        String sql = "INSERT INTO check_quarto (data_hora_inicio, data_hora_fim, obs, status, quarto_id) VALUES (?, ?, ?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setTimestamp(1, Timestamp.valueOf(objeto.getDataHoraInicio()));
            if (objeto.getDataHoraFim() != null) {
                pstm.setTimestamp(2, Timestamp.valueOf(objeto.getDataHoraFim()));
            } else {
                pstm.setNull(2, Types.TIMESTAMP);
            }
            pstm.setString(3, objeto.getObs());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.setInt(5, objeto.getQuarto().getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<CheckQuarto> Retrieve() {
        String sql = "SELECT cq.*, q.id as q_id, q.descricao as q_desc, q.identificacao as q_ident " + 
                     "FROM check_quarto cq JOIN quarto q ON cq.quarto_id = q.id WHERE cq.status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<CheckQuarto> lista = new ArrayList<>();
        try {
            pstm = conexao.prepareStatement(sql);
            rst = pstm.executeQuery();
            while (rst.next()) {
                CheckQuarto cq = new CheckQuarto();
                fillEntityFromResultSet(rst, cq);
                lista.add(cq);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public CheckQuarto Retrieve(int id) {
        String sql = "SELECT cq.*, q.id as q_id, q.descricao as q_desc, q.identificacao as q_ident " + 
                     "FROM check_quarto cq JOIN quarto q ON cq.quarto_id = q.id WHERE cq.id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        CheckQuarto cq = new CheckQuarto();
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();
            if (rst.next()) {
                fillEntityFromResultSet(rst, cq);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return cq;
    }

    @Override
    public List<CheckQuarto> Retrieve(String atributo, String valor) {
        String coluna = atributo; // Ajustar se necessário
        String sql = "SELECT cq.*, q.id as q_id, q.descricao as q_desc, q.identificacao as q_ident " + 
                     "FROM check_quarto cq JOIN quarto q ON cq.quarto_id = q.id WHERE cq." + coluna + " LIKE ? AND cq.status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<CheckQuarto> lista = new ArrayList<>();
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();
            while (rst.next()) {
                CheckQuarto cq = new CheckQuarto();
                fillEntityFromResultSet(rst, cq);
                lista.add(cq);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public void Update(CheckQuarto objeto) {
        String sql = "UPDATE check_quarto SET data_hora_inicio = ?, data_hora_fim = ?, obs = ?, status = ?, quarto_id = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;
        if (conexao == null) { System.err.println("ERRO: Conexão NULA ao atualizar CheckQuarto ID: " + objeto.getId()); return; }
        try {
            originalAutoCommitState = conexao.getAutoCommit(); if (originalAutoCommitState) { conexao.setAutoCommit(false); }
            pstm = conexao.prepareStatement(sql);
            pstm.setTimestamp(1, Timestamp.valueOf(objeto.getDataHoraInicio()));
            if (objeto.getDataHoraFim() != null) { pstm.setTimestamp(2, Timestamp.valueOf(objeto.getDataHoraFim())); } else { pstm.setNull(2, Types.TIMESTAMP); }
            pstm.setString(3, objeto.getObs());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.setInt(5, objeto.getQuarto().getId());
            pstm.setInt(6, objeto.getId());
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) { conexao.commit(); } else { conexao.rollback(); }
        } catch (SQLException ex) {
            ex.printStackTrace();
            try { if (conexao != null) { conexao.rollback(); } } catch (SQLException rbEx) { rbEx.printStackTrace(); }
        } finally {
            try { if (conexao != null && !conexao.isClosed()) { if (conexao.getAutoCommit() != originalAutoCommitState) { conexao.setAutoCommit(originalAutoCommitState); } } } catch (SQLException acEx) { acEx.printStackTrace(); }
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(CheckQuarto objeto) {
        String sql = "UPDATE check_quarto SET status = 'I' WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;
        if (conexao == null) { System.err.println("ERRO: Conexão NULA ao deletar CheckQuarto ID: " + objeto.getId()); return; }
        try {
            originalAutoCommitState = conexao.getAutoCommit(); if (originalAutoCommitState) { conexao.setAutoCommit(false); }
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, objeto.getId());
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) { conexao.commit(); } else { conexao.rollback(); }
        } catch (SQLException ex) {
            ex.printStackTrace();
            try { if (conexao != null) { conexao.rollback(); } } catch (SQLException rbEx) { rbEx.printStackTrace(); }
        } finally {
            try { if (conexao != null && !conexao.isClosed()) { if (conexao.getAutoCommit() != originalAutoCommitState) { conexao.setAutoCommit(originalAutoCommitState); } } } catch (SQLException acEx) { acEx.printStackTrace(); }
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    private void fillEntityFromResultSet(ResultSet rst, CheckQuarto cq) throws SQLException {
        cq.setId(rst.getInt("id"));
        cq.setDataHoraInicio(rst.getTimestamp("data_hora_inicio").toLocalDateTime());
        Timestamp tsFim = rst.getTimestamp("data_hora_fim");
        cq.setDataHoraFim(tsFim != null ? tsFim.toLocalDateTime() : null);
        cq.setObs(rst.getString("obs"));
        cq.setStatus(rst.getString("status").charAt(0));

        Quarto quarto = new Quarto();
        quarto.setId(rst.getInt("q_id"));
        quarto.setDescricao(rst.getString("q_desc"));
        quarto.setIdentificacao(rst.getString("q_ident"));
        cq.setQuarto(quarto);
    }
}