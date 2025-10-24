package model.DAO;

import java.sql.Connection;
import java.sql.Date; // Para LocalDate
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.bo.Check;
import model.bo.Reserva;

public class ReservaDAO implements InterfaceDAO<Reserva> {

    @Override
    public void Create(Reserva objeto) {
        String sql = "INSERT INTO reserva (data_hora_reserva, data_prevista_entrada, data_prevista_saida, obs, status, check_id) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setTimestamp(1, Timestamp.valueOf(objeto.getDataHoraReserva()));
            pstm.setDate(2, Date.valueOf(objeto.getDataPrevistaEntrada()));
            pstm.setDate(3, Date.valueOf(objeto.getDataPrevistaSaida()));
            pstm.setString(4, objeto.getObs());
            pstm.setString(5, String.valueOf(objeto.getStatus()));
            if (objeto.getCheck() != null) {
                pstm.setInt(6, objeto.getCheck().getId());
            } else {
                pstm.setNull(6, Types.INTEGER);
            }
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<Reserva> Retrieve() {
        String sql = "SELECT r.*, c.id as c_id FROM reserva r LEFT JOIN check c ON r.check_id = c.id WHERE r.status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Reserva> lista = new ArrayList<>();
        try {
            pstm = conexao.prepareStatement(sql);
            rst = pstm.executeQuery();
            while (rst.next()) {
                Reserva reserva = new Reserva();
                fillEntityFromResultSet(rst, reserva);
                lista.add(reserva);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public Reserva Retrieve(int id) {
        String sql = "SELECT r.*, c.id as c_id FROM reserva r LEFT JOIN check c ON r.check_id = c.id WHERE r.id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Reserva reserva = new Reserva();
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();
            if (rst.next()) {
                fillEntityFromResultSet(rst, reserva);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return reserva;
    }

    @Override
    public List<Reserva> Retrieve(String atributo, String valor) {
        String coluna = atributo; // Ajustar se necessário
        String sql = "SELECT r.*, c.id as c_id FROM reserva r LEFT JOIN check c ON r.check_id = c.id WHERE r." + coluna + " LIKE ? AND r.status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Reserva> lista = new ArrayList<>();
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();
            while (rst.next()) {
                Reserva reserva = new Reserva();
                fillEntityFromResultSet(rst, reserva);
                lista.add(reserva);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public void Update(Reserva objeto) {
        String sql = "UPDATE reserva SET data_hora_reserva = ?, data_prevista_entrada = ?, data_prevista_saida = ?, obs = ?, status = ?, check_id = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;
        if (conexao == null) { System.err.println("ERRO: Conexão NULA ao atualizar Reserva ID: " + objeto.getId()); return; }
        try {
            originalAutoCommitState = conexao.getAutoCommit(); if (originalAutoCommitState) { conexao.setAutoCommit(false); }
            pstm = conexao.prepareStatement(sql);
            pstm.setTimestamp(1, Timestamp.valueOf(objeto.getDataHoraReserva()));
            pstm.setDate(2, Date.valueOf(objeto.getDataPrevistaEntrada()));
            pstm.setDate(3, Date.valueOf(objeto.getDataPrevistaSaida()));
            pstm.setString(4, objeto.getObs());
            pstm.setString(5, String.valueOf(objeto.getStatus()));
            if (objeto.getCheck() != null) { pstm.setInt(6, objeto.getCheck().getId()); } else { pstm.setNull(6, Types.INTEGER); }
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
    public void Delete(Reserva objeto) {
        String sql = "UPDATE reserva SET status = 'I' WHERE id = ?"; // Assume 'I' para inativo
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;
        if (conexao == null) { System.err.println("ERRO: Conexão NULA ao deletar Reserva ID: " + objeto.getId()); return; }
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

    private void fillEntityFromResultSet(ResultSet rst, Reserva reserva) throws SQLException {
        reserva.setId(rst.getInt("id"));
        reserva.setDataHoraReserva(rst.getTimestamp("data_hora_reserva").toLocalDateTime());
        reserva.setDataPrevistaEntrada(rst.getDate("data_prevista_entrada").toLocalDate());
        reserva.setDataPrevistaSaida(rst.getDate("data_prevista_saida").toLocalDate());
        reserva.setObs(rst.getString("obs"));
        reserva.setStatus(rst.getString("status").charAt(0));

        int checkId = rst.getInt("c_id");
        if (!rst.wasNull()) {
            Check check = new Check();
            check.setId(checkId);
            reserva.setCheck(check);
        } else {
            reserva.setCheck(null);
        }
    }
}