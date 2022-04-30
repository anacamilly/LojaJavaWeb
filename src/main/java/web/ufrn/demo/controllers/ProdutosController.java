package web.ufrn.demo.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import web.ufrn.demo.DAOS.ProdutoDAO;
import web.ufrn.demo.entidades.Produtos;

@Controller
public class ProdutosController {
    
    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/cadastro-produtos", method = RequestMethod.POST)
    public void CadastroClientes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var codigo = request.getParameter("codigo");
        var nome = request.getParameter("nome");
        var descricao = request.getParameter("descricao");
        var preco = request.getParameter("preco");
        var quantidade = request.getParameter("quantidade");


        var writer = response.getWriter();

        Produtos produtos = new Produtos();
        produtos.setCodigo(Integer.parseInt(codigo));
        produtos.setNome(nome);
        produtos.setDescricao(descricao);
        produtos.setPreco(Float.parseFloat(preco));
        produtos.setQuantidade(Integer.parseInt(quantidade));


        if(ProdutoDAO.verificacao(produtos.getCodigo())==null){
            ProdutoDAO.cadastrar(produtos);

            RequestDispatcher encaminhar = request.getRequestDispatcher("/login-clientes");
            encaminhar.forward(request, response);
            
        }else{
            writer.println("ERROR: Codigo já cadastrado");
        }
    }
    
}
