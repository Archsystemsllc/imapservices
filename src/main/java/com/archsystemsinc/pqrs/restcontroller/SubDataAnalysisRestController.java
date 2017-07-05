package com.archsystemsinc.pqrs.restcontroller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.archsystemsinc.pqrs.model.SubDataAnalysis;
import com.archsystemsinc.pqrs.model.TemplateFile;
import com.archsystemsinc.pqrs.service.DataAnalysisService;
import com.archsystemsinc.pqrs.service.SubDataAnalysisService;

/**
 * This is the Spring Rest Controller Class for the sub Hypothesis Screen.
 * 
 * @author Grmahun Redda
 * @since 6/29/2017
 */
@RestController
@RequestMapping("/api")
public class SubDataAnalysisRestController {
	@Autowired
	private SubDataAnalysisService subDataAnalysisService;
	
	@Autowired
	private DataAnalysisService dataAnalysisService;
	
	@RequestMapping(value = "/subdata", method = RequestMethod.GET)
	public List<SubDataAnalysis> getSubDataAnalysis(final Model model){		
		return subDataAnalysisService.findAll();
	}
	
	@RequestMapping(value = "/subdata/{subdataId}", method = RequestMethod.GET)
	public SubDataAnalysis getSubDataAnalysisById(@PathVariable int subdataId, HttpServletRequest request, Principal currentUser,final Model model){		
		return subDataAnalysisService.findById(subdataId);
	}
	
	@RequestMapping(value = "/subdata/hypothesis/{dataId}", method = RequestMethod.GET)
	public List<SubDataAnalysis> getSubDataAnalysisByDataAnalysis(@PathVariable int dataId, HttpServletRequest request, Principal currentUser,final Model model){		
		return subDataAnalysisService.findByDataAnalysis(dataAnalysisService.findById(dataId));
	}
}
