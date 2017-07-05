package com.archsystemsinc.pqrs.restcontroller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.archsystemsinc.pqrs.model.DocumentUpload;
import com.archsystemsinc.pqrs.model.ProviderHypothesis;
import com.archsystemsinc.pqrs.model.Speciality;
import com.archsystemsinc.pqrs.model.StatewiseStatistic;
import com.archsystemsinc.pqrs.service.ParameterLookUpService;
import com.archsystemsinc.pqrs.service.ProviderHypothesisService;
import com.archsystemsinc.pqrs.service.ReportingOptionLookUpService;
import com.archsystemsinc.pqrs.service.SpecialityService;
import com.archsystemsinc.pqrs.service.StatewiseStatisticService;
import com.archsystemsinc.pqrs.service.YearLookUpService;

@RestController
@RequestMapping("/api")
public class DocumentUploadRestController {
	private static final Logger log = Logger.getLogger(DocumentUploadRestController.class);
	@Autowired
	private ProviderHypothesisService providerHypothesisService;
	
	@Autowired
	private ParameterLookUpService parameterLookUpService;
	
	@Autowired
	private ReportingOptionLookUpService reportingOptionLookUpService;
	
	@Autowired
	private YearLookUpService yearLookUpService;
	
	@Autowired
	private SpecialityService specialtyService;
	
	@Autowired
	private StatewiseStatisticService statewiseStatisticService;
	
