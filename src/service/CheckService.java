package service;

import java.util.List;
import model.DAO.CheckDAO;
import model.bo.Check;

public class CheckService {
    public static void Criar(Check obj) { new CheckDAO().Create(obj); }
    public static List<Check> Carregar() { return new CheckDAO().Retrieve(); }
    public static Check Carregar(int id) { return new CheckDAO().Retrieve(id); }
    public static List<Check> Carregar(String atr, String val) { return new CheckDAO().Retrieve(atr, val); }
    public static void Atualizar(Check obj) { new CheckDAO().Update(obj); }
    public static void Apagar(Check obj) { new CheckDAO().Delete(obj); }
}