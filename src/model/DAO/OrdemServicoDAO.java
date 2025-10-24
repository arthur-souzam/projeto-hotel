package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bo.OrdemServico;
import model.bo.Check;
import model.bo.Servico;
import model.bo.Quarto;

public class OrdemServicoDAO implements InterfaceDAO<OrdemServico> {

    @Override
    public void Create(OrdemServico objeto) {
        String sqlInstrucao = "INSERT INTO oderm_servico "
                + "(data_hora_cadastro, data_hora_prevista_inicio, data_hora_prevista_termino, obs, status, check_id, servico_id, quarto_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setTimestamp(1, new java.sql.Timestamp(objeto.getDataHoraCadastro().getTime()));
            pstm.setTimestamp(2, new java.sql.Timestamp(objeto.getDataHoraPrevistaInicio().getTime()));
            pstm.setTimestamp(3, new java.sql.Timestamp(objeto.getDataHoraPrevistaTermino().getTime()));
            pstm.setString(4, objeto.getObs());
            pstm.setString(5, String.valueOf(objeto.getStatus()));
            pstm.setInt(6, objeto.getCheck().getId());
            pstm.setInt(7, objeto.getServico().getId());
            pstm.setInt(8, objeto.getQuarto().getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public List<OrdemServico> Retrieve() {
        String sqlInstrucao = "SELECT * FROM oderm_servico";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<OrdemServico> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            rst = pstm.executeQuery();

            while (rst.next()) {
                OrdemServico os = new OrdemServico();
                os.setId(rst.getInt("id"));
                os.setDataHoraCadastro(rst.getTimestamp("data_hora_cadastro"));
                os.setDataHoraPrevistaInicio(rst.getTimestamp("data_hora_prevista_inicio"));
                os.setDataHoraPrevistaTermino(rst.getTimestamp("data_hora_prevista_termino"));
                os.setObs(rst.getString("obs"));
                os.setStatus(rst.getString("status").charAt(0));

                ServicoDAO servicoDAO = new ServicoDAO();
                os.setServico(servicoDAO.Retrieve(rst.getInt("servico_id")));

                Check check = new Check();
                check.setId(rst.getInt("check_id"));
                os.setCheck(check);

                Quarto quarto = new Quarto();
                quarto.setId(rst.getInt("quarto_id"));
                os.setQuarto(quarto);

                lista.add(os);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public OrdemServico Retrieve(int id) {
        String sqlInstrucao = "SELECT * FROM oderm_servico WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        OrdemServico os = new OrdemServico();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                os.setId(rst.getInt("id"));
                os.setDataHoraCadastro(rst.getTimestamp("data_hora_cadastro"));
                os.setDataHoraPrevistaInicio(rst.getTimestamp("data_hora_prevista_inicio"));
                os.setDataHoraPrevistaTermino(rst.getTimestamp("data_hora_prevista_termino"));
                os.setObs(rst.getString("obs"));
                os.setStatus(rst.getString("status").charAt(0));

                ServicoDAO servicoDAO = new ServicoDAO();
                os.setServico(servicoDAO.Retrieve(rst.getInt("servico_id")));

                Check check = new Check();
                check.setId(rst.getInt("check_id"));
                os.setCheck(check);

                Quarto quarto = new Quarto();
                quarto.setId(rst.getInt("quarto_id"));
                os.setQuarto(quarto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return os;
        }
    }

    @Override
    public List<OrdemServico> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "SELECT * FROM oderm_servico WHERE " + atributo + " LIKE ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<OrdemServico> lista = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                 OrdemServico os = new OrdemServico();
                 os.setId(rst.getInt("id"));
                 os.setDataHoraCadastro(rst.getTimestamp("data_hora_cadastro"));
                 os.setDataHoraPrevistaInicio(rst.getTimestamp("data_hora_prevista_inicio"));
                 os.setDataHoraPrevistaTermino(rst.getTimestamp("data_hora_prevista_termino"));
                 os.setObs(rst.getString("obs"));
                 os.setStatus(rst.getString("status").charAt(0));

                 ServicoDAO servicoDAO = new ServicoDAO();
                 os.setServico(servicoDAO.Retrieve(rst.getInt("servico_id")));

                 Check check = new Check();
                 check.setId(rst.getInt("check_id"));
                 os.setCheck(check);

                 Quarto quarto = new Quarto();
                 quarto.setId(rst.getInt("quarto_id"));
                 os.setQuarto(quarto);

                 lista.add(os);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return lista;
        }
    }

    @Override
    public void Update(OrdemServico objeto) {
        String sqlInstrucao = "UPDATE oderm_servico SET data_hora_cadastro = ?, data_hora_prevista_inicio = ?, "
                + "data_hora_prevista_termino = ?, obs = ?, status = ?, check_id = ?, servico_id = ?, quarto_id = ? WHERE id = ?";
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setTimestamp(1, new java.sql.Timestamp(objeto.getDataHoraCadastro().getTime()));
            pstm.setTimestamp(2, new java.sql.Timestamp(objeto.getDataHoraPrevistaInicio().getTime()));
            pstm.setTimestamp(3, new java.sql.Timestamp(objeto.getDataHoraPrevistaTermino().getTime()));
            pstm.setString(4, objeto.getObs());
            pstm.setString(5, String.valueOf(objeto.getStatus()));
            pstm.setInt(6, objeto.getCheck().getId());
            pstm.setInt(7, objeto.getServico().getId());
            pstm.setInt(8, objeto.getQuarto().getId());
            pstm.setInt(9, objeto.getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(OrdemServico objeto) {
        String sqlInstrucao = "UPDATE oderm_servico SET status = 'I' WHERE id = ?";
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