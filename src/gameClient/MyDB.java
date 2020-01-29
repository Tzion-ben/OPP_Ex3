package gameClient;

/**
 * This class is about pull the data from the DB about me games , my level and my position according to the 
 * class
 * @author tzion
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MyDB {

	public static final String jdbcUrl="jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
	public static final String jdbcUser="student";
	public static final String jdbcUserPassword="OOP2020student";


	/************************************************************************************************
	 * simply prints all the games as played by the user (in the database).
	 ************************************************************************************************/
	public ArrayList<Integer> printMYLog(int id) {
		ArrayList<Integer> MyResultd=new ArrayList<Integer>();
		int disLevel=-1;
		int countGames=0;		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//this method load the driver, belongs to class CLASS
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
			Statement statement = connection.createStatement();
			String allCustomersQuery = "SELECT * FROM Logs where userID="+id;


			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			int ind =0;
			while(resultSet.next())
			{
				countGames++;
				disLevel=resultSet.getInt("levelID");

				//*******it will take the level wevery time and at the end of the lopp it will take the 
				//currect last level

				//				System.out.println(ind+") Id: " + resultSet.getInt("UserID")+", level: "+
				//						resultSet.getInt("levelID")+", score: "+resultSet.getInt("score")+", moves:"
				//						+ " "+resultSet.getInt("moves")+", time: "+resultSet.getDate("time"));
				//				ind++;
			}//end of the while loop

			resultSet.close();
			statement.close();		
			connection.close();	

			MyResultd.add(countGames);
			MyResultd.add(disLevel);

		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(MyResultd.isEmpty())
			MyResultd.add(-1);
		
		return MyResultd;
	}

	/************************************************************************************************
	 ************************************simple constructor******************************************
	 ************************************************************************************************/
	public MyDB() {;}
}//end of ths class
