package service;

import java.util.List;
import model.DAO.ReceberDAO;
import model.bo.Receber;

public class ReceberService {

    public static void Criar(Receber objeto) {
        ReceberDAO receberDAO = new ReceberDAO();
        receberDAO.Create(objeto);
    }

    public static Receber Carregar(int id) {
        ReceberDAO receberDAO = new ReceberDAO();
        return receberDAO.Retrieve(id);
    }



    public static List<Receber> Carregar(String atributo, String valor) {
        ReceberDAO receberDAO = new ReceberDAO();
        return receberDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(Receber objeto) {
        ReceberDAO receberDAO = new ReceberDAO();
        receberDAO.Update(objeto);
    }

    public static void Apagar(Receber objeto) {
        ReceberDAO receberDAO = new ReceberDAO();
        receberDAO.Delete(objeto);
    }
}