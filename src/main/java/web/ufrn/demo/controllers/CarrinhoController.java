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
                    writer.println("<a href='/removerCarrinho?id="+p.getCodigo()+"'>Remover</a>");
                    writer.println("</td>");
                    writer.println("</tr>");
                }
            }
            writer.println("</table>");
            writer.println("</body></html>");
        }else{
            response.getWriter().println("Carrinho vazio");
        }
    }
}
