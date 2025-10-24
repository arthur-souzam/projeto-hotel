package service;

import java.util.List;
import model.DAO.CheckQuartoDAO;
import model.bo.CheckQuarto;

public class CheckQuartoService {
    public static void Criar(CheckQuarto obj) { new CheckQuartoDAO().Create(obj); }
    public static List<CheckQuarto> Carregar() { return new CheckQuartoDAO().Retrieve(); }
    public static CheckQuarto Carregar(int id) { return new CheckQuartoDAO().Retrieve(id); }
    public static List<CheckQuarto> Carregar(String atr, String val) { return new CheckQuartoDAO().Retrieve(atr, val); }
    public static void Atualizar(CheckQuarto obj) { new CheckQuartoDAO().Update(obj); }
    public static void Apagar(CheckQuarto obj) { new CheckQuartoDAO().Delete(obj); }
}