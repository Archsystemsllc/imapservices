package com.archsystemsinc.pqrs.restcontroller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

//import org.apache.tomcat.util.http.fileupload.IOUtils;
//import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;

import com.archsystemsinc.pqrs.model.TemplateFile;
import com.archsystemsinc.pqrs.service.TemplateFileService;

@RestController
@RequestMapping("/api")
public class TemplateFileRestController {
	@Autowired
	private TemplateFileService templateFileService;
	
	@RequestMapping(value = "/templates", method = RequestMethod.GET)
	public List<TemplateFile> getTemplates(final Model model){		
		return templateFileService.findAll();
	}
	
	// Single file upload
    @PostMapping("/templates/upload")      
    public ResponseEntity<?> uploadFile(    		
    	@RequestParam("file") MultipartFile uploadedFile){
    	//MultipartFile uploadedFile = null;
		ByteArrayOutputStream outputStreamBuffer = null;
		TemplateFile templateFile = new TemplateFile();
	  try {
			/*File Upload Code - Start */
			//uploadedFile = templateFile.getUploadFile();		
			
			if(uploadedFile != null && !uploadedFile.isEmpty()) {
				InputStream inputStream = uploadedFile.getInputStream();
				templateFile.setUploadedFileName(uploadedFile.getOriginalFilename());
				templateFile.setUploadedFileType(uploadedFile.getContentType());				
				outputStreamBuffer = new ByteArrayOutputStream();
				
				
		        int nRead;
		        byte[] data = new byte[16384];
	
		        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
		        	outputStreamBuffer.write(data, 0, nRead);
		        }
	
		     outputStreamBuffer.flush();
			}		     
			templateFile.setUploadedFileContent(outputStreamBuffer.toByteArray());
			TemplateFile uploadedTemplateFile = templateFileService.create(templateFile);
			//redirectAttributes.addFlashAttribute("success", "success.save.file");
		}catch (Exception e) {			
			e.printStackTrace();
			return new ResponseEntity("Exception in file upload: " +
	                uploadedFile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
		}
		//return "redirect:../templates";

        return new ResponseEntity("Successfully uploaded - " +
                uploadedFile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
    
    
    @RequestMapping(value="/templates/download/{tempId}", method=RequestMethod.GET)
	public void downloadTemplate(@PathVariable int tempId, HttpServletResponse response) throws IOException {

    	TemplateFile document = templateFileService.findById(Long.valueOf(tempId));
        response.setContentType(document.getUploadedFileType());
        response.setContentLength(document.getUploadedFileContent().length);
        response.setHeader("Content-Disposition","attachment; filename=\"" + document.getUploadedFileName()+"\"");
  
        FileCopyUtils.copy(document.getUploadedFileContent(), response.getOutputStream());        
		response.flushBuffer();
	}
    
    @RequestMapping(value = { "/templates/download"}, method = RequestMethod.GET)
    public ResponseEntity<Void> downloadAllDocument(HttpServletResponse response) throws IOException {
		List<TemplateFile> templateFiles = templateFileService.findAll();
		
		if(!templateFiles.isEmpty()){
	        // Call the zipFiles method for creating a zip stream.
	        byte[] zip = zipFiles(templateFiles);
			
			response.setContentType("application/zip");
		    response.setContentLength(zip.length);
		    response.setHeader("Content-Disposition","attachment; filename=\"" + "DATA.ZIP"+"\"");
		  
		    FileCopyUtils.copy(zip, response.getOutputStream());
		    return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
		}else{
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		
	}
    
    @RequestMapping(value = { "/templates/delete/all"}, method = RequestMethod.GET)
    public ResponseEntity<Void> deleteDocuments() {
		List<TemplateFile> templateFiles = templateFileService.findAll();
		for(TemplateFile templateFile : templateFiles){
			templateFileService.deleteById(templateFile.getId());
		}
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
        //return "redirect:/admin/templates";
    }
    
    @RequestMapping(value = { "/templates/delete/{tempId}" }, method = RequestMethod.GET)
    public ResponseEntity<Void> deleteDocument(@PathVariable int tempId) {
		templateFileService.deleteById(Long.valueOf(tempId));
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(headers, HttpStatus.OK);
    }
    
    /**
     * Compress the given directory with all its files.
     */
    private byte[] zipFiles(List<TemplateFile> templateFiles) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        
        for(TemplateFile document: templateFiles) {            
            zos.putNextEntry(new ZipEntry(document.getUploadedFileName()));  
            zos.write(document.getUploadedFileContent(), 0, document.getUploadedFileContent().length);
            zos.closeEntry();            
        }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();

        return baos.toByteArray();
    }
}