	//@PostMapping("/file/import")
	@RequestMapping(value = "/file/import", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<?> fileUploadPost(@RequestBody final DocumentUpload documentFileUpload) throws InvalidFormatException {		
		log.debug("--> fileUploadPost -->");
		try {				
			
			if(documentFileUpload.getProvider().getSize() > 0){
				documentFileUpload.setDocumentTypeId(1L);
				documentUploadProvider(documentFileUpload);
			}else if(documentFileUpload.getSpecialty().getSize() > 0){
				documentFileUpload.setDocumentTypeId(2L);
				specialtyDocUpload(documentFileUpload);
			}else if(documentFileUpload.getStatewise().getSize() > 0){
				documentFileUpload.setDocumentTypeId(3L);
				stateWiseStatistics(documentFileUpload);
			}	
		}catch (Exception e) {
			System.out.println("Exception in file import: " + e.getMessage());	
			e.printStackTrace();
			return new ResponseEntity("Exception thrown - " +
					 documentFileUpload.getDocumentTypeId(), new HttpHeaders(), HttpStatus.OK);
			
		}	
		 return new ResponseEntity("Successfully uploaded - " +
				 documentFileUpload.getDocumentTypeId(), new HttpHeaders(), HttpStatus.OK);
	}
	
	public void documentUploadProvider(
			final DocumentUpload documentFileUpload) throws InvalidFormatException, EncryptedDocumentException, IOException {
		
		int totalNumberOfRows = 0;
		int totalProRowsCreatedOrUpdated = 0;
		ArrayList<Object> returnObjects = null;		
		
			
			if (documentFileUpload.getProvider() != null) {
				
				Workbook providersFileWorkbook = WorkbookFactory.create(documentFileUpload.getProvider().getInputStream());
				Sheet providersFileSheet = providersFileWorkbook.getSheetAt(0);
				Iterator<Row> providersFileRowIterator = providersFileSheet.rowIterator();
                int providersFileRowCount = providersFileSheet.getPhysicalNumberOfRows();
				totalNumberOfRows = providersFileRowCount - 1;
				String stringResult = "";		
				
				

				while (providersFileRowIterator.hasNext()) 
				{
					Row providersFileRow = (Row) providersFileRowIterator.next();
					
					returnObjects = new ArrayList<Object>();
					
					if (providersFileRow.getRowNum() > 0 && providersFileRow.getRowNum() <= providersFileRowCount)
					{
						System.out.println("ROW - " + providersFileRow.getRowNum());
						Iterator<Cell> iterator = providersFileRow.cellIterator();
						ProviderHypothesis provider = new ProviderHypothesis();
						
						
						while (iterator.hasNext()) 
						{
							Cell hssfCell = (Cell) iterator.next();
							int cellIndex = hssfCell.getColumnIndex();
							
							switch (cellIndex) 
							{
							
							case 1:
								switch (hssfCell.getCellType())
								{
								
				                case Cell.CELL_TYPE_STRING:					                	
				                    stringResult=hssfCell.getStringCellValue();
				                    provider.setYearLookup(yearLookUpService.findByYearName(stringResult));
				                    System.out.println("Year name: " + stringResult);
				                  
				                    break;
								
								}
								break;								
							case 2:
								switch (hssfCell.getCellType())
								{
								
				                case Cell.CELL_TYPE_STRING:	
				                	
				                    stringResult=hssfCell.getStringCellValue();
				                    provider.setReportingOptionLookup(reportingOptionLookUpService.findByReportingOptionName(stringResult));
				                    break;	
								
								}
								break;
	
							case 3:
								switch (hssfCell.getCellType()) 
								{
								
				                case Cell.CELL_TYPE_STRING:	
				                	
				                    stringResult=hssfCell.getStringCellValue();
				                    provider.setParameterLookup(parameterLookUpService.findByParameterName(stringResult));				                   
				                    break;
								
								}
								break;
							case 4:
								switch (hssfCell.getCellType())
								{
								
				                case Cell.CELL_TYPE_NUMERIC:	
				                    provider.setYesValue((int)hssfCell.getNumericCellValue());
				                    break;
								
								}
								break;
							case 5:
								switch (hssfCell.getCellType())
								{
								
				                case Cell.CELL_TYPE_NUMERIC:	
				                    provider.setNoValue((int)hssfCell.getNumericCellValue());
				                    break;
								
								}
								break;
							case 6:
								switch (hssfCell.getCellType())
								{
								
				                case Cell.CELL_TYPE_NUMERIC:	
				                	provider.setYesCount(BigInteger.valueOf((int)hssfCell.getNumericCellValue()));				                   
				                    break;								
								}
								break;
							case 7:
								switch (hssfCell.getCellType())
								{
								
				                case Cell.CELL_TYPE_NUMERIC:	
				                	provider.setNoCount(BigInteger.valueOf((int)hssfCell.getNumericCellValue()));
				                    break;								
								}
								break;
							case 8:
								switch (hssfCell.getCellType())
								{
								
				                case Cell.CELL_TYPE_NUMERIC:	
				                	provider.setYesPercent(hssfCell.getNumericCellValue());
				                    break;								
								}
								break;
							case 9:
								switch (hssfCell.getCellType())
								{
								
				                case Cell.CELL_TYPE_NUMERIC:	
				                	provider.setNoPercent(hssfCell.getNumericCellValue());
				                    break;								
								}
								break;
							case 10:
								switch (hssfCell.getCellType())
								{
								
				                case Cell.CELL_TYPE_NUMERIC:	
				                	provider.setTotalSum(BigInteger.valueOf((int)hssfCell.getNumericCellValue()));				                					                    
				                    break;								
								}
								break;
							case 11:
								switch (hssfCell.getCellType())
								{
								
				                case Cell.CELL_TYPE_NUMERIC:	
				                	provider.setRpPercent(hssfCell.getNumericCellValue());
				                	providerHypothesisService.create(provider);				                    
				                    break;								
								}
								break;		
							}


						}
						
						
					}
 
				}
			}			

	}
	
	
	public void stateWiseStatistics(
			final DocumentUpload documentFileUpload) throws InvalidFormatException, EncryptedDocumentException, IOException {
		int totalNumberOfRows = 0;
		int totalProRowsCreatedOrUpdated = 0;
		ArrayList<Object> returnObjects = null;		
		
			
			if (documentFileUpload.getStatewise() != null) {
				
				Workbook stateStatFileWorkbook = WorkbookFactory.create(documentFileUpload.getStatewise().getInputStream());
				Sheet stateStatFileSheet = stateStatFileWorkbook.getSheetAt(0);
				Iterator<Row> stateStatFileRowIterator = stateStatFileSheet.rowIterator();
                int stateStatFileRowCount = stateStatFileSheet.getPhysicalNumberOfRows();
				totalNumberOfRows = stateStatFileRowCount - 1;
				String stringResult = "";	

				while (stateStatFileRowIterator.hasNext()) 
				{
					Row stateStatFileRow = (Row) stateStatFileRowIterator.next();
					
					returnObjects = new ArrayList<Object>();
					
					if (stateStatFileRow.getRowNum() > 0 && stateStatFileRow.getRowNum() <= stateStatFileRowCount)
					{
						System.out.println("ROW - " + stateStatFileRow.getRowNum());
						Iterator<Cell> iterator = stateStatFileRow.cellIterator();
						StatewiseStatistic statewiseStatistic = new StatewiseStatistic();
						
						
						while (iterator.hasNext()) 
						{
							Cell hssfCell = (Cell) iterator.next();
							int cellIndex = hssfCell.getColumnIndex();
							
							switch (cellIndex) 
							{
							
							case 0:
								switch (hssfCell.getCellType())
								{
								
				                case Cell.CELL_TYPE_STRING:					                	
				                    stringResult=hssfCell.getStringCellValue();
				                    statewiseStatistic.setState(stringResult); 
				                    System.out.println("State: " + stringResult);
				                  
				                    break;
								
								}
								break;								
							case 1:
								switch (hssfCell.getCellType())
								{
								
				                case Cell.CELL_TYPE_STRING:
				                	stringResult=hssfCell.getStringCellValue();
				                	statewiseStatistic.setYearLookup(yearLookUpService.findByYearName(stringResult));				                   
				                    break;	
								
								}
								break;
	
							case 2:
								switch (hssfCell.getCellType()) 
								{
								
								case Cell.CELL_TYPE_NUMERIC:
				                    statewiseStatistic.setEpOrGpro((int)hssfCell.getNumericCellValue());
				                    break;
								
								}
								break;
							case 3:
								switch (hssfCell.getCellType())
								{
								
								case Cell.CELL_TYPE_NUMERIC:
				                    statewiseStatistic.setRuralUrban((int)hssfCell.getNumericCellValue());
				                    break;
								
								}
								break;
							case 4:
								switch (hssfCell.getCellType())
								{
								
								case Cell.CELL_TYPE_NUMERIC:
				                    statewiseStatistic.setYesOrNooption((int)hssfCell.getNumericCellValue());
				                    break;
								
								}
								break;
							case 5:
								switch (hssfCell.getCellType())
								{
								
								case Cell.CELL_TYPE_STRING:
									stringResult=hssfCell.getStringCellValue();									
				                    statewiseStatistic.setReportingOptionLookup(reportingOptionLookUpService.findByReportingOptionName(stringResult));
				                    break;								
								}
								break;
							case 6:
								switch (hssfCell.getCellType())
								{
								
								case Cell.CELL_TYPE_NUMERIC:
									 System.out.println("start");
				                    statewiseStatistic.setCount(BigInteger.valueOf((int)hssfCell.getNumericCellValue()));
				                    System.out.println("Count" + hssfCell.getNumericCellValue());
				                    statewiseStatisticService.create(statewiseStatistic);
				                    System.out.println("stop");
				                    break;								
								}
								break;
							default:
								break;
							
							}


						}
						
						
					}
 
				}

			}			

	}
	
	
	public void specialtyDocUpload(
			final DocumentUpload documentFileUpload) throws InvalidFormatException, EncryptedDocumentException, IOException {
		int totalNumberOfRows = 0;
		int totalProRowsCreatedOrUpdated = 0;
		ArrayList<Object> returnObjects = null;	
		
			
			if (documentFileUpload.getSpecialty() != null) {
				
				Workbook specialtyFileWorkbook = WorkbookFactory.create(documentFileUpload.getSpecialty().getInputStream());
				Sheet specialtyFileSheet = specialtyFileWorkbook.getSheetAt(0);
				Iterator<Row> specialtyFileRowIterator = specialtyFileSheet.rowIterator();
                int specialtyFileRowCount = specialtyFileSheet.getPhysicalNumberOfRows();
				totalNumberOfRows = specialtyFileRowCount - 1;
				String stringResult = "";

				while (specialtyFileRowIterator.hasNext()) 
				{
					Row specialtyFileRow = (Row) specialtyFileRowIterator.next();
					
					returnObjects = new ArrayList<Object>();
					
					if (specialtyFileRow.getRowNum() > 0 && specialtyFileRow.getRowNum() <= specialtyFileRowCount)
					{
						System.out.println("ROW - " + specialtyFileRow.getRowNum());
						Iterator<Cell> iterator = specialtyFileRow.cellIterator();
						Speciality specialty = new Speciality();						
						
						while (iterator.hasNext()) 
						{
							Cell hssfCell = (Cell) iterator.next();
							int cellIndex = hssfCell.getColumnIndex();
							
							switch (cellIndex) 
							{
							
							case 1:
								switch (hssfCell.getCellType())
								{
								
				                case Cell.CELL_TYPE_STRING:					                	
				                    stringResult=hssfCell.getStringCellValue();
				                    specialty.setPrimarySpeciality(stringResult);
				                    System.out.println("Primary Spec: " + stringResult);
				                  
				                    break;
								
								}
								break;								
							case 2:
								switch (hssfCell.getCellType())
								{
								
				                case Cell.CELL_TYPE_NUMERIC:
				                	specialty.setCount(BigInteger.valueOf((int)hssfCell.getNumericCellValue()));
				                    break;	
								
								}
								break;
	
							case 3:
								switch (hssfCell.getCellType()) 
								{
								
								case Cell.CELL_TYPE_NUMERIC:
									specialty.setPercent(hssfCell.getNumericCellValue());
				                    break;
								
								}
								break;
							case 4:
								switch (hssfCell.getCellType())
								{
								
								case Cell.CELL_TYPE_STRING:
									stringResult=hssfCell.getStringCellValue();										
									specialty.setYearLookup(yearLookUpService.findByYearName(stringResult));									
				                    break;
								
								}
								break;							
							case 5:
								switch (hssfCell.getCellType())
								{								
								case Cell.CELL_TYPE_STRING:									
									stringResult=hssfCell.getStringCellValue();									
									specialty.setReportingOptionLookup(reportingOptionLookUpService.findByReportingOptionName(stringResult));
									specialtyService.create(specialty);
				                    break;								
								}
								break;
							default:
								break;
							
							}


						}
						
						
					}
 
				}

			}			
	}


}
