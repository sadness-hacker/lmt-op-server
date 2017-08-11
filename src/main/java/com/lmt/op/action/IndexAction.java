package com.lmt.op.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
@Controller
@RequestMapping(value={"/","/op/index"})
public class IndexAction {

	@RequestMapping
	public String index(
			HttpServletRequest request,HttpServletResponse response){
		
		return "index";
	}
	
}
