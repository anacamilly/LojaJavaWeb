package web.ufrn.demo.DAOS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import web.ufrn.demo.entidades.Produtos;

public class ProdutoDAO {
    private final static String CADASTRO = "insert into tb_produtos (codigo, nome, descricao, preco, quantidade) values (?,?,?,?)";
    private final static String VERIFICACAO = "select * from tb_produtos where codigo=?";

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
}
