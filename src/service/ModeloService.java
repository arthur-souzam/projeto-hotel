package service;

import java.util.List;
import model.DAO.ModeloDAO;
import model.bo.Modelo;

public class ModeloService {

    public static void Criar(Modelo objeto) {
        ModeloDAO modeloDAO = new ModeloDAO();
        modeloDAO.Create(objeto);
    }
    
    
    public static List<Modelo> Carregar() {
        ModeloDAO modeloDAO = new ModeloDAO();
        return modeloDAO.Retrieve();
    }
    
    public static Modelo Carregar(int id) {
        ModeloDAO modeloDAO = new ModeloDAO();
        return modeloDAO.Retrieve(id);
    }

    public static List<Modelo> Carregar(String atributo, String valor) {
        ModeloDAO modeloDAO = new ModeloDAO();
        return modeloDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(Modelo objeto) {
        ModeloDAO modeloDAO = new ModeloDAO();
        modeloDAO.Update(objeto);
    }

    public static void Apagar(Modelo objeto) {
        ModeloDAO modeloDAO = new ModeloDAO();
        modeloDAO.Delete(objeto);
    }
}