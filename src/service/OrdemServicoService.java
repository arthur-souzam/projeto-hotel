package service;

import java.util.List;
import model.DAO.OrdemServicoDAO;
import model.bo.OrdemServico;

public class OrdemServicoService {

    public static void Criar(OrdemServico objeto) {
        OrdemServicoDAO osDAO = new OrdemServicoDAO();
        osDAO.Create(objeto);
    }

    public static OrdemServico Carregar(int id) {
        OrdemServicoDAO osDAO = new OrdemServicoDAO();
        return osDAO.Retrieve(id);
    }

    public static List<OrdemServico> Carregar(String atributo, String valor) {
        OrdemServicoDAO osDAO = new OrdemServicoDAO();
        return osDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(OrdemServico objeto) {
        OrdemServicoDAO osDAO = new OrdemServicoDAO();
        osDAO.Update(objeto);
    }

    public static void Apagar(OrdemServico objeto) {
        OrdemServicoDAO osDAO = new OrdemServicoDAO();
        osDAO.Delete(objeto);
    }
}