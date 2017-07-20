package com.archsystemsinc.pqrs.restcontroller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.archsystemsinc.pqrs.model.CategoryLookup;
import com.archsystemsinc.pqrs.service.CategoryLookupService;

/**
 * @author Grmahun Redda
 *
 */

@RestController
@RequestMapping("/api")
public class CategoryLookupRestController {
	private static final Logger log = Logger.getLogger(CategoryLookupRestController.class);
	
	@Autowired
	private CategoryLookupService categoryLookupService;
	
	@RequestMapping(value = "/category/all", method = RequestMethod.GET)
	public List<CategoryLookup> getCategories(HttpServletRequest request, Principal currentUser){
		log.debug("--> getCategories");
		final List<CategoryLookup> categoryList = categoryLookupService.findAll();
		log.debug("<-- getCategories");
        return categoryList;
	}
	
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
	public CategoryLookup getCategoryById(@PathVariable int categoryId, HttpServletRequest request, Principal currentUser,final Model model){		
		return categoryLookupService.findById(categoryId);
	}

}
