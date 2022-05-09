package web.ufrn.demo.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import web.ufrn.demo.model.Lojistas;
import web.ufrn.demo.repository.LojistasDAO;

@Controller
public class LojistasController {

    @RequestMapping(value = "/lojistas", method = RequestMethod.GET)
    private void lojistas(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        var writer = response.getWriter();

        writer.println("<html>");
        writer.println("<body>");
        writer.println("<h1> Lojistas </h1>");
        writer.println("<a href='/lojistas/loginLojistas.html'> Login </a><br/>");
        writer.println("</html></body>");

    }
    
    @RequestMapping(value = "/login-lojistas", method = RequestMethod.POST)
    public void LoginClientes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var email = request.getParameter("email");
        var senha = request.getParameter("senha");

        var writer = response.getWriter();
        
        Lojistas lojista = new  Lojistas();
        
        lojista.setEmail(email);
        lojista.setSenha(senha);

        if( LojistasDAO.login(lojista.getEmail(), lojista.getSenha())!=null){
            HttpSession session = request.getSession();

            if(session != null){
                String id = session.getId();
                //writer.println("id: " + id);

                request.getSession().setAttribute("lojistas", email);
                request.getSession().setAttribute("idSession", id);

                response.sendRedirect("/inicio-lojistas");               
            }
                       
        }else{
            writer.println("ERROR: lojista não cadastrado");
        }
    }

    
    @RequestMapping(value = "/inicio-lojistas", method = RequestMethod.GET)
    private void pagInicialClientes(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var writer = response.getWriter();

        String lojistas = (String) request.getSession().getAttribute("lojistas");
        String idSession = (String) request.getSession().getAttribute("idSession");
 
        if(lojistas != null){
            writer.println("<html>");
            writer.println("<body>");
            writer.println("<h1> Página Inicial - Lojistas </h1>");
            writer.println("<h3>Bem-vindo: " + lojistas + "</h3>");
            writer.println("<a href='/cadastro-produtos'> Cadastro de Produtos </a><br/>");
            writer.println("<a href='/lista-de-produtos'> Produtos </a>");
            writer.println("<hr>");
            writer.println("<form action='/logout' method='get'>");
            writer.println(" <button type='submit'>logout</button>");
            writer.println("</form>");
            writer.println("<p>Sua sessão é: " + idSession + "</p>");
            writer.println("</html></body>");
        }else{
            response.sendRedirect("/lojistas/loginLojistas.html");
        }
    }
    
}
