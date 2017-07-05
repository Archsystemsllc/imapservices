package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.TemplateFile;

/**
 * interface for template service class 
 * 
 * @author Grmahun Redda
 * @since 6/16/2017
 */
public interface TemplateFileService {
	TemplateFile create(final TemplateFile uploadedFile);	
	void update(final Long id);
	TemplateFile findById(final Long id);
	void deleteById(final Long id);
	List<TemplateFile> findAll();    
    List<TemplateFile> findAllByUserId(Long id);  

}
