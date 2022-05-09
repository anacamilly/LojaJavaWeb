package web.ufrn.demo.repository;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import web.ufrn.demo.model.Produtos;

public class ProdutoDAO {
    private final static String CADASTRO = "insert into tb_produtos (codigo, nome, descricao, preco, quantidade) values (?,?,?,?,?)";
    private final static String VERIFICACAO = "select * from tb_produtos where codigo=?";
    private final static String LISTAR =  "SELECT * FROM tb_produtos";
    private final static String LISTAR_POR_CODIGO = "select * from tb_produtos where codigo=?";

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
    public List<Produtos> listarProdutos() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Produtos> listaDeProdutos = new ArrayList<>();
        try {
            connection = Conexao.getConnection();
            stmt = connection.prepareStatement(LISTAR);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Produtos p = new Produtos();
                p.setCodigo(rs.getInt("codigo"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getFloat("preco"));
                p.setQuantidade(rs.getInt("quantidade"));

                listaDeProdutos.add(p);
            }
            connection.close();
        } catch (SQLException | URISyntaxException ignored) {
        }

        return listaDeProdutos;
    }

    public List<Produtos> listarProdutosPorCodigo(int codigo) {

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Produtos> listaDeProdutos = new ArrayList<>();

        try {
            connection = Conexao.getConnection();
            stmt = connection.prepareStatement(LISTAR_POR_CODIGO);
            stmt.setInt(1, codigo);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Produtos p = new Produtos();
                p.setCodigo(rs.getInt("codigo"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getFloat("preco"));
                p.setQuantidade(rs.getInt("quantidade"));
                listaDeProdutos.add(p);
            }
            connection.close();
        } catch (SQLException | URISyntaxException ignored) {
        }
        return listaDeProdutos;
    }

    
}
