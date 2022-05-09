package web.ufrn.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

import web.ufrn.demo.model.Clientes;
import web.ufrn.demo.repository.ClienteDAO;
import web.ufrn.demo.repository.Conexao;

@Controller
public class ClientesController {

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/cadastro-clientes", method = RequestMethod.POST)
    public void CadastroClientes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        var writer = response.getWriter();

        Clientes cliente = new Clientes();
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setSenha(senha);

        if(ClienteDAO.verificacao(cliente.getEmail())==null){
            ClienteDAO.cadastrar(cliente);

            RequestDispatcher encaminhar = request.getRequestDispatcher("/login-clientes");
            encaminhar.forward(request, response);
        }else{
            writer.println("ERROR: Email já cadastrado");
        }
    }


    @RequestMapping(value = "/login-clientes", method = RequestMethod.POST)
    public void LoginClientes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
         
        var email = request.getParameter("email");
        var senha = request.getParameter("senha");

        var writer = response.getWriter();
        
        Clientes cliente = new Clientes();
        
        cliente.setEmail(email);
        cliente.setSenha(senha);


        if(ClienteDAO.login(cliente.getEmail(), cliente.getSenha())!=null){    
            HttpSession session = request.getSession();

            if(session != null){
                String id = session.getId();
                //writer.println("id: " + id);

                request.getSession().setAttribute("cliente", email);
                request.getSession().setAttribute("idSession", id);

                response.sendRedirect("/inicio-clientes");                
            }
        }else{
            writer.println("ERROR: Email não cadastrado");
        }
    }

    
    @RequestMapping(value = "/inicio-clientes")
    private void pagInicialClientes(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var writer = response.getWriter();

        String cliente = (String) request.getSession().getAttribute("cliente");
        String idSession = (String) request.getSession().getAttribute("idSession");
 
        if(cliente != null){
            writer.println("<html>");
            writer.println("<body>");
            writer.println("<h1> Página Inicial - Clientes </h1>");
            writer.println("<h3>Bem-vindo: " + cliente + "</h3>");
            writer.println("<a href='/lista-de-produtos'> Produtos </a><br>");
            writer.println("<a href='/visualizar-carrinho'> Carrinho de Compras </a><br>");
            writer.println("<hr>");
            writer.println(" <button action='/logout' type='submit'>logout</button>");
            writer.println("<p>Sua sessão é: " + idSession + "</p>");
            writer.println("</html></body>");
        }else{
            response.sendRedirect("/login-clientes");
        }

    }

        
    @GetMapping("/logout")
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.getSession().invalidate();
        response.sendRedirect("/index.html");
    }

    @RequestMapping("/conexao")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{

		String name = request.getParameter("nome");
		response.getWriter().append("Hello, ").append(name).append(".");

		Connection connection = null;
		try {
			connection = Conexao.getConnection();
		} catch (SQLException | URISyntaxException ex) {
			response.getWriter().append("Connection Failed! Check output console");
		}

		if (connection != null) {
			response.getWriter().append("A conexão com o banco foi realizada!");
		} else {
			response.getWriter().append("A conexão com o banco falhou!");
		}

		try {
			assert connection != null;
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
