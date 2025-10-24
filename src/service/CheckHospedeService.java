package service;

import java.util.List;
import model.DAO.CheckHospedeDAO;
import model.bo.CheckHospede;

public class CheckHospedeService {
    public static void Criar(CheckHospede obj) { new CheckHospedeDAO().Create(obj); }
    public static List<CheckHospede> Carregar() { return new CheckHospedeDAO().Retrieve(); }
    public static CheckHospede Carregar(int id) { return new CheckHospedeDAO().Retrieve(id); }
    public static List<CheckHospede> Carregar(String atr, String val) { return new CheckHospedeDAO().Retrieve(atr, val); }
    public static void Atualizar(CheckHospede obj) { new CheckHospedeDAO().Update(obj); }
    public static void Apagar(CheckHospede obj) { new CheckHospedeDAO().Delete(obj); }
}