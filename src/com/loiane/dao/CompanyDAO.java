package com.loiane.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.loiane.model.Company;

/**
 * Company DAO class
 * 
 * @author Loiane Groner
 * http://loianegroner.com (English)
 * http://loiane.com (Portuguese)
 */
@Repository
public class CompanyDAO implements ICompanyDAO {

	private HibernateTemplate hibernateTemplate;

	/**
	 * Get List of companies from database
	 * @param start first result to get from database
	 * @param limit max of result to bring from database
	 * @param restrictions hibernate restrictions list - optional
	 * @return list of companies
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getCompanies(int start, int limit, List<Criterion> restrictions) {
		
		DetachedCriteria criteria = getHibernateCriteria(restrictions);
		
		return hibernateTemplate.findByCriteria(criteria, start, limit);
		
	}
	
	/**
	 * Get total of Companies from database
	 * @param restrictions hibernate restrictions list - optional
	 * @return
	 */
	@Override
	public int getTotalCompanies(List<Criterion> restrictions) {
		
		DetachedCriteria criteria = getHibernateCriteria(restrictions);
		
		Projection projection = Projections.rowCount();
		criteria.setProjection(projection);
		
		return DataAccessUtils.intResult(hibernateTemplate.findByCriteria(criteria));
	}
	
	/**
	 * Get DetachedCriteria with restrictions.
	 * Restrictions are used because of ExtJS Filters
	 * @param restrictions
	 * @return
	 */
	private DetachedCriteria getHibernateCriteria(List<Criterion> restrictions) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Company.class);
		
		if (!restrictions.isEmpty()){ //filters is optional
			
			for (Criterion criterion : restrictions){
				criteria.add(criterion);
			}
		}
		
		return criteria;
	}
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

}
