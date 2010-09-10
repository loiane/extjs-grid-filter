package com.loiane.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.loiane.model.Company;
import com.loiane.model.ExtJSFilter;

/**
 * Contains all the logic to get information from ExtJS Filters and transform
 * into Hibernate objects.
 * It is a generic class. You can use it on you app if you use ExtJS Filters.
 * 
 * @author Loiane Groner
 * http://loianegroner.com (English)
 * http://loiane.com (Portuguese)
 */
@Component
public class HibernateUtil {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	/**
	 * Get list of Hibernate Restrictions from ExtJS Filters.
	 * It covers all ExtJS Filters.
	 * It is a generic method.
	 * @param filters ExtJSFilters
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws ParseException 
	 */
	public List<Criterion> getRestrictions(List<ExtJSFilter> filters) throws SecurityException, NoSuchFieldException, ParseException{
		
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		for (ExtJSFilter filter : filters){
			
			if (filter.getType().equals("string")){
				
				restrictions.add(Restrictions.ilike(filter.getField(),"%"+filter.getValue()+"%")); //case insensitive
				
			} else if (filter.getType().equals("boolean")){ //boolean in database is 1 (true) or 0 (false)
				
				restrictions.add(Restrictions.eq(filter.getField(), (byte)(Boolean.parseBoolean(filter.getValue())?1:0)));
				
			} else if (filter.getType().equals("numeric")){
				
				restrictions.add(getComparison(filter.getComparison(), filter.getField(), getNumericValue(filter.getValue(),filter.getField())));
				
			} else if (filter.getType().equals("date")){
				
				restrictions.add(getComparison(filter.getComparison(), filter.getField(), getDateValue(filter.getValue())));
				
			} else if (filter.getType().equals("list")){ //can contain only one value (use equals) or multiple values (use IN - list of values)
				
				String[] values = filter.getValue().split(",");
				
				if (values.length > 1){ //more than one value - use IN
					
					restrictions.add(Restrictions.in(filter.getField(), values));
					
				} else{ //just only one value - can use equals
					
					restrictions.add(Restrictions.eq(filter.getField(), filter.getValue()));
				}
			} 
		}
		
		return restrictions;
	}
	
	/**
	 * Get Comparison Restriction. It can be: equals, less than, larger than.
	 * ExtJS Filters do not use not equals.
	 * @param comparison
	 * @param field
	 * @param value
	 * @return
	 */
	private Criterion getComparison(String comparison, String field, Object value){
		
		if (comparison.equals("eq")){ //equals
			
			return Restrictions.eq(field, value);
			
		} else if (comparison.equals("lt")){ //less than
			
			return Restrictions.lt(field, value);
			
		} else if (comparison.equals("gt")){ //greater than
			
			return Restrictions.gt(field, value);
		}
		return null;
	}
	
	/**
	 * Get formated date
	 * @param value
	 * @return
	 * @throws ParseException 
	 */
	private Date getDateValue(String value) throws ParseException{
		
		return dateFormat.parse(value);
	}
	
	/**
	 * Get numeric value. It is a generic method.
	 * Used reflection to get attribute data type.
	 * @param value
	 * @param field
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	private Object getNumericValue(String value, String field) throws SecurityException, NoSuchFieldException{
		
		Field classField = Company.class.getDeclaredField(field);
		
		if (classField.getType() == Integer.TYPE){
			
			return Integer.parseInt(value);
			
		} else if (classField.getType() == Byte.TYPE){
			
			return Byte.parseByte(value);
			
		} else if (classField.getType() == Long.TYPE){
			
			return Long.parseLong(value);
			
		} else if (classField.getType() == Float.TYPE){
			
			return Float.parseFloat(value);
			
		} else if (classField.getType() == Double.TYPE){
			
			return Double.parseDouble(value);
		}
		
		return value;
	}
}
