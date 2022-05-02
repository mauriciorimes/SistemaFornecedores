package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Contato;

public class ContatoDaoJDBC implements InterfaceDao<Contato> {

    private Connection conn;

    public ContatoDaoJDBC() throws Exception {
        this.conn = ConnFactory.getConnection();
    }

    public void incluir(Contato entidade) throws Exception {

        PreparedStatement ps = conn.prepareStatement("INSERT INTO Contato (nome, email, telefone, foto, status) VALUES(?, ?, ?, ?, ?)");

        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getEmail());
        ps.setString(3, entidade.getTelefone());
        ps.setString(4, entidade.getFoto());
        ps.setString(5, entidade.getStatus());
        ps.execute();

    }

    public void editar(Contato entidade) throws Exception {

        PreparedStatement ps = conn.prepareStatement("UPDATE Contato SET  nome = ?, email = ?, telefone = ?, foto = ?, status = ? WHERE id = ?");

        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getEmail());
        ps.setString(3, entidade.getTelefone());
        ps.setString(4, entidade.getFoto());
        ps.setString(5, entidade.getStatus());
        ps.setInt(6, entidade.getId());

        ps.execute();

    }

    public void excluir(Contato entidade) throws Exception {
        
        PreparedStatement ps = conn.prepareStatement("DELETE FROM Contato WHERE  id = ?");

        ps.setInt(1, entidade.getId());
        ps.execute();
    }

    public Contato pesquisarPorId(int id) throws Exception {
        
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Contato WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Contato c = null;
        
        if (rs.next()) {
            c = new Contato();
            c.setId(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
            c.setEmail(rs.getString("email"));
            c.setTelefone(rs.getString("telefone")); 
            c.setFoto(rs.getString("foto"));
            c.setStatus(rs.getString("status"));
        }

        return c;
        
    }

    public List<Contato> listar(String param) throws Exception {
        PreparedStatement ps = null;
        if (param == "") {
            ps = conn.prepareStatement("SELECT * FROM Contato");            
        } else {
            ps = conn.prepareStatement("SELECT * FROM Contato WHERE nome like '%" + param + "%'");             
        }

        
        ResultSet rs = ps.executeQuery();
        List<Contato> lista = new ArrayList();
        while (rs.next()) {
            Contato c = new Contato();
            c.setId(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
            c.setEmail(rs.getString("email"));
            c.setTelefone(rs.getString("telefone"));
            c.setFoto(rs.getString("foto"));
            c.setStatus(rs.getString("status"));
            lista.add(c);
        }

        return lista;

    }

}
