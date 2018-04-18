package lu.lllc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddUser
 * 
 */

@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	
	public AddUser() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Connection connection;
		PreparedStatement statement;
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String dbURL = DBInfo.getDBURL();
		String dbuser = DBInfo.getUser();
		String dbpassword = DBInfo.getPassword();

		try {

			Class.forName(DBInfo.getDriver());
		} catch (ClassNotFoundException e) {
			System.out.println("Error. Driver class not found: " + e);
		}
		
		try {
			connection = DriverManager.getConnection(dbURL, dbuser, dbpassword);
		} catch (SQLException e) {
			System.out.println("Error. Connection problem: " + e);
			return;
		}
		
		
		try {
			statement = connection.prepareStatement("INSERT INTO users (id, username, password) VALUES (0,?,?)");
			
			statement.setString(1, username);
			statement.setString(2, password);
		} catch (SQLException e) {
			System.out.println("Error. Can not create the statement: " + e);
			return;
		}
		
		
		
		try {
					
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error. Problem with executeUpdate: " + e);
			return;
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error. Problem with closing connection: " + e);
			return;
		}
		
		RequestDispatcher disp = request.getRequestDispatcher("/WEB-INF/addingOk.jsp");
		disp.forward(request, response);

		
		
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
