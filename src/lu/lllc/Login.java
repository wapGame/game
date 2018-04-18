package lu.lllc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Login")
public class Login extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public Login() {
		super();
	}
	
	private boolean checkUser(String username, String password) {
		boolean exist = false;
		
		Connection connection;
		PreparedStatement statement;

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
				return false;
			}
			
			try {
				statement = connection.prepareStatement("SELECT * FROM users WHERE name=? AND password=?");
				
				statement.setString(1, username);
				statement.setString(2, password);
				
				statement.executeUpdate();
				ResultSet rs = statement.executeQuery();
		        exist = rs.next();
		         
			} catch (SQLException e) {
				System.out.println("Error. Can not create the statement: " + e);
				return false;
			}
			
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Error. Problem with closing connection: " + e);
				return false;
			}
			return exist;
			
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(checkUser(username, password)) {
			RequestDispatcher disp = request.getRequestDispatcher("/WEB-INF/game.html");
			disp.forward(request, response);			
			System.out.println("checkUser == true");
		} else {
			RequestDispatcher disp = request.getRequestDispatcher("/WEB-INF/index.html");
			disp.forward(request, response);
			System.out.println("checkUser == false");
		}


	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			doGet(request, response);
	}

}
