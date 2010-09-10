package com.loiane.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.loiane.model.Company;

public interface ICompanyDAO {

	List<Company> getCompanies(int start, int limit, List<Criterion> restrictions);
	
	int getTotalCompanies(List<Criterion> restrictions);
}
