package dataManager.dataEntities;

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

	// 模块工作量计算公式，输入参数为cocomo原始输入值
	public static Double[] getModuleEffortTime(Double size,
			HashMap<String, String> factorsSF, HashMap<String, String> factorsEM, String EMtype) {
		// 求各SF因子的和
		Double sumSF = getSumSF(factorsSF);
		// 求各EM因子的乘积
		Double multiEM = getMultiEM(factorsEM, EMtype);
		// 求effort
		String propertyKey = EMtype + "." + "EM.SCED." + factorsEM.get("SCED");
		Double SCED = Double.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", propertyKey));
		
		return getModuleEffortTimeQuick(size, EMtype, sumSF, multiEM, SCED);
	}

	//一级集成工作量计算公式
	public static Double[] getIntegratedEffortTime(Double[] sizes,
			HashMap<String, String> factorsSF, HashMap<String, String> factorsEM, String EMtype) {
		//搜索A、B、C、D值
		Double A = Double.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", "A"));
		Double B = Double.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", "B"));
		Double C = Double.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", "C"));
		Double D = Double.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", "D"));
		// 求各SF因子的和
		Double sumSF = getSumSF(factorsSF);
		// 求各EM因子的乘积
		Double multiEM = getMultiEM(factorsEM, EMtype);
		// 求effort
		Double E = B + 0.01 * sumSF;
		String propertyKey = EMtype + "." + "EM.SCED." + factorsEM.get("SCED");
		Double SCED = Double.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", propertyKey));
		Double sumSize = 0.0;
		for(Double size: sizes)
			sumSize += size;
		Double PMBasic = A * Math.pow((sumSize / 1000), E) * SCED;
		Double PM = 0.0;
		for(Double size: sizes)
			PM += PMBasic * (size/sumSize)* (multiEM/SCED);
		// 求TDEV: Time to development
		Double TDEV = C * Math.pow((PM / SCED), (D + 0.2 * (E - B))) * SCED;
		
		Double[] effort = { PM, TDEV };
		
		System.out.println("sumSF = " + sumSF);
		System.out.println("multiEM = " + multiEM);
		System.out.println("SCED = " + SCED);
		System.out.println("PM = " + PM);
		System.out.println("TDEV = " + TDEV);
		
		return effort;
	}
	//求各SF因子的和
	private static Double getSumSF(HashMap<String, String> factorsSF)
	{
		Double sumSF = 0.0;
		String propertyKey;
		Set<String> factors = factorsSF.keySet();
		for (String factor : factors) {
			
			propertyKey = "SF." + factor + "." + factorsSF.get(factor);
			System.out.println(propertyKey);
			sumSF += Double.valueOf(PropertyFile.readValue(
					"properties/COCOMO.properties", propertyKey));
			System.out.println(propertyKey);
		}
		return sumSF;
	}
	// 求各EM因子的乘积
	private static Double getMultiEM(HashMap<String, String> factorsEM, String EMtype)
	{
		Double multiEM = 1.0;
		String propertyKey;
		Set<String> factors = factorsEM.keySet();
		for (Object factor : factors) {
			propertyKey = EMtype + "." + "EM." + factor.toString() + "."
					+ factorsEM.get(factor);
			multiEM *= Double.valueOf(PropertyFile.readValue(
					"properties/COCOMO.properties", propertyKey));
		}
		return multiEM;
	}
	// 模块工作量计算公式,输入参数为cocomo运算的中间值
	public static Double[] getModuleEffortTimeQuick(Double size, String EMtype, Double sumSF, Double multiEM, Double SCED)
	{
		//搜索A、B、C、D值
		Double A = Double.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", "A"));
		Double B = Double.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", "B"));
		Double C = Double.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", "C"));
		Double D = Double.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", "D"));
		// 求effort
		Double E = B + 0.01 * sumSF;
		Double PM = A * Math.pow((size / 1000), E) * multiEM;
		// 求TDEV: Time to development
		Double TDEV = C * Math.pow((PM / SCED), (D + 0.2 * (E - B))) * SCED;
		Double[] effort = { PM, TDEV };
		
		System.out.println("sumSF = " + sumSF);
		System.out.println("multiEM = " + multiEM);
		System.out.println("SCED = " + SCED);
		System.out.println("PM = " + PM);
		System.out.println("TDEV = " + TDEV);
		
		return effort;
	}
}
