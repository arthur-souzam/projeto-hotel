package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.bo.Quarto;
import model.bo.Reserva;
import model.bo.ReservaQuarto;

public class ReservaQuartoDAO implements InterfaceDAO<ReservaQuarto> {

    @Override
    public void Create(ReservaQuarto objeto) {
        String sql = "INSERT INTO reserva_quarto (data_hora_inicio, data_hora_fim, obs, status, reserva_id, quarto_id) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setTimestamp(1, Timestamp.valueOf(objeto.getDataHoraInicio()));
             if (objeto.getDataHoraFim() != null) { pstm.setTimestamp(2, Timestamp.valueOf(objeto.getDataHoraFim())); } else { pstm.setNull(2, Types.TIMESTAMP); }
            pstm.setString(3, objeto.getObs());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.setInt(5, objeto.getReserva().getId());
            pstm.setInt(6, objeto.getQuarto().getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<ReservaQuarto> Retrieve() {
        String sql = "SELECT rq.*, r.id as r_id, q.id as q_id, q.identificacao as q_ident " +
                     "FROM reserva_quarto rq JOIN reserva r ON rq.reserva_id = r.id JOIN quarto q ON rq.quarto_id = q.id WHERE rq.status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<ReservaQuarto> lista = new ArrayList<>();
        try {
            pstm = conexao.prepareStatement(sql);
            rst = pstm.executeQuery();
            while (rst.next()) {
                ReservaQuarto rq = new ReservaQuarto();
                fillEntityFromResultSet(rst, rq);
                lista.add(rq);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public ReservaQuarto Retrieve(int id) {
        String sql = "SELECT rq.*, r.id as r_id, q.id as q_id, q.identificacao as q_ident " +
                     "FROM reserva_quarto rq JOIN reserva r ON rq.reserva_id = r.id JOIN quarto q ON rq.quarto_id = q.id WHERE rq.id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        ReservaQuarto rq = new ReservaQuarto();
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();
            if (rst.next()) {
                fillEntityFromResultSet(rst, rq);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return rq;
    }

    @Override
    public List<ReservaQuarto> Retrieve(String atributo, String valor) {
        String coluna = atributo; // Ajustar se necessário
        String sql = "SELECT rq.*, r.id as r_id, q.id as q_id, q.identificacao as q_ident " +
                     "FROM reserva_quarto rq JOIN reserva r ON rq.reserva_id = r.id JOIN quarto q ON rq.quarto_id = q.id WHERE rq." + coluna + " LIKE ? AND rq.status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<ReservaQuarto> lista = new ArrayList<>();
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();
            while (rst.next()) {
                ReservaQuarto rq = new ReservaQuarto();
                fillEntityFromResultSet(rst, rq);
                lista.add(rq);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public void Update(ReservaQuarto objeto) {
        String sql = "UPDATE reserva_quarto SET data_hora_inicio = ?, data_hora_fim = ?, obs = ?, status = ?, reserva_id = ?, quarto_id = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;
        if (conexao == null) { System.err.println("ERRO: Conexão NULA ao atualizar ReservaQuarto ID: " + objeto.getId()); return; }
        try {
            originalAutoCommitState = conexao.getAutoCommit(); if (originalAutoCommitState) { conexao.setAutoCommit(false); }
            pstm = conexao.prepareStatement(sql);
            pstm.setTimestamp(1, Timestamp.valueOf(objeto.getDataHoraInicio()));
            if (objeto.getDataHoraFim() != null) { pstm.setTimestamp(2, Timestamp.valueOf(objeto.getDataHoraFim())); } else { pstm.setNull(2, Types.TIMESTAMP); }
            pstm.setString(3, objeto.getObs());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.setInt(5, objeto.getReserva().getId());
            pstm.setInt(6, objeto.getQuarto().getId());
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
    public void Delete(ReservaQuarto objeto) {
        String sql = "UPDATE reserva_quarto SET status = 'I' WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;
        if (conexao == null) { System.err.println("ERRO: Conexão NULA ao deletar ReservaQuarto ID: " + objeto.getId()); return; }
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

    private void fillEntityFromResultSet(ResultSet rst, ReservaQuarto rq) throws SQLException {
        rq.setId(rst.getInt("id"));
        rq.setDataHoraInicio(rst.getTimestamp("data_hora_inicio").toLocalDateTime());
        Timestamp tsFim = rst.getTimestamp("data_hora_fim");
        rq.setDataHoraFim(tsFim != null ? tsFim.toLocalDateTime() : null);
        rq.setObs(rst.getString("obs"));
        rq.setStatus(rst.getString("status").charAt(0));

        Reserva reserva = new Reserva();
        reserva.setId(rst.getInt("r_id"));
        rq.setReserva(reserva);

        Quarto quarto = new Quarto();
        quarto.setId(rst.getInt("q_id"));
        quarto.setIdentificacao(rst.getString("q_ident"));
        rq.setQuarto(quarto);
    }
}