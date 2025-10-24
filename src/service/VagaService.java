package service;

import java.util.List;
import model.DAO.VagaDAO;
import model.DAO.VagaDAO;
import model.bo.Vaga;

public class VagaService {

    public static void Criar(Vaga objeto) {
        VagaDAO vagaDAO = new VagaDAO();
        vagaDAO.Create(objeto);
    }
    
    public static List<Vaga> Carregar() {
        VagaDAO vagaDAO = new VagaDAO();
        return vagaDAO.Retrieve();
    }
     
    public static Vaga Carregar(int id) {
        VagaDAO vagaDAO = new VagaDAO();
        return vagaDAO.Retrieve(id);
    }

    public static List<Vaga> Carregar(String atributo, String valor) {
        VagaDAO vagaDAO = new VagaDAO();
        return vagaDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(Vaga objeto) {
        VagaDAO vagaDAO = new VagaDAO();
        vagaDAO.Update(objeto);
    }

    public static void Apagar(Vaga objeto) {
        VagaDAO vagaDAO = new VagaDAO();
        vagaDAO.Delete(objeto);
    }
}