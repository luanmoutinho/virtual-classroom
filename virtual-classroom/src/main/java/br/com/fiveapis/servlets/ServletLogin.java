package br.com.fiveapis.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fiveapis.dao.DaoLogin;
import br.com.fiveapis.model.ModelLogin;

@WebServlet(urlPatterns = { "/principal/ServletLogin", "/ServletLogin" })
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoLogin daoLogin = new DaoLogin();

	public ServletLogin() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String userPassword = request.getParameter("password");
		String url = request.getParameter("url");

		try {

			if (email != null && !email.isEmpty() && userPassword != null && !userPassword.isEmpty()) {

				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setEmail(email);
				modelLogin.setUserPassword(userPassword);

				if (daoLogin.validarLogin(modelLogin)) {

					request.getSession().setAttribute("usuario", modelLogin.getEmail());

					if (url == null || url.equalsIgnoreCase("null")) {
						url = "principal/principal.jsp";
					}
					RequestDispatcher redirect = request.getRequestDispatcher(url);
					redirect.forward(request, response);

				} else {
					RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
					request.setAttribute("msg", "não foi encontrado o login");
					redirect.forward(request, response);
				}

			} else {
				RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "Informe o Login para continuar");
				redirect.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
			request.setAttribute("msg", "um erro inesperado aconteceu");
			redirect.forward(request, response);
		}

	}

}
