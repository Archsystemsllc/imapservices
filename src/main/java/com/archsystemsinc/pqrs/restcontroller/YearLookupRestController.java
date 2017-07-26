package com.archsystemsinc.pqrs.restcontroller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.archsystemsinc.pqrs.model.YearLookup;
import com.archsystemsinc.pqrs.service.YearLookupService;

@RestController
@RequestMapping("/api")
public class YearLookupRestController {
	private static final Logger log = Logger.getLogger(YearLookupRestController.class);
	
	@Autowired
	private YearLookupService yearLookupService; 
	
	@RequestMapping(value = "/year/all", method = RequestMethod.GET)
	public List<YearLookup> getYears(HttpServletRequest request, Principal currentUser){
		log.debug("--> getYears");
		final List<YearLookup> yearList = yearLookupService.findAll();
		log.debug("<-- getYears");
        return yearList;
	}
	
	@RequestMapping(value = "/year/{yearId}", method = RequestMethod.GET)
	public YearLookup getMeasureById(@PathVariable int yearId, HttpServletRequest request, Principal currentUser,final Model model){		
		return yearLookupService.findById(yearId);
	}
}
