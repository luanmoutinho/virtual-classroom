package br.com.fiveapis.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.fiveapis.connection.SingleConnection;


@WebFilter(urlPatterns = {"/principal/*"})
public class FilterAutenticacao extends HttpFilter implements Filter {
       
    
	private static final long serialVersionUID = 1L;
	private static Connection connection;


	public FilterAutenticacao() {
        super();
       
    }

	
	public void destroy() {
		
		try {
			connection.close();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		try {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String usuarioLogado = (String) session.getAttribute("usuario");
		String urlParaAutenticar = req.getServletPath();
		
		if(usuarioLogado == null && 
				!urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {
			RequestDispatcher redirect = request.getRequestDispatcher("/index.jsp?url="+urlParaAutenticar);
		request.setAttribute("msg", "É necessário realizar o login");
		redirect.forward(request, response);
		return;
			
		}else {
			chain.doFilter(req, response);
		}
		
			connection.commit();
		} catch (SQLException e) {
			
			e.printStackTrace();
			RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
			request.setAttribute("msg", "um erro ocorreu no filtro");
			redirect.forward(request, response);
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}
		
		
		
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnection.getConnection();
	}

}
