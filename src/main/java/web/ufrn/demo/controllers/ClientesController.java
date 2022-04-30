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
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

import web.ufrn.demo.DAOS.ClienteDAO;
import web.ufrn.demo.DAOS.Conexao;
import web.ufrn.demo.entidades.Clientes;

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

            RequestDispatcher encaminhar = request.getRequestDispatcher("/inicio-clientes");
            encaminhar.forward(request, response);           
        }else{
            writer.println("ERROR: usuario não cadastrado");
        }
    }

    @GetMapping("/inicio-clientes")
    public String pagInicialClientes(){
        return "pagInicialClientes";
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