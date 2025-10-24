package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.bo.Check;
import model.bo.CheckQuarto;
import model.bo.Quarto;

public class CheckDAO implements InterfaceDAO<Check> {

    @Override
    public void Create(Check objeto) {
        String sql = "INSERT INTO check (data_hora_cadastro, data_hora_entrada, data_hora_saida, obs, status, check_quarto_id) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setTimestamp(1, Timestamp.valueOf(objeto.getDataHoraCadastro()));
            pstm.setTimestamp(2, Timestamp.valueOf(objeto.getDataHoraEntrada()));
            if (objeto.getDataHoraSaida() != null) {
                pstm.setTimestamp(3, Timestamp.valueOf(objeto.getDataHoraSaida()));
            } else {
                pstm.setNull(3, Types.TIMESTAMP);
            }
            pstm.setString(4, objeto.getObs());
            pstm.setString(5, String.valueOf(objeto.getStatus()));
            pstm.setInt(6, objeto.getCheckQuarto().getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<Check> Retrieve() {
        String sql = "SELECT c.*, cq.id as cq_id, cq.data_hora_inicio as cq_inicio, cq.data_hora_fim as cq_fim, cq.obs as cq_obs, cq.status as cq_status, "
                   + " q.id as q_id, q.descricao as q_desc, q.identificacao as q_ident "
                   + " FROM check c JOIN check_quarto cq ON c.check_quarto_id = cq.id JOIN quarto q ON cq.quarto_id = q.id "
                   + " WHERE c.status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Check> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sql);
            rst = pstm.executeQuery();
            while (rst.next()) {
                Check check = new Check();
                fillEntityFromResultSet(rst, check);
                lista.add(check);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public Check Retrieve(int id) {
         String sql = "SELECT c.*, cq.id as cq_id, cq.data_hora_inicio as cq_inicio, cq.data_hora_fim as cq_fim, cq.obs as cq_obs, cq.status as cq_status, "
                   + " q.id as q_id, q.descricao as q_desc, q.identificacao as q_ident "
                   + " FROM check c JOIN check_quarto cq ON c.check_quarto_id = cq.id JOIN quarto q ON cq.quarto_id = q.id "
                   + " WHERE c.id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Check check = new Check();

        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();
            if (rst.next()) {
                fillEntityFromResultSet(rst, check);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return check;
    }

    @Override
    public List<Check> Retrieve(String atributo, String valor) {
         String coluna = atributo;
         String sql = "SELECT c.*, cq.id as cq_id, cq.data_hora_inicio as cq_inicio, cq.data_hora_fim as cq_fim, cq.obs as cq_obs, cq.status as cq_status, "
                   + " q.id as q_id, q.descricao as q_desc, q.identificacao as q_ident "
                   + " FROM check c JOIN check_quarto cq ON c.check_quarto_id = cq.id JOIN quarto q ON cq.quarto_id = q.id "
                   + " WHERE c." + coluna + " LIKE ? AND c.status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Check> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();
            while (rst.next()) {
                Check check = new Check();
                fillEntityFromResultSet(rst, check);
                lista.add(check);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public void Update(Check objeto) {
        String sql = "UPDATE check SET data_hora_cadastro = ?, data_hora_entrada = ?, data_hora_saida = ?, obs = ?, status = ?, check_quarto_id = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;
        if (conexao == null) { System.err.println("ERRO: Conexão NULA ao atualizar Check ID: " + objeto.getId()); return; }
        try {
            originalAutoCommitState = conexao.getAutoCommit(); if (originalAutoCommitState) { conexao.setAutoCommit(false); }
            pstm = conexao.prepareStatement(sql);
            pstm.setTimestamp(1, Timestamp.valueOf(objeto.getDataHoraCadastro()));
            pstm.setTimestamp(2, Timestamp.valueOf(objeto.getDataHoraEntrada()));
            if (objeto.getDataHoraSaida() != null) { pstm.setTimestamp(3, Timestamp.valueOf(objeto.getDataHoraSaida())); } else { pstm.setNull(3, Types.TIMESTAMP); }
            pstm.setString(4, objeto.getObs());
            pstm.setString(5, String.valueOf(objeto.getStatus()));
            pstm.setInt(6, objeto.getCheckQuarto().getId());
            pstm.setInt(7, objeto.getId());
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
    public void Delete(Check objeto) {
        String sql = "UPDATE check SET status = 'I' WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;
        if (conexao == null) { System.err.println("ERRO: Conexão NULA ao deletar Check ID: " + objeto.getId()); return; }
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

    private void fillEntityFromResultSet(ResultSet rst, Check check) throws SQLException {
        check.setId(rst.getInt("id"));
        check.setDataHoraCadastro(rst.getTimestamp("data_hora_cadastro").toLocalDateTime());
        check.setDataHoraEntrada(rst.getTimestamp("data_hora_entrada").toLocalDateTime());
        Timestamp tsSaida = rst.getTimestamp("data_hora_saida");
        check.setDataHoraSaida(tsSaida != null ? tsSaida.toLocalDateTime() : null);
        check.setObs(rst.getString("obs"));
        check.setStatus(rst.getString("status").charAt(0));

        CheckQuarto cq = new CheckQuarto();
        cq.setId(rst.getInt("cq_id"));
        cq.setDataHoraInicio(rst.getTimestamp("cq_inicio").toLocalDateTime());
        Timestamp tsCqFim = rst.getTimestamp("cq_fim");
        cq.setDataHoraFim(tsCqFim != null ? tsCqFim.toLocalDateTime() : null);
        cq.setObs(rst.getString("cq_obs"));
        cq.setStatus(rst.getString("cq_status").charAt(0));
        
        Quarto quarto = new Quarto();
        quarto.setId(rst.getInt("q_id"));
        quarto.setDescricao(rst.getString("q_desc"));
        quarto.setIdentificacao(rst.getString("q_ident"));
        
        cq.setQuarto(quarto);
        check.setCheckQuarto(cq);
    }
}