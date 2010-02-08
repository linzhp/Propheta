package data.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import data.database.dataAccess.DataBaseAccess;

public class MainClass {

	public static void main(String args[]){
		ArrayList<Integer> array = new ArrayList<Integer>();
		DataBaseAccess da= new DataBaseAccess();
		ResultSet rs = da.query("select duration,count(*) from csbsg group by duration order by count(*) asc");
		try {
			while (rs.next()){
				System.out.println("duration:"+rs.getInt("duration")+ " count:" + rs.getInt("count(*)"));
				array.add(rs.getInt("duration"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for( int i = 0; i < array.size(); i++) {
	        stats.addValue(array.get(i));
		}
		
		System.out.println("median= " + stats.getPercentile(50));
		System.out.println("mean= " + stats.getMean());
		DataBaseAccess.disposeConnections();
	}
}
