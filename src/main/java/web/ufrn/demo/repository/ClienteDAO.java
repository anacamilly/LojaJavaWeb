package web.ufrn.demo.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import web.ufrn.demo.model.Clientes;

public class ClienteDAO {
    private final static String VERIFICACAO = "select * from tb_clientes where email=?";
    private final static String CADASTRO = "insert into tb_clientes ( nome, email, senha) values (?,?,?)"; 
    private final static String LOGIN = "select * from tb_clientes where email=? and senha=?";
    
    
    ////////////////////////// CADASTRO //////////////////////////////
    public static void cadastrar(Clientes cliente){
        try{
            Conexao.getConnection();
            PreparedStatement instrucao = Conexao.getConnection().prepareStatement(CADASTRO);
            instrucao.setString(1, cliente.getNome());
            instrucao.setString(2, cliente.getEmail());
            instrucao.setString(3, cliente.getSenha());
  
            instrucao.execute();
        } catch (Exception e){
            System.out.println("Erro no cadastro: " + e.getMessage());
        }
    }


    ////////////////////////////// VERIFICAÇÃO - CADASTRO //////////////////////////////
    public static Clientes verificacao(String email){
        Clientes cliente = null;
        try{
            Conexao.getConnection();
            PreparedStatement instrucao = Conexao.getConnection().prepareStatement(VERIFICACAO);
            instrucao.setString(1, email);
  
            ResultSet rs = instrucao.executeQuery();

            if(rs.next()){
                cliente = new Clientes(rs.getString("Email"));
            }

            instrucao.close();

        } catch (Exception e){
            System.out.println("Erro na verificação: " + e.getMessage());
        }

        return cliente;
    }


    ////////////////////////////// VERIFICAÇÃO - LOGIN //////////////////////////////
    public static Clientes login(String email, String senha){
        Clientes cliente = null;
        
        try{
            Conexao.getConnection();
            PreparedStatement instrucao = Conexao.getConnection().prepareStatement(LOGIN);
            instrucao.setString(1, email);
            instrucao.setString(2, senha);
  
            ResultSet rs = instrucao.executeQuery();

            if(rs.next()){
                cliente = new Clientes(rs.getString("Email"), rs.getString("senha"));
            
            }
            instrucao.close();

        } catch (Exception e){
            System.out.println("Erro na verificação: " + e.getMessage());
        }

        return cliente;
    }

}
