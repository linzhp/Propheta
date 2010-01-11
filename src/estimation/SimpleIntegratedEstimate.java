package estimation;

public class SimpleIntegratedEstimate {

	//简单的集成计算公式
	public static Double getIntegratedEffort(Double[] efforts)
	{
		Double integratedEffort = 0.0;
		for(Double effort: efforts)
			integratedEffort += effort;
		integratedEffort *= (1 +0.2);
		return integratedEffort;
	}
	
}
