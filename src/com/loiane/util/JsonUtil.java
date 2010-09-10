package com.loiane.util;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Component;

import com.loiane.model.ExtJSFilter;

/**
 * Util class. 
 * Deserialize JSON object from request into Java object format.
 * 
 * @author Loiane Groner
 * http://loianegroner.com (English)
 * http://loiane.com (Portuguese)
 */
@Component
public class JsonUtil {
	
	/**
	 * Get list of extjsFilters from request.
	 * Transform json data format into list of extjsFilter objects
	 * @param data - json data from request 
	 * @return list of extjsFilters
	 */
	@SuppressWarnings("unchecked")
	public List<ExtJSFilter> getExtJSFiltersFromRequest(Object data){

		JSONArray jsonArray = JSONArray.fromObject(data);
		List<ExtJSFilter> extjsFilters = (List<ExtJSFilter>) JSONArray.toCollection(jsonArray,ExtJSFilter.class);

		return extjsFilters;
	}

}
