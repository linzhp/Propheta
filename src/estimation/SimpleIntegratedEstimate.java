package estimation;

import java.sql.SQLException;

import data.database.dataAccess.ConstantsAccess;

public class SimpleIntegratedEstimate {

	//简单的集成计算公式
	public static Double getIntegratedEffort(Double[] efforts) throws SQLException
	{
		Double integratedEffort = 0.0;
		for(Double effort: efforts)
			integratedEffort += effort;
		ConstantsAccess contants = new ConstantsAccess();
		integratedEffort *= contants.get("集成因子");
		return integratedEffort;
	}
}
