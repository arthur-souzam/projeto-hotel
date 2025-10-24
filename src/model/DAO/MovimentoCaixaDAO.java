package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bo.Caixa;
import model.bo.MovimentoCaixa;
import model.bo.Receber;

public class MovimentoCaixaDAO implements InterfaceDAO<MovimentoCaixa> {

    @Override
    public void Create(MovimentoCaixa objeto) {
        String sqlInstrucao = "INSERT INTO movimento_caixa "
                + "(data_movimento, valor_movimento, observacao, flag_tipo_movimento, status, caixa_id, receber_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setTimestamp(1, new java.sql.Timestamp(objeto.getDataMovimento().getTime()));
            pstm.setFloat(2, objeto.getValorMovimento());
            pstm.setString(3, objeto.getObservacao());
            pstm.setString(4, String.valueOf(objeto.getFlagTipoMovimento()));
            pstm.setString(5, String.valueOf(objeto.getStatus()));
            pstm.setInt(6, objeto.getCaixa().getId());

            if (objeto.getReceber() != null) {
                pstm.setInt(7, objeto.getReceber().getId());
            } else {
                pstm.setNull(7, java.sql.Types.INTEGER);
            }

            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<MovimentoCaixa> Retrieve() {
        String sqlInstrucao = "SELECT * FROM movimento_caixa";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<MovimentoCaixa> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            rst = pstm.executeQuery();

            while (rst.next()) {
                MovimentoCaixa movimento = new MovimentoCaixa();
                movimento.setId(rst.getInt("id"));
                movimento.setDataMovimento(rst.getTimestamp("data_movimento"));
                movimento.setValorMovimento(rst.getFloat("valor_movimento"));
                movimento.setObservacao(rst.getString("observacao"));
                movimento.setFlagTipoMovimento(rst.getString("flag_tipo_movimento").charAt(0));
                movimento.setStatus(rst.getString("status").charAt(0));

                CaixaDAO caixaDAO = new CaixaDAO();
                movimento.setCaixa(caixaDAO.Retrieve(rst.getInt("caixa_id")));

                int receberId = rst.getInt("receber_id");
                if (!rst.wasNull()) {
                     //Quando ReceberDAO existir:
                     //ReceberDAO receberDAO = new ReceberDAO();
                     //movimento.setReceber(receberDAO.Retrieve(receberId));
                     Receber receber = new Receber(); // Placeholder
                     receber.setId(receberId);
                     movimento.setReceber(receber); // Define o receber com ID
                } else {
                     movimento.setReceber(null); // Define como nulo se for nulo no banco
                }

                lista.add(movimento);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
        }
        return lista;
    }


    @Override
    public MovimentoCaixa Retrieve(int id) {
        String sqlInstrucao = "SELECT * FROM movimento_caixa WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        MovimentoCaixa movimento = new MovimentoCaixa();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                movimento.setId(rst.getInt("id"));
                movimento.setDataMovimento(rst.getTimestamp("data_movimento"));
                movimento.setValorMovimento(rst.getFloat("valor_movimento"));
                movimento.setObservacao(rst.getString("observacao"));
                movimento.setFlagTipoMovimento(rst.getString("flag_tipo_movimento").charAt(0));
                movimento.setStatus(rst.getString("status").charAt(0));

                CaixaDAO caixaDAO = new CaixaDAO();
                movimento.setCaixa(caixaDAO.Retrieve(rst.getInt("caixa_id")));

                int receberId = rst.getInt("receber_id");
                 if (!rst.wasNull()) {
                     Receber receber = new Receber();
                     receber.setId(receberId);
                     movimento.setReceber(receber);
                 } else {
                      movimento.setReceber(null);
                 }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return movimento;
        }
    }

    @Override
    public List<MovimentoCaixa> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "SELECT * FROM movimento_caixa WHERE " + atributo + " LIKE ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<MovimentoCaixa> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                MovimentoCaixa movimento = new MovimentoCaixa();
                movimento.setId(rst.getInt("id"));
                movimento.setDataMovimento(rst.getTimestamp("data_movimento"));
                movimento.setValorMovimento(rst.getFloat("valor_movimento"));
                movimento.setObservacao(rst.getString("observacao"));
                movimento.setFlagTipoMovimento(rst.getString("flag_tipo_movimento").charAt(0));
                movimento.setStatus(rst.getString("status").charAt(0));

                CaixaDAO caixaDAO = new CaixaDAO();
                movimento.setCaixa(caixaDAO.Retrieve(rst.getInt("caixa_id")));

                int receberId = rst.getInt("receber_id");
                if (!rst.wasNull()) {
                    Receber receber = new Receber();
                    receber.setId(receberId);
                    movimento.setReceber(receber);
                } else {
                    movimento.setReceber(null);
                }

                lista.add(movimento);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public void Update(MovimentoCaixa objeto) {
        String sqlInstrucao = "UPDATE movimento_caixa SET data_movimento = ?, valor_movimento = ?, observacao = ?, "
                + "flag_tipo_movimento = ?, status = ?, caixa_id = ?, receber_id = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setTimestamp(1, new java.sql.Timestamp(objeto.getDataMovimento().getTime()));
            pstm.setFloat(2, objeto.getValorMovimento());
            pstm.setString(3, objeto.getObservacao());
            pstm.setString(4, String.valueOf(objeto.getFlagTipoMovimento()));
            pstm.setString(5, String.valueOf(objeto.getStatus()));
            pstm.setInt(6, objeto.getCaixa().getId());

            if (objeto.getReceber() != null) {
                pstm.setInt(7, objeto.getReceber().getId());
            } else {
                pstm.setNull(7, java.sql.Types.INTEGER);
            }

            pstm.setInt(8, objeto.getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(MovimentoCaixa objeto) {
        String sqlInstrucao = "UPDATE movimento_caixa SET status = 'I' WHERE id = ?";
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