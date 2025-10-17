package service;

import java.util.List;
import model.DAO.MovimentoCaixaDAO;
import model.bo.MovimentoCaixa;

public class MovimentoCaixaService {

    public static void Criar(MovimentoCaixa objeto) {
        MovimentoCaixaDAO movimentoCaixaDAO = new MovimentoCaixaDAO();
        movimentoCaixaDAO.Create(objeto);
    }

    public static MovimentoCaixa Carregar(int id) {
        MovimentoCaixaDAO movimentoCaixaDAO = new MovimentoCaixaDAO();
        return movimentoCaixaDAO.Retrieve(id);
    }


    public static List<MovimentoCaixa> Carregar(String atributo, String valor) {
        MovimentoCaixaDAO movimentoCaixaDAO = new MovimentoCaixaDAO();
        return movimentoCaixaDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(MovimentoCaixa objeto) {
        MovimentoCaixaDAO movimentoCaixaDAO = new MovimentoCaixaDAO();
        movimentoCaixaDAO.Update(objeto);
    }

    public static void Apagar(MovimentoCaixa objeto) {
        MovimentoCaixaDAO movimentoCaixaDAO = new MovimentoCaixaDAO();
        movimentoCaixaDAO.Delete(objeto);
    }
}