package com.loiane.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loiane.service.CompanyService;

/**
 * Controller - Spring
 * 
 * @author Loiane Groner
 * http://loianegroner.com (English)
 * http://loiane.com (Portuguese)
 */
@Controller
@RequestMapping(value="/company")
public class CompanyController {

	private CompanyService companyService;
	
	@RequestMapping(value="/view.action")
	public @ResponseBody Map<String,? extends Object> view(@RequestParam int start, @RequestParam int limit,
			@RequestParam(required=false) Object filter) throws Exception {

		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		
		try{
			
			modelMap = companyService.getCompanyModelMap(start, limit, filter);

			return modelMap;

		} catch (Exception e) {
			
			e.printStackTrace();
			
			modelMap.put("success", false);

			return modelMap;
		}
	}
	
	@Autowired
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
}
