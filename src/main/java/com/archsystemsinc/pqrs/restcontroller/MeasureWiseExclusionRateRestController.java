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
import com.archsystemsinc.pqrs.model.MeasureWiseExclusionRate;
import com.archsystemsinc.pqrs.service.MeasureWiseExclusionRateService;

/**
 * @author Grmahun Redda
 *
 */

@RestController
@RequestMapping("/api")
public class MeasureWiseExclusionRateRestController {
	private static final Logger log = Logger.getLogger(MeasureWiseExclusionRateRestController.class);
	
	@Autowired
	private MeasureWiseExclusionRateService measureWiseExclusionRateService;
	
	@RequestMapping(value = "/measureExclusionRate/all", method = RequestMethod.GET)
	public List<MeasureWiseExclusionRate> getMeasureExclusionRates(HttpServletRequest request, Principal currentUser){
		log.debug("--> getMeasureExclusionRates");
		final List<MeasureWiseExclusionRate> measureExclusionRateList = measureWiseExclusionRateService.findAll();
		log.debug("<-- getMeasureExclusionRates");
        return measureExclusionRateList;
	}
	
	@RequestMapping(value = "/measureExclusionRate/{measureExclusionRateId}", method = RequestMethod.GET)
	public MeasureWiseExclusionRate getCategoryById(@PathVariable int measureExclusionRateId, HttpServletRequest request, Principal currentUser, final Model model){		
		return measureWiseExclusionRateService.findById(measureExclusionRateId);
	}
}
