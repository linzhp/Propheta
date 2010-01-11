package estimation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import dataManager.dataAccess.DataBaseAccess;


public class CSBSG {
	private final static HashMap<String, Double> constData = new HashMap<String, Double>();
	static{
		constData.put("developmentType.ReDevelopment", -0.16);
		constData.put("developmentType.NewDevelopment", -0.46);
		constData.put("developmentType.Enhancement", 0.0);
		constData.put("developmentType.Other", 0.0);

		constData.put("businessArea.Telecom", 0.32);
		constData.put("businessArea.Transport", -0.13);
		constData.put("businessArea.Finance", 0.48);
		constData.put("businessArea.Retail", 0.81);
		constData.put("businessArea.Media", 0.87);
		constData.put("businessArea.Energy", 0.12);
		constData.put("businessArea.Other", 0.28);
		constData.put("businessArea.Generic", 0.17);
		constData.put("businessArea.HealthCare", 0.38);
		constData.put("businessArea.PublicAdmin", 0.123);
		constData.put("businessArea.Manufacturing", 0.0);

		constData.put("language.ASP", -0.29);
		constData.put("language.C", 0.23);
		constData.put("language.C#", -0.06);
		constData.put("language.C++", 0.34);
		constData.put("language.Cobol", -0.24);
		constData.put("language.Java", 0.30);
		constData.put("language.VB", 0.65);
		constData.put("language._cons", 0.31);
		constData.put("language.Other", 0.0);
	}

	private static int[] getSizeScale(int size) {
		int projectSize = size;
		if (projectSize >= 0 && projectSize <= 4000)
			return (new int[] { 0, 4000 });
		else if (projectSize > 4000 && projectSize <= 16000)
			return (new int[] { 4000, 16000 });
		else if (projectSize > 16000 && projectSize <= 64000)
			return (new int[] { 16000, 64000 });
		else if (projectSize > 64000 && projectSize <= 256000)
			return (new int[] { 16000, 256000 });
		else if (projectSize > 256000 && projectSize <= 1024000)
			return (new int[] { 256000, 1024000 });
		else
			return (new int[] { 1024000, -1 });
	}

	public static ArrayList<Double> getProductivity(int size,
			HashMap<String, String> factors) {
		ArrayList<Double> arrayList = new ArrayList<Double>();
		String sql = null;

		int[] sizeScale = getSizeScale(size);
		int minSize = sizeScale[0];
		int maxSize = sizeScale[1];

		if (minSize < 1024000)
			sql = "select productivity from csbsg where projectSize>="
					+ minSize + " and " + "projectSize<" + maxSize;
		else
			sql = "select productivity from csbsg where projectSize>="
					+ minSize;
		
		Set<String> factorSet = factors.keySet();
		for (String factor : factorSet) {
			if (factor != "duration")
				sql += " and " + factor + "='" + factors.get(factor) + "'";
		}
		sql += " order by productivity asc";
		System.out.println(sql);

		DataBaseAccess dataAccess = new DataBaseAccess();
		dataAccess.initConnection();
		ResultSet resultSet = dataAccess.query(sql);
		try {
			while (resultSet.next()) {
				arrayList.add(resultSet.getDouble("productivity"));
				System.out
						.println("pi: " + resultSet.getDouble("productivity"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataAccess.disposeConnection();
		return arrayList;
	}

	// 返回projectSize,effort数组
	public static ArrayList<Double[]> getEffort(int size, double percent) {
		ArrayList<Double[]> array = new ArrayList<Double[]>();

		Double projectSize = (double) size;
		Double minSize = projectSize * (1 - percent);
		Double maxSize = projectSize * (1 + percent);

		String sql = "select projectSize, effort from csbsg where projectSize>="
				+ minSize
				+ " and "
				+ "projectSize<"
				+ maxSize
				+ " order by effort asc";

		DataBaseAccess dataAccess = new DataBaseAccess();
		dataAccess.initConnection();
		ResultSet resultSet = dataAccess.query(sql);
		try {
			while (resultSet.next())
				array.add(new Double[] { resultSet.getDouble("projectSize"),
						resultSet.getDouble("effort") });
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataAccess.disposeConnection();
		return array;

	}

	//由CSBSG公式计算effort值
	public static Double getEqnEffort(Double projectSize,
			HashMap<String, String> factors) {
		Double teamSize, duration, lnEffort;
		if (factors.containsKey("teamSize")) {
			teamSize = Double.parseDouble(factors.get("teamSize").toString());
		} else
			// 用户未输入团队规模时，取默认值5
			teamSize = 5.0;
		if (factors.containsKey("duration")) {
			duration = Double.parseDouble(factors.get("duration").toString());
		} else
			// 用户未输入项目周期时，取默认值180天
			duration = 180.0;
		
		// 按公式计算
		lnEffort = 0.38 * Math.log(projectSize) + 0.5 * Math.log(teamSize)
				+ 0.55 * Math.log(duration) + 0.31;
		Set<String> keys = factors.keySet();
		for (String key : keys) {
			if(key != "teamSize" && key != "duration"){
				lnEffort += constData.get(key + "." + factors.get(key));
				System.out.println(key + "." + factors.get(key));
			}
		}
		System.out.println("duration = " + duration);
		return Math.exp(lnEffort);
	}

	/*
	 * public static void main(String[] args) { CSBSG c = new CSBSG();
	 * ArrayList<Double[]> l = c.getEffort(10000.0, 0.1); for(int i=0;
	 * i<l.size(); i++) System.out.println(l.get(i)[0]+":"+l.get(i)[1]); }
	 */
}
