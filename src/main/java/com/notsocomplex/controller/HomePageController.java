package com.notsocomplex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *  @author Jasmin Larouche for Not So Complex
 *
 */
@Controller
@RequestMapping("/")
public class HomePageController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView  home() {
		return new ModelAndView("home");
	}
}
