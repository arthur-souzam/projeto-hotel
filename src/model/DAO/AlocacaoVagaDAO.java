package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.bo.AlocacaoVaga;
import model.bo.Check;
import model.bo.Vaga;
import model.bo.Veiculo;

public class AlocacaoVagaDAO implements InterfaceDAO<AlocacaoVaga> {

    @Override
    public void Create(AlocacaoVaga objeto) {
        String sql = "INSERT INTO alocacao_vaga (obs, status, veiculo_id, vaga_estacionamento_id, check_id) VALUES (?, ?, ?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, objeto.getObs());
            pstm.setString(2, String.valueOf(objeto.getStatus()));
            pstm.setInt(3, objeto.getVeiculo().getId());
            pstm.setInt(4, objeto.getVaga().getId());
            pstm.setInt(5, objeto.getCheck().getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<AlocacaoVaga> Retrieve() {
        String sql = "SELECT av.*, v.id as v_id, v.placa as v_placa, vg.id as vg_id, vg.descricao as vg_desc, c.id as c_id " +
                     "FROM alocacao_vaga av JOIN veiculo v ON av.veiculo_id = v.id JOIN vaga_estacionamento vg ON av.vaga_estacionamento_id = vg.id JOIN check c ON av.check_id = c.id " +
                     "WHERE av.status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<AlocacaoVaga> lista = new ArrayList<>();
        try {
            pstm = conexao.prepareStatement(sql);
            rst = pstm.executeQuery();
            while (rst.next()) {
                AlocacaoVaga av = new AlocacaoVaga();
                fillEntityFromResultSet(rst, av);
                lista.add(av);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public AlocacaoVaga Retrieve(int id) {
        String sql = "SELECT av.*, v.id as v_id, v.placa as v_placa, vg.id as vg_id, vg.descricao as vg_desc, c.id as c_id " +
                     "FROM alocacao_vaga av JOIN veiculo v ON av.veiculo_id = v.id JOIN vaga_estacionamento vg ON av.vaga_estacionamento_id = vg.id JOIN check c ON av.check_id = c.id " +
                     "WHERE av.id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        AlocacaoVaga av = new AlocacaoVaga();
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();
            if (rst.next()) {
                fillEntityFromResultSet(rst, av);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return av;
    }

    @Override
    public List<AlocacaoVaga> Retrieve(String atributo, String valor) {
        String coluna = atributo; // Ajustar se necessário
        String sql = "SELECT av.*, v.id as v_id, v.placa as v_placa, vg.id as vg_id, vg.descricao as vg_desc, c.id as c_id " +
                     "FROM alocacao_vaga av JOIN veiculo v ON av.veiculo_id = v.id JOIN vaga_estacionamento vg ON av.vaga_estacionamento_id = vg.id JOIN check c ON av.check_id = c.id " +
                     "WHERE av." + coluna + " LIKE ? AND av.status = 'A'";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<AlocacaoVaga> lista = new ArrayList<>();
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();
            while (rst.next()) {
                AlocacaoVaga av = new AlocacaoVaga();
                fillEntityFromResultSet(rst, av);
                lista.add(av);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public void Update(AlocacaoVaga objeto) {
        String sql = "UPDATE alocacao_vaga SET obs = ?, status = ?, veiculo_id = ?, vaga_estacionamento_id = ?, check_id = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;
        if (conexao == null) { System.err.println("ERRO: Conexão NULA ao atualizar AlocacaoVaga ID: " + objeto.getId()); return; }
        try {
            originalAutoCommitState = conexao.getAutoCommit(); if (originalAutoCommitState) { conexao.setAutoCommit(false); }
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, objeto.getObs());
            pstm.setString(2, String.valueOf(objeto.getStatus()));
            pstm.setInt(3, objeto.getVeiculo().getId());
            pstm.setInt(4, objeto.getVaga().getId());
            pstm.setInt(5, objeto.getCheck().getId());
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
    public void Delete(AlocacaoVaga objeto) {
        String sql = "UPDATE alocacao_vaga SET status = 'I' WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        boolean originalAutoCommitState = true;
        if (conexao == null) { System.err.println("ERRO: Conexão NULA ao deletar AlocacaoVaga ID: " + objeto.getId()); return; }
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

    private void fillEntityFromResultSet(ResultSet rst, AlocacaoVaga av) throws SQLException {
        av.setId(rst.getInt("id"));
        av.setObs(rst.getString("obs"));
        av.setStatus(rst.getString("status").charAt(0));

        Veiculo veiculo = new Veiculo();
        veiculo.setId(rst.getInt("v_id"));
        veiculo.setPlaca(rst.getString("v_placa"));
        av.setVeiculo(veiculo);

        Vaga vaga = new Vaga();
        vaga.setId(rst.getInt("vg_id"));
        vaga.setDescricao(rst.getString("vg_desc"));
        av.setVaga(vaga);

        Check check = new Check();
        check.setId(rst.getInt("c_id"));
        av.setCheck(check);
    }
}