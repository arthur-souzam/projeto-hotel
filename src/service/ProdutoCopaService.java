package service;

import java.util.List;
import model.DAO.ProdutoCopaDAO;
import model.bo.ProdutoCopa;

public class ProdutoCopaService {

    public static void Criar(ProdutoCopa objeto) {
        ProdutoCopaDAO produtoCopaDAO = new ProdutoCopaDAO();
        produtoCopaDAO.Create(objeto);
    }
    
    public static List<ProdutoCopa> Carregar() {
        ProdutoCopaDAO produtoCopaDAO = new ProdutoCopaDAO();
        return produtoCopaDAO.Retrieve();
    }

    public static ProdutoCopa Carregar(int id) {
        ProdutoCopaDAO produtoCopaDAO = new ProdutoCopaDAO();
        return produtoCopaDAO.Retrieve(id);
    }


    public static List<ProdutoCopa> Carregar(String atributo, String valor) {
        ProdutoCopaDAO produtoCopaDAO = new ProdutoCopaDAO();
        return produtoCopaDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(ProdutoCopa objeto) {
        ProdutoCopaDAO produtoCopaDAO = new ProdutoCopaDAO();
        produtoCopaDAO.Update(objeto);
    }

    public static void Apagar(ProdutoCopa objeto) {
        ProdutoCopaDAO produtoCopaDAO = new ProdutoCopaDAO();
        produtoCopaDAO.Delete(objeto);
    }
}