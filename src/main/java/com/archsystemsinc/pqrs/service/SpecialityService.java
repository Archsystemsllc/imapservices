package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.Speciality;

/**
 * interface for template service class 
 * 
 * @author Grmahun Redda
 * @since 6/16/2017
 */
public interface SpecialityService {
	Speciality create(final Speciality specialty);	
	void update(final Speciality specialty);
	Speciality findById(final Long id);
	void deleteById(final Long id);
	List<Speciality> findAll();    
    List<Speciality> findAllByUserId(Long id);

}
