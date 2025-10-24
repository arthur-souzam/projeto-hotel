package service;

import java.util.List;
import model.DAO.ReservaDAO;
import model.bo.Reserva;

public class ReservaService {
    public static void Criar(Reserva obj) { new ReservaDAO().Create(obj); }
    public static List<Reserva> Carregar() { return new ReservaDAO().Retrieve(); }
    public static Reserva Carregar(int id) { return new ReservaDAO().Retrieve(id); }
    public static List<Reserva> Carregar(String atr, String val) { return new ReservaDAO().Retrieve(atr, val); }
    public static void Atualizar(Reserva obj) { new ReservaDAO().Update(obj); }
    public static void Apagar(Reserva obj) { new ReservaDAO().Delete(obj); }
}