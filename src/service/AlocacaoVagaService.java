package service;

import java.util.List;
import model.DAO.AlocacaoVagaDAO;
import model.bo.AlocacaoVaga;

public class AlocacaoVagaService {
    public static void Criar(AlocacaoVaga obj) { new AlocacaoVagaDAO().Create(obj); }
    public static List<AlocacaoVaga> Carregar() { return new AlocacaoVagaDAO().Retrieve(); }
    public static AlocacaoVaga Carregar(int id) { return new AlocacaoVagaDAO().Retrieve(id); }
    public static List<AlocacaoVaga> Carregar(String atr, String val) { return new AlocacaoVagaDAO().Retrieve(atr, val); }
    public static void Atualizar(AlocacaoVaga obj) { new AlocacaoVagaDAO().Update(obj); }
    public static void Apagar(AlocacaoVaga obj) { new AlocacaoVagaDAO().Delete(obj); }
}