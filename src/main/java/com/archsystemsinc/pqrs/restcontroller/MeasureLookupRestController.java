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
import com.archsystemsinc.pqrs.model.MeasureLookup;
import com.archsystemsinc.pqrs.service.MeasureLookupService;

/**
 * @author Grmahun Redda
 *
 */

@RestController
@RequestMapping("/api")
public class MeasureLookupRestController {
	private static final Logger log = Logger.getLogger(MeasureLookupRestController.class);
	
	@Autowired
	private MeasureLookupService measureLookupService;
	
	@RequestMapping(value = "/measure/all", method = RequestMethod.GET)
	public List<MeasureLookup> getMeasures(HttpServletRequest request, Principal currentUser){
		log.debug("--> getMeasures");
		final List<MeasureLookup> measureList = measureLookupService.findAll();
		log.debug("<-- getMeasures");
        return measureList;
	}
	
	@RequestMapping(value = "/measure/{measureId}", method = RequestMethod.GET)
	public MeasureLookup getMeasureById(@PathVariable int measureId, HttpServletRequest request, Principal currentUser,final Model model){		
		return measureLookupService.findById(measureId);
	}
}
