package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.bo.Check;
import model.bo.CheckHospede;
import model.bo.Hospede;

public class CheckHospedeDAO implements InterfaceDAO<CheckHospede> {

    @Override
    public void Create(CheckHospede objeto) {
        String sql = "INSERT INTO check_hospede (tipo_hospede, obs, status, check_id, hospede_id) VALUES (?, ?, ?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, objeto.getTipoHospede());
            pstm.setString(2, objeto.getObs());
            pstm.setString(3, String.valueOf(objeto.getStatus()));
            pstm.setInt(4, objeto.getCheck().getId());
            pstm.setInt(5, objeto.getHospede().getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<CheckHospede> Retrieve() {
        String sql = "SELECT ch.*, c.id as c_id, h.id as h_id, h.nome as h_nome " +
                     "FROM check_hospede ch JOIN check c ON ch.check_id = c.id JOIN hospede h ON ch.hospede_id = h.id WHERE ch.status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<CheckHospede> lista = new ArrayList<>();
        try {
            pstm = conexao.prepareStatement(sql);
            rst = pstm.executeQuery();
            while (rst.next()) {
                CheckHospede ch = new CheckHospede();
                fillEntityFromResultSet(rst, ch);
                lista.add(ch);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public CheckHospede Retrieve(int id) {
        String sql = "SELECT ch.*, c.id as c_id, h.id as h_id, h.nome as h_nome " +
                     "FROM check_hospede ch JOIN check c ON ch.check_id = c.id JOIN hospede h ON ch.hospede_id = h.id WHERE ch.id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        CheckHospede ch = new CheckHospede();
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();
            if (rst.next()) {
                fillEntityFromResultSet(rst, ch);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return ch;
    }

    @Override
    public List<CheckHospede> Retrieve(String atributo, String valor) {
        String coluna = atributo; // Ajustar se necessário (ex: tipo_hospede)
        String sql = "SELECT ch.*, c.id as c_id, h.id as h_id, h.nome as h_nome " +
                     "FROM check_hospede ch JOIN check c ON ch.check_id = c.id JOIN hospede h ON ch.hospede_id = h.id WHERE ch." + coluna + " LIKE ? AND ch.status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<CheckHospede> lista = new ArrayList<>();
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();
            while (rst.next()) {
                CheckHospede ch = new CheckHospede();
                fillEntityFromResultSet(rst, ch);
                lista.add(ch);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public void Update(CheckHospede objeto) {
        String sql = "UPDATE check_hospede SET tipo_hospede = ?, obs = ?, status = ?, check_id = ?, hospede_id = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;
        if (conexao == null) { System.err.println("ERRO: Conexão NULA ao atualizar CheckHospede ID: " + objeto.getId()); return; }
        try {
            originalAutoCommitState = conexao.getAutoCommit(); if (originalAutoCommitState) { conexao.setAutoCommit(false); }
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, objeto.getTipoHospede());
            pstm.setString(2, objeto.getObs());
            pstm.setString(3, String.valueOf(objeto.getStatus()));
            pstm.setInt(4, objeto.getCheck().getId());
            pstm.setInt(5, objeto.getHospede().getId());
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
    public void Delete(CheckHospede objeto) {
        String sql = "UPDATE check_hospede SET status = 'I' WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;
        if (conexao == null) { System.err.println("ERRO: Conexão NULA ao deletar CheckHospede ID: " + objeto.getId()); return; }
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

    private void fillEntityFromResultSet(ResultSet rst, CheckHospede ch) throws SQLException {
        ch.setId(rst.getInt("id"));
        ch.setTipoHospede(rst.getString("tipo_hospede"));
        ch.setObs(rst.getString("obs"));
        ch.setStatus(rst.getString("status").charAt(0));

        Check check = new Check();
        check.setId(rst.getInt("c_id")); 
        ch.setCheck(check);

        Hospede hospede = new Hospede();
        hospede.setId(rst.getInt("h_id")); 
        hospede.setNome(rst.getString("h_nome"));
        ch.setHospede(hospede);
    }
}