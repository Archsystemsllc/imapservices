package com.archsystemsinc.pqrs.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * file upload class object for provider hypothesis, 
 * state wise statistics, and specialty
 * 
 * @author Grmahun Redda
 * @since 6/20/2017
 */
public class DocumentUpload {

    private MultipartFile provider;
    
    private int providerHypId;
    
    private int providerSubHypId;	

	private MultipartFile specialty;
	
	private MultipartFile statewise;	
	
	private Long documentTypeId;	
	

	public MultipartFile getProvider() {
		return provider;
	}

	public void setProvider(MultipartFile provider) {
		this.provider = provider;
	}	

	public int getProviderHypId() {
		return providerHypId;
	}

	public void setProviderHypId(int providerHypId) {
		this.providerHypId = providerHypId;
	}

	public int getProviderSubHypId() {
		return providerSubHypId;
	}

	public void setProviderSubHypId(int providerSubHypId) {
		this.providerSubHypId = providerSubHypId;
	}
	public MultipartFile getSpecialty() {
		return specialty;
	}

	public void setSpecialty(MultipartFile specialty) {
		this.specialty = specialty;
	}

	public MultipartFile getStatewise() {
		return statewise;
	}

	public void setStatewise(MultipartFile statewise) {
		this.statewise = statewise;
	}

	public Long getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(Long documentTypeId) {
		this.documentTypeId = documentTypeId;
	}
	
}
