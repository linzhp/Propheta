package estimation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import data.database.dataAccess.DataBaseAccess;


public class ISBSG {
	private final static HashMap<String, Double> constData = new HashMap<String, Double>();
	static{
		constData.put("developmentPlatform.MR", -0.138);
		constData.put("developmentPlatform.Multi", -0.219);
		constData.put("developmentPlatform.PC", -0.269);
		constData.put("developmentPlatform.MF", 0.0);

		constData.put("developmentTechniques.Object Oriented Analysis;Object Oriented Design", -0.403);
		constData.put("developmentTechniques.Event Modelling", -0.447);
		constData.put("developmentTechniques.Business Area Modelling", -0.276);
		constData.put("developmentTechniques.Regression Testing", -0.024);
		constData.put("developmentTechniques.Object Oriented Analysis;Object Oriented Design;Event Modelling", 0.821);
		constData.put("developmentTechniques.Regression Testing;Business Area Modelling", 1.015);
		constData.put("developmentTechniques.Other", 0.0);

		constData.put("languageType.2GL", 0.0);
		constData.put("languageType.3GL", -0.463);
		constData.put("languageType.4GL", -1.049);
		constData.put("languageType.ApG", -1.021);
	}

	private static int[] getSizeScale(int size, double percent) {
		double projectSize = size;
		return (new int[] { (int) (projectSize * (1 - percent)),
				(int) (projectSize * (1 + percent)) });
	}

	public static ArrayList<Double> getPDR(int size,
			HashMap<String, String> factors) {
		ArrayList<Double> arrayList = new ArrayList<Double>();
		String sql = null;

		int[] sizeScale = getSizeScale(size, 0.1);
		int minSize = sizeScale[0];
		int maxSize = sizeScale[1];
		sql = "select PDR from isbsg where functionPoints>=" + minSize
				+ " and " + "functionPoints<" + maxSize;
		
		Set<String> factorSet = factors.keySet();
		for (String factor : factorSet) {
			if (factor == "teamSize"){
				double teamSize = Double.parseDouble(factors.get(factor));
				sql += " and " + "teamSize" + ">=" + (teamSize - 0.5) + " and " + "teamSize<" + (teamSize +
						0.5);
			}
			else if(factor != "developmentTechniques")
				sql += " and " + factor + "='" + factors.get(factor) + "'";
		}
		sql += " order by PDR asc";
		System.out.println(sql);

		DataBaseAccess dataAccess = new DataBaseAccess();
		dataAccess.initConnection();
		ResultSet resultSet = dataAccess.query(sql);
		try {
			while (resultSet.next()) {
				arrayList.add(resultSet.getDouble("PDR"));
				System.out.println("PDR: " + resultSet.getDouble("PDR"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataAccess.disposeConnection();
		return arrayList;
	}

	//由ISBSG公式计算PDR值
	public static Double getEqnPDR(HashMap<String, String> factors) {
		Double teamSize, lnPDR;
		if (factors.containsKey("teamSize")) {
			teamSize = Double.parseDouble(factors.get("teamSize").toString());
		} else
			// 用户未输入团队规模时，取默认值5
			teamSize = 5.0;
		// 按公式计算
		lnPDR = 2.651+0.357*Math.log(teamSize);
		Set<String> keys = factors.keySet();
		for (String key : keys) {
			if(key != "teamSize" && key != "developmentType"){
				lnPDR += constData.get(key + "." + factors.get(key));
				System.out.println(key + "." + factors.get(key));
			}
		}
		return Math.exp(lnPDR);
	}

	/*
	 * public static void main(String[] args) { CSBSG c = new CSBSG();
	 * ArrayList<Double[]> l = c.getEffort(10000.0, 0.1); for(int i=0;
	 * i<l.size(); i++) System.out.println(l.get(i)[0]+":"+l.get(i)[1]); }
	 */
}
