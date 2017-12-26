package org.jeecgframework.core.compartor;

import java.util.Comparator;

import org.jeecgframework.core.annotation.excel.Excel;

public class commonCompartor implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		if(o1 instanceof Excel){
			return ((Excel) o1).sortNum() - ((Excel) o2).sortNum();
		}
		
		return 0;
	}

}
