package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nucleo.model.negocios.Blog;
import nucleo.model.negocios.Postagem;
import fachada.Facade;

/**
 * Servlet implementation class MostrarPost
 */
@WebServlet("/web/mostrar_post.jsp")
public class MostrarPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Facade facade = new Facade();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostrarPost() {
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
		
		Blog b = (Blog) request.getSession(true).getAttribute("blogMostrar");
		
		List<Integer> idsPosts = null;
		List<Postagem> posts = null;
		
		int index;
		
		System.out.println("inicio do mp");
		
		try {
			index = 0;
			idsPosts = new ArrayList<Integer>();

			while (true)
				idsPosts.add(facade.getPost(b.getCodigo(), index++));

		} catch (Exception e) {
			e.printStackTrace();
			
			posts = new ArrayList<Postagem>();
			
			for (Integer idPost : idsPosts) {
				
				String titulo = "";
				String texto = "";
				String data_criacao = "";
				
				try {
					
					titulo = facade.getPostInformation(idPost, "titulo");
					texto = facade.getPostInformation(idPost, "texto");
					data_criacao = facade.getPostInformation(idPost, "data_criacao");
					
					Postagem p = new Postagem();
					p.setBlog(b);
					p.setCodigo(idPost);
					p.setConteudo(texto);
					p.setTitulo(titulo);
					p.setData(Date.valueOf(data_criacao));
					
					posts.add(p);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			System.out.println("chegou ao mp");
//			request.getSession(true).setAttribute("blog", b); n precisa, pq é de session
			request.getSession(true).setAttribute("postsMostrar", posts);
			request.getRequestDispatcher("mostrar_comentario.jsp").include(request, response);;
		}

	}

}
