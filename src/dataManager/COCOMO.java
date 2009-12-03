package dataManager;

import java.util.HashMap;
import java.util.Set;

import file.PropertyFile;

public class COCOMO {

	// 重用代码量计算公式
	public static int getReuseSize(Double SLOC, Double AT, Double DM,
			Double CM, Double IM, Double AA, Double SU, Double UNFM) {
		Double size, AAF, AAM;
		AAF = (0.4 * DM) + (0.3 * CM) + (0.3 * IM);
		if (AAF <= 50)
			AAM = (AA + AAF * (1 + (0.02 * SU * UNFM))) / 100;
		else
			AAM = (AA + AAF + (SU * UNFM)) / 100;
		size = SLOC * (1 - (AT / 100)) * AAM;
		return size.intValue();
	}

	// 维护代码量计算公式
	public static int getMaintainSize(Double addSLOC, Double modifySLOC,
			Double SU, Double UNFM) {
		Double size = (addSLOC + modifySLOC) * (1 + ((SU / 100) * UNFM));
		return size.intValue();
	}

	// 模块工作量计算公式
	public static Double[] getModuleEffortTime(Double size,
			HashMap<String, String> factorsSF, HashMap<String, String> factorsEM) {
		Double sumSF = 0.0;
		Double multiEM = 1.0;
		Double A = Double.valueOf(PropertyFile.readValue(
				"properties\\COCOMO.properties", "A"));
		Double B = Double.valueOf(PropertyFile.readValue(
				"properties\\COCOMO.properties", "B"));
		Double C = Double.valueOf(PropertyFile.readValue(
				"properties\\COCOMO.properties", "C"));
		Double D = Double.valueOf(PropertyFile.readValue(
				"properties\\COCOMO.properties", "D"));
		// 求各SF因子的和
		String propertyKey;
		Set<String> factors = factorsSF.keySet();
		for (String factor : factors) {
			propertyKey = "SF." + factor + "." + factorsSF.get(factor);
			sumSF += Double.valueOf(PropertyFile.readValue(
					"properties\\COCOMO.properties", propertyKey));
		}
		// 求各EM因子的乘积
		factors = factorsEM.keySet();
		for (Object factor : factors) {
			propertyKey = "EM." + factor.toString() + "."
					+ factorsEM.get(factor);
			multiEM *= Double.valueOf(PropertyFile.readValue(
					"properties\\COCOMO.properties", propertyKey));
		}
		// 求effort
		Double E = B + 0.01 * sumSF;
		Double PM = A * Math.pow((size / 1000), E) * multiEM;
		// 求TDEV: Time to development
		propertyKey = "EM.SCED." + factorsEM.get("SCED");
		Double SCED = Double.valueOf(PropertyFile.readValue(
				"properties\\COCOMO.properties", propertyKey));
		Double TDEV = C * Math.pow((PM / SCED), (D + 0.2 * (E - B))) * SCED;
		Double[] effort = { PM, TDEV };
		System.out.println("A = " + A);
		System.out.println("B = " + B);
		System.out.println("C = " + C);
		System.out.println("D = " + D);
		System.out.println("E = " + E);
		System.out.println("sumSF = " + sumSF);
		System.out.println("multiEM = " + multiEM);
		System.out.println("SCED = " + SCED);
		System.out.println("PM = " + PM);
		System.out.println("TDEV = " + TDEV);
		return effort;
	}

}
