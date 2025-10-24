package service;

import java.util.List;
import model.DAO.ReservaQuartoDAO;
import model.bo.ReservaQuarto;

public class ReservaQuartoService {
    public static void Criar(ReservaQuarto obj) { new ReservaQuartoDAO().Create(obj); }
    public static List<ReservaQuarto> Carregar() { return new ReservaQuartoDAO().Retrieve(); }
    public static ReservaQuarto Carregar(int id) { return new ReservaQuartoDAO().Retrieve(id); }
    public static List<ReservaQuarto> Carregar(String atr, String val) { return new ReservaQuartoDAO().Retrieve(atr, val); }
    public static void Atualizar(ReservaQuarto obj) { new ReservaQuartoDAO().Update(obj); }
    public static void Apagar(ReservaQuarto obj) { new ReservaQuartoDAO().Delete(obj); }
}