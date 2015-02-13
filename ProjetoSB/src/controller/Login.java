package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nucleo.model.negocios.Usuario;
import fachada.Facade;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login.jsp")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Facade facade = new Facade();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("login");
		String senha = request.getParameter("pass");
		
		try {
			facade.doLogin(login, senha);
			
			Usuario u = new Usuario();
			u.setLogin(login);
			u.setSenha(senha);
			u.setNome(facade.getProfileInformation(login, "nome_exibicao"));
			
			request.getSession().setAttribute("usuario_logado", u);
			response.sendRedirect(request.getContextPath() + "/home"); // pensar melhor
		} catch (Exception e) {
			e.printStackTrace();
			// usar dispatcher com include colocando o erro na resposta
		}
		
	}

}
