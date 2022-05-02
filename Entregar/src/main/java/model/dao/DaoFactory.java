package model.dao;

public class DaoFactory {
    
    public static ContatoDaoJDBC novoContatoDao() throws Exception {
        return new ContatoDaoJDBC();        
    }
    
}
