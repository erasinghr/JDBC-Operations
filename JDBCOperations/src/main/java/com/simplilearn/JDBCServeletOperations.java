package com.simplilearn;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class JDBCServeletOperations
 */
@WebServlet("/JDBCServeletOperations")
public class JDBCServeletOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public JDBCServeletOperations() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			PrintWriter printWriter = response.getWriter();
			printWriter.println("<html><body");
			InputStream inputStream = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
			Properties props = new Properties();
			props.load(inputStream);
			JDBCOperations conn = new JDBCOperations(props.getProperty("url"), props.getProperty("userid"), props.getProperty("password"));
			//Insert Statement demo
			Statement statement = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate("insert into eproduct(name, price, date_added) values('Apple', 132000, now())");
			statement.executeUpdate("insert into eproduct(name, price, date_added) values('Dell', 250000, now())");
			statement.executeUpdate("insert into eproduct(name, price, date_added) values('hp', 60000, now())");
			statement.executeUpdate("insert into eproduct(name, price, date_added) values('Asus', 121150, now())");
			statement.executeUpdate("insert into eproduct(name, price, date_added) values('Lenovo', 25000, now())");
			
			printWriter.println("Insert operation successful<br>");
			
			//Update Statement demo
			statement.executeUpdate("update eproduct set price=25000 where name='Acer'");
			printWriter.println("Update operation successful<br>");
			
			//Delete Statement demo
			int delCount = statement.executeUpdate("delete from eproduct where name='hp'");
			printWriter.println("Delete Operation done...deleted product count:"+delCount);
			printWriter.println("<br><br>");
			//ResultSet Sorting
			ResultSet rst = statement.executeQuery("select * from eproduct ORDER by name DESC"); // Fetch the data from DB
			
			//Iterate through the Resultset Data
			while(rst.next())
			{
				printWriter.println(rst.getInt("ID")+","+rst.getString("name")+"<br>"); // Print the rows in the product list table
			}
			statement.close();
			printWriter.println("</body></html>");
			conn.closeConnection();	
		}catch(SQLException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
