package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bo.Check;
import model.bo.Receber;

public class ReceberDAO implements InterfaceDAO<Receber> {

    @Override
    public void Create(Receber objeto) {
        String sqlInstrucao = "INSERT INTO receber "
                + "(data_emissao, data_vencimento, valor_emitido, valor_acrescimo, valor_desconto, valor_pago, data_pagamento, status, check_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setTimestamp(1, new java.sql.Timestamp(objeto.getDataEmissao().getTime()));
            pstm.setTimestamp(2, new java.sql.Timestamp(objeto.getDataVencimento().getTime()));
            pstm.setFloat(3, objeto.getValorEmitido());
            pstm.setFloat(4, objeto.getValorAcrescimo());
            pstm.setFloat(5, objeto.getValorDesconto());
            pstm.setFloat(6, objeto.getValorPago());
            if (objeto.getDataPagamento() != null) {
                pstm.setTimestamp(7, new java.sql.Timestamp(objeto.getDataPagamento().getTime()));
            } else {
                pstm.setNull(7, java.sql.Types.TIMESTAMP);
            }
            pstm.setString(8, String.valueOf(objeto.getStatus()));
            pstm.setInt(9, objeto.getCheck().getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<Receber> Retrieve() {
        String sqlInstrucao = "SELECT * FROM receber";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Receber> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            rst = pstm.executeQuery();

            while (rst.next()) {
                Receber receber = new Receber();
                receber.setId(rst.getInt("id"));
                receber.setDataEmissao(rst.getTimestamp("data_emissao"));
                receber.setDataVencimento(rst.getTimestamp("data_vencimento"));
                receber.setValorEmitido(rst.getFloat("valor_emitido"));
                receber.setValorAcrescimo(rst.getFloat("valor_acrescimo"));
                receber.setValorDesconto(rst.getFloat("valor_desconto"));
                receber.setValorPago(rst.getFloat("valor_pago"));
                receber.setDataPagamento(rst.getTimestamp("data_pagamento"));
                receber.setStatus(rst.getString("status").charAt(0));

                Check check = new Check();
                check.setId(rst.getInt("check_id"));
                receber.setCheck(check);

                lista.add(receber);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }

    @Override
    public Receber Retrieve(int id) {
        String sqlInstrucao = "SELECT * FROM receber WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Receber receber = new Receber();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                receber.setId(rst.getInt("id"));
                receber.setDataEmissao(rst.getTimestamp("data_emissao"));
                receber.setDataVencimento(rst.getTimestamp("data_vencimento"));
                receber.setValorEmitido(rst.getFloat("valor_emitido"));
                receber.setValorAcrescimo(rst.getFloat("valor_acrescimo"));
                receber.setValorDesconto(rst.getFloat("valor_desconto"));
                receber.setValorPago(rst.getFloat("valor_pago"));
                receber.setDataPagamento(rst.getTimestamp("data_pagamento"));
                receber.setStatus(rst.getString("status").charAt(0));

                Check check = new Check();
                check.setId(rst.getInt("check_id"));
                receber.setCheck(check);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return receber;
        }
    }

    @Override
    public List<Receber> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "SELECT * FROM receber WHERE " + atributo + " LIKE ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Receber> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Receber receber = new Receber();
                receber.setId(rst.getInt("id"));
                receber.setDataEmissao(rst.getTimestamp("data_emissao"));
                receber.setDataVencimento(rst.getTimestamp("data_vencimento"));
                receber.setValorEmitido(rst.getFloat("valor_emitido"));
                receber.setValorAcrescimo(rst.getFloat("valor_acrescimo"));
                receber.setValorDesconto(rst.getFloat("valor_desconto"));
                receber.setValorPago(rst.getFloat("valor_pago"));
                receber.setDataPagamento(rst.getTimestamp("data_pagamento"));
                receber.setStatus(rst.getString("status").charAt(0));

                Check check = new Check();
                check.setId(rst.getInt("check_id"));
                receber.setCheck(check);

                lista.add(receber);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public void Update(Receber objeto) {
        String sqlInstrucao = "UPDATE receber SET data_emissao = ?, data_vencimento = ?, valor_emitido = ?, "
                + "valor_acrescimo = ?, valor_desconto = ?, valor_pago = ?, data_pagamento = ?, status = ?, check_id = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setTimestamp(1, new java.sql.Timestamp(objeto.getDataEmissao().getTime()));
            pstm.setTimestamp(2, new java.sql.Timestamp(objeto.getDataVencimento().getTime()));
            pstm.setFloat(3, objeto.getValorEmitido());
            pstm.setFloat(4, objeto.getValorAcrescimo());
            pstm.setFloat(5, objeto.getValorDesconto());
            pstm.setFloat(6, objeto.getValorPago());
            if (objeto.getDataPagamento() != null) {
                pstm.setTimestamp(7, new java.sql.Timestamp(objeto.getDataPagamento().getTime()));
            } else {
                pstm.setNull(7, java.sql.Types.TIMESTAMP);
            }
            pstm.setString(8, String.valueOf(objeto.getStatus()));
            pstm.setInt(9, objeto.getCheck().getId());
            pstm.setInt(10, objeto.getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Receber objeto) {
        String sqlInstrucao = "UPDATE receber SET status = 'I' WHERE id = ?";
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