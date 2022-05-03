package web.ufrn.demo.DAOS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import web.ufrn.demo.entidades.Produtos;

public class ProdutoDAO {
    private final static String CADASTRO = "insert into tb_produtos (codigo, nome, descricao, preco, quantidade) values (?,?,?,?,?)";
    private final static String VERIFICACAO = "select * from tb_produtos where codigo=?";
    private final static String LISTAR =  "SELECT * FROM tb_produtos";

    ////////////////////////////// CADASTRO //////////////////////////////
    public static void cadastrar(Produtos produtos){
        try{
            Conexao.getConnection();
            PreparedStatement instrucao = Conexao.getConnection().prepareStatement(CADASTRO);
            instrucao.setInt(1, produtos.getCodigo());
            instrucao.setString(2, produtos.getNome());
            instrucao.setString(3, produtos.getDescricao());
            instrucao.setFloat(4, produtos.getPreco());
            instrucao.setInt(5, produtos.getQuantidade());
  
            instrucao.execute();
        } catch (Exception e){
            System.out.println("Erro no cadastro: " + e.getMessage());
        }
    }

    ////////////////////////////// VERIFICAÇÃO - CADASTRO //////////////////////////////
    public static Produtos verificacao(Integer codigo){
        Produtos produtos = null;
        try{
            Conexao.getConnection();
            PreparedStatement instrucao = Conexao.getConnection().prepareStatement(VERIFICACAO);
            instrucao.setInt(1, codigo);
  
            ResultSet rs = instrucao.executeQuery();

            if(rs.next()){
                produtos = new Produtos(rs.getInt("codigo"));
            }

            instrucao.close();

        } catch (Exception e){
            System.out.println("Erro na verificação: " + e.getMessage());
        }

        return produtos;
    }

    ////////////////////////////// LISTAR //////////////////////////////
    public static List<Produtos> listar(){
	
        List <Produtos> produto = new ArrayList<Produtos>();
        Connection connection = null;
        PreparedStatement prep = null;
        ResultSet lista = null;
        
        try {
            connection = Conexao.getConnection();
    
            prep = connection.prepareStatement(LISTAR);
            
            lista= prep.executeQuery();
            
            
            while(lista.next()) {
                Produtos produtos = new Produtos();
                produtos.setCodigo(lista.getInt("codigo"));
                produtos.setNome(lista.getString("nome"));
                produtos.setDescricao( lista.getString("descricao"));
                produtos.setPreco(lista.getFloat("preco"));
                produtos.setQuantidade(lista.getInt("quantidade"));
                produto.add(produtos);
            }
            
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }finally {
            //////fecha conexões
            try {
                if(lista!=null) {
                    prep.close();
                    connection.close();
                }if (prep!=null) {
                    prep.close();
                }if(connection!=null) {
                    connection.close();
                }
            } catch (Exception e2) {
             System.out.println("erro: " + e2.getMessage());
            }
        
        }
        return produto;
        }
    
}
