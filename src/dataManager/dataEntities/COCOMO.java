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
			HashMap<String, String> factorsSF, HashMap<String, String> factorsEM) {
		// 求各SF因子的和
		Double sumSF = getSumSF(factorsSF);
		// 求各EM因子的乘积
		Double productEM = getProductEM(factorsEM);
		// 求effort
		String propertyKey = "EM.SCED." + factorsEM.get("SCED");
		Double SCED = Double.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", propertyKey));
		
		return getModuleEffortTime(size, sumSF, productEM/SCED, SCED);
	}
	
	// 模块工作量计算公式,输入参数为cocomo运算的中间值
	public static Double[] getModuleEffortTime(Double size, Double sumSF, Double productEM, Double SCED)
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
		Double PM = A * Math.pow((size / 1000), E) * productEM * SCED;
		// 求TDEV: Time to development
		Double TDEV = C * Math.pow((PM / SCED), (D + 0.2 * (E - B))) * SCED;
		Double[] effort = { PM, TDEV };
		
		System.out.println("sumSF = " + sumSF);
		System.out.println("productEM = " + productEM);
		System.out.println("SCED = " + SCED);
		System.out.println("PM = " + PM);
		System.out.println("TDEV = " + TDEV);
		
		return effort;
	}

	//一级集成工作量计算公式，输入参数为cocomo原始输入值
	public static Double[] getIntegratedEffortTime(Double[] sizes, Double[] productEMs,
			HashMap<String, String> factorsSF, String SCEDLevel) {
		
		// 求各SF因子的和
		Double sumSF = getSumSF(factorsSF);
		
		// 求effort
		String propertyKey = "EM.SCED." + SCEDLevel;
		Double SCED = Double.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", propertyKey));
		
		return getIntegratedEffortTime(sizes, productEMs, sumSF, SCED);
	}
	
	//一级集成工作量计算公式，输入参数为cocomo中间值
	public static Double[] getIntegratedEffortTime(Double[] sizes, Double[] productEMs,
			Double sumSF, Double SCED) {
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
		Double sumSize = 0.0;
		for(Double size: sizes)
			sumSize += size;
		Double PMBasic = A * Math.pow((sumSize / 1000), E) * SCED;
		Double PM = 0.0;
		for(int i=0; i<sizes.length; i++)
			PM += PMBasic * (sizes[i]/sumSize)* productEMs[i];
		// 求TDEV: Time to development
		Double TDEV = C * Math.pow((PM/SCED), (D + 0.2 * (E - B))) * SCED;
		
		Double[] effort = { PM, TDEV };
		
		System.out.println("sumSF = " + sumSF);
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
	private static Double getProductEM(HashMap<String, String> factorsEM)
	{
		Double productEM = 1.0;
		String propertyKey;
		Set<String> factors = factorsEM.keySet();
		for (Object factor : factors) {
			propertyKey = "EM." + factor.toString() + "."
					+ factorsEM.get(factor);
			productEM *= Double.valueOf(PropertyFile.readValue(
					"properties/COCOMO.properties", propertyKey));
		}
		return productEM;
	}
	
}
