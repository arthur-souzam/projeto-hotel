package service;

import java.util.List;
import model.DAO.CaixaDAO;
import model.bo.Caixa;

public class CaixaService {

    public static void Criar(Caixa objeto) {
        CaixaDAO caixaDAO = new CaixaDAO();
        caixaDAO.Create(objeto);
    }

    public static Caixa Carregar(int id) {
        CaixaDAO caixaDAO = new CaixaDAO();
        return caixaDAO.Retrieve(id);
    }

   

    public static List<Caixa> Carregar(String atributo, String valor) {
        CaixaDAO caixaDAO = new CaixaDAO();
        return caixaDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(Caixa objeto) {
        CaixaDAO caixaDAO = new CaixaDAO();
        caixaDAO.Update(objeto);
    }

    public static void Apagar(Caixa objeto) {
        CaixaDAO caixaDAO = new CaixaDAO();
        caixaDAO.Delete(objeto);
    }
}