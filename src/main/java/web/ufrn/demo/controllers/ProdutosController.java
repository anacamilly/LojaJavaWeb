package web.ufrn.demo.controllers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import web.ufrn.demo.model.Produtos;
import web.ufrn.demo.repository.ProdutoDAO;

@Controller
public class ProdutosController {
    
    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/cadastro-produtos", method = RequestMethod.POST)
    public void CadastroProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        String lojistas = (String) request.getSession().getAttribute("lojistas");

        if(lojistas != null){
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
                writer.println("Produto cadastrado com sucesso");
            }else{
                writer.println("ERROR: Codigo já cadastrado");
            }

        }else{
            response.sendRedirect("/lojistas/loginLojistas.html");
        }
    }
 
    @RequestMapping(value = "/lista-de-produtos", method = RequestMethod.GET)
    public void doListAll(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String cliente = (String) request.getSession().getAttribute("cliente");

        if(cliente != null){
            var writer = response.getWriter();
            writer.println("<html><body>");
            writer.println("<h1>Lista de Produtos</h1>");
            writer.println("<table border=1>");
            var produtoDao = new ProdutoDAO();
            var listaProdutos = produtoDao.listarProdutos();
            for (var p:listaProdutos ){
                writer.println("<tr>");
                writer.println("<td>");
                writer.println("<p>Nome:</p>");
                writer.println(p.getNome());
                writer.println("</td>");
                writer.println("<td>");
                writer.println("<p>Descrição:</p>");
                writer.println(p.getDescricao());
                writer.println("</td>");
                writer.println("<td>");
                writer.println("<p>Preço:</p>");
                writer.println(p.getPreco());
                writer.println("</td>");
                writer.println("<td>");
                writer.println("<p>Quantidade:</p>");
                writer.println(p.getQuantidade());
                writer.println("</td>");
                writer.println("<td>");
                writer.println("<a href='/adicionar-carrinho?codigo="+p.getCodigo()+"'>Adicionar</a>");
                writer.println("</td>");
                writer.println("</tr>");
            }

            writer.println("</table>");
            writer.println("<br/>");
            writer.println("<a href='/visualizar-carrinho'>Visualizar Carrinho</a>");
            writer.println("</body></html>");

        }else{
            response.sendRedirect("/clientes/loginClientes.html");
        }
    }

    @RequestMapping(value = "/lista-de-produtos-lojistas", method = RequestMethod.GET)
    public void doListaDeProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String lojistas = (String) request.getSession().getAttribute("lojistas");

        if(lojistas != null){
            var writer = response.getWriter();
            writer.println("<html><body>");
            writer.println("<h1>Lista de Produtos</h1>");
            writer.println("<table border=1>");
            var produtoDao = new ProdutoDAO();
            var listaProdutos = produtoDao.listarProdutos();
            for (var p:listaProdutos ){
                writer.println("<tr>");
                writer.println("<td>");
                writer.println("<p>Nome:</p>");
                writer.println(p.getNome());
                writer.println("</td>");
                writer.println("<td>");
                writer.println("<p>Descrição:</p>");
                writer.println(p.getDescricao());
                writer.println("</td>");
                writer.println("<td>");
                writer.println("<p>Preço:</p>");
                writer.println(p.getPreco());
                writer.println("</td>");
                writer.println("<td>");
                writer.println("<p>Quantidade:</p>");
                writer.println(p.getQuantidade());
                writer.println("</td>");
                writer.println("</tr>");
        }
        
        writer.println("</table>");
        writer.println("</body></html>");

        }else{
            response.sendRedirect("/lojistas/loginLojistas.html");
        }

    }
}