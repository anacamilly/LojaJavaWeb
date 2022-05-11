package web.ufrn.demo.controllers;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import web.ufrn.demo.model.Carrinho;
import web.ufrn.demo.model.Produtos;
import web.ufrn.demo.repository.ProdutoDAO;

@Controller
public class CarrinhoController {

    @RequestMapping(value="/adicionar-carrinho", method= RequestMethod.GET)
    public void doAdicionarCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var codProduto = request.getParameter("codigo");
        var produtoDao = new ProdutoDAO();
        var produtos = produtoDao.listarProdutosPorCodigo(Integer.parseInt(codProduto));

        Cookie carrinho = new Cookie("carrinho", "");
        carrinho.setMaxAge(60*60*24*7);

        Cookie[] requestCookies = request.getCookies();
        boolean achouCarrinho = false;

        if (requestCookies != null){
            for (var c:requestCookies){
                if (c.getName().equals("carrinho")){
                    achouCarrinho = true;
                    carrinho = c;
                    break;
                }
            }
        }

        Produtos produtoEscolhido = null;

        if (!produtos.isEmpty()) {
            produtoEscolhido = produtos.get(0);
            if (achouCarrinho) {
                String value = carrinho.getValue();
                carrinho.setValue(value + produtoEscolhido.getCodigo() + "|");
            } else {
                carrinho.setValue(produtoEscolhido.getCodigo() + "|");
            }
        }else {
            response.addCookie(carrinho);
            response.getWriter().println("Cod inexistente");
        }
        response.addCookie(carrinho);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/lista-de-produtos");
        dispatcher.forward(request, response);
    }


    @GetMapping("/visualizar-carrinho")
    public void doVisualizarCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String cliente = (String) request.getSession().getAttribute("cliente");

        if(cliente != null){
            Cookie carrinho = new Cookie("carrinho", "");
            carrinho.setMaxAge(60*60*24*7);

            Cookie[] requestCookies = request.getCookies();
            boolean achouCarrinho = false;

            if (requestCookies != null){
                for (var c:requestCookies){
                    if (c.getName().equals("carrinho")){
                        achouCarrinho = true;
                        carrinho = c;
                        break;
                    }
                }
            }

            if (achouCarrinho){
                var writer = response.getWriter();
                writer.println("<html><body>");
                writer.println("<h1>Carrinho de Compras</h1>");
                writer.println("<table border=1>");
                var produtoDao = new ProdutoDAO();
                StringTokenizer tokenizer = new StringTokenizer(carrinho.getValue(), "|");
                while (tokenizer.hasMoreTokens() ){
                    List<Produtos> produtos = produtoDao.listarProdutosPorCodigo(Integer.parseInt(tokenizer.nextToken()));
                    if (produtos != null){
                        Produtos p = produtos.get(0);
                        writer.println("<tr>");
                        writer.println("<td>");
                        writer.println(p.getNome());
                        writer.println("</td>");
                        writer.println("<td>");
                        writer.println(p.getDescricao());
                        writer.println("</td>");
                        writer.println("<td>");
                        writer.println(p.getPreco());
                        writer.println("</td>");
                        writer.println("<td>");
                        writer.println("<a href='/remove-carrinho?codigo="+p.getCodigo()+"'>Remover</a>");
                        writer.println("</td>");
                        writer.println("</tr>");
                    }

                }

                writer.println("</table>");
                writer.println("<a href='/finalizar-compra'> Finalizar compra </a>");
                writer.println("</body></html>");
            }else{
                response.getWriter().println("Carrinho vazio");
            }
        }else{
            response.sendRedirect("/clientes/loginClientes.html");
        }
    }

    
    @GetMapping(value="/finalizar-compra")
    public void doRemoverCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var writer = response.getWriter();
        Cookie[] requestCookies = request.getCookies();

        if (requestCookies != null){
            for (Cookie cookie : requestCookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }

        writer.println("<h1>Compra Finalizada</h1>");
        writer.println("<a href='/inicio-clientes'>Pagina Inicial</a>");
        }
    }


    @RequestMapping(value="/remove-carrinho", method= RequestMethod.GET)
    public void doRemoveCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var writer = response.getWriter();

        var token = "";
        var codigoProduto = request.getParameter("codigo");
        boolean achouCarrinho = false;

        Cookie[] requestCookies = request.getCookies();
        Cookie carrinho = new Cookie("carrinho", "");

        if (requestCookies != null) {
            for (var produto : requestCookies) {
                if (produto.getName().equals("carrinho")) {
                    achouCarrinho = true;
                    carrinho = produto;
                    break;
                }
            }
        }

        if (achouCarrinho) {
            StringTokenizer tokenizer = new StringTokenizer(carrinho.getValue(), "|");
            var cod = "";


            while (tokenizer.hasMoreTokens()) {

                cod = tokenizer.nextToken();

                if (!cod.equals(codigoProduto)) {

                    token += cod + "|";
                }
            }
            carrinho.setValue(token);

            response.addCookie(carrinho);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/visualizar-carrinho");
            dispatcher.forward(request, response);

        } else {
            writer.println("<p> produto não encontrado!</p>");
        }
    }
}