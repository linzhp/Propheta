package dataManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainClass {

	public static void main(String args[]){
		DataAccess da= new DataAccess();
		ResultSet rs = da.query("select teamSize from csbsg where teamsize>30");
		try {
			while (rs.next()){
				System.out.println("teamSize:"+rs.getInt("teamSize"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		da.close();
	}
}
