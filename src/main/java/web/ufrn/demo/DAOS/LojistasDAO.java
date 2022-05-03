package web.ufrn.demo.DAOS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


import web.ufrn.demo.entidades.Lojistas;

public class LojistasDAO {
    private final static String LOGIN = "select * from tb_lojistas where email=? and senha=?";
    
     ////////////////////////////// VERIFICAÇÃO - LOGIN //////////////////////////////
     public static Lojistas login(String email, String senha){
        Lojistas lojista = null;
        
        try{
            Conexao.getConnection();
            PreparedStatement instrucao = Conexao.getConnection().prepareStatement(LOGIN);
            instrucao.setString(1, email);
            instrucao.setString(2, senha);
  
            ResultSet rs = instrucao.executeQuery();

            if(rs.next()){
                lojista = new Lojistas(rs.getString("Email"), rs.getString("senha"));
            }
            instrucao.close();

        } catch (Exception e){
            System.out.println("Erro na verificação: " + e.getMessage());
        }

        return lojista;
    }
}
