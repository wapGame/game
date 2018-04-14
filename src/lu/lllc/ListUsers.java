package lu.lllc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListUsers
 */
@WebServlet("/ListUsers")
public class ListUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListUsers() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ArrayList<User> userList = new ArrayList<User>();
		// Adding the (at this moment empty) bookList to the session
		request.setAttribute("userList", userList);

		Connection connection;
		Statement statement;
		ResultSet result = null;

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
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Error. Can not create the statement: " + e);
			return;
		}

		String searchString = "SELECT * FROM users";
		try {
			result = statement.executeQuery(searchString);
		} catch (SQLException e) {
			System.out.println("Error. Problem with executeUpdate: " + e);
			return;
		}

		// Now we collect the data from the result in order to display them in
		// JSP

		try {
			while (result.next()) {
				User user = new User();
				String username = result.getString("username");
				user.setUsername(username);

				String password = result.getString("password");
				user.setPassword(password);

				userList.add(user);
			}
		} catch (SQLException e) {
			System.out.println("Error. Problem reading data: " + e);
			return;
		}

		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error. Problem with closing connection: " + e);
			return;
		}

		RequestDispatcher disp = request
				.getRequestDispatcher("/WEB-INF/showUsers.jsp");
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
