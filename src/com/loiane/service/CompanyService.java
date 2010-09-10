package com.loiane.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loiane.dao.CompanyDAO;
import com.loiane.model.Company;
import com.loiane.model.ExtJSFilter;
import com.loiane.util.HibernateUtil;
import com.loiane.util.JsonUtil;

/**
 * Company Service
 * 
 * @author Loiane Groner
 * http://loianegroner.com (English)
 * http://loiane.com (Portuguese)
 */
@Service
public class CompanyService {

	private CompanyDAO companyDAO;
	private JsonUtil jsonUtil;
	private HibernateUtil hibernateUtil;
	
	/**
	 * Get ModelMap to return to Sptring Controller.
	 * Contais all the logic to populate an Ext.data.Store, which
	 * is required to render and populate ExtJS DataGrid
	 * @param start first result to get from database
	 * @param limit max of result to bring from database
	 * @param filter extjs filters - object from request
	 * @return modelmap with following jeys: data, total, success
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws ParseException
	 */
	public Map<String,Object> getCompanyModelMap(int start, int limit, Object filter) throws SecurityException, NoSuchFieldException, ParseException{
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		
		List<ExtJSFilter> filters = null;
		
		if (filter != null){ //filter is optional
			filters = jsonUtil.getExtJSFiltersFromRequest(filter);
		}
		
		List<Criterion> restrictions = getHibernateCriteriaList(filters);
		
		modelMap.put("data", getCompanyList(start, limit, restrictions));
		modelMap.put("total", getTotalCompanies(restrictions));
		modelMap.put("success", true);
		
		return modelMap;
	}
	
	/**
	 * Get List of companies from database
	 * @param start first result to get from database
	 * @param limit max of result to bring from database
	 * @param restrictions hibernate restrictions list - optional
	 * @return list of companies
	 */
	private List<Company> getCompanyList(int start, int limit, List<Criterion> restrictions) {
		
		return companyDAO.getCompanies(start, limit, restrictions);
	}
	
	/**
	 * Get total of Companies from database.
	 * Need to set this value on ExtJS Store - required for paging
	 * @param restrictions hibernate restrictions list - optional
	 * @return
	 */
	private int getTotalCompanies(List<Criterion> restrictions) {
		
		return companyDAO.getTotalCompanies(restrictions);
	}
	
	/**
	 * Get list of Hibernate Criterion - used to represent
	 * restrictions of ExtJS Filter
	 * @param filters ExtJS filters - optional
	 * @return list of criterions
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws ParseException
	 */
	private List<Criterion> getHibernateCriteriaList(List<ExtJSFilter> filters) throws SecurityException, NoSuchFieldException, ParseException{
		
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		if (filters != null){ //filters is optional
			
			restrictions = hibernateUtil.getRestrictions(filters);
		}
		
		return restrictions;
	}

	/**
	 * Spring use - DI
	 * @param companyDAO
	 */
	@Autowired
	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	/**
	 * Spring use - DI
	 * @param companyDAO
	 */
	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
	
	/**
	 * Spring use - DI
	 * @param companyDAO
	 */
	@Autowired
	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}
	
}
