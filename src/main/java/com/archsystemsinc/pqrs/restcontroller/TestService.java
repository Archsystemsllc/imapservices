/**
 * 
 */
package com.archsystemsinc.pqrs.restcontroller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Q
 *
 */
@RestController
@RequestMapping("api")
public class TestService {
	private static final Logger log = Logger.getLogger(TestService.class);
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String testService(){
		log.debug("--> testService");
		log.debug("<-- testService");
		return "[data:21231231]";
	}
}
