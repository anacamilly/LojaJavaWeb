package web.ufrn.demo.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import web.ufrn.demo.model.Lojistas;
import web.ufrn.demo.repository.LojistasDAO;

@Controller
public class LojistasController {
    
    @RequestMapping(value = "/login-lojistas", method = RequestMethod.POST)
    public void LoginClientes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var email = request.getParameter("email");
        var senha = request.getParameter("senha");

        var writer = response.getWriter();
        
        Lojistas lojista = new  Lojistas();
        
        lojista.setEmail(email);
        lojista.setSenha(senha);

        if( LojistasDAO.login(lojista.getEmail(), lojista.getSenha())!=null){
            response.sendRedirect("/inicio-lojistas");           
        }else{
            writer.println("ERROR: lojista não cadastrado");
        }
    }

    
    @RequestMapping(value = "/inicio-lojistas")
    private void pagInicialClientes(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        response.sendRedirect("lojistas/pagInicialLojistas.html");
    }
    
}
