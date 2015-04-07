package com.notsocomplex.newcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.notsocomplex.controller.HomePageController;
import com.notsocomplex.core.annotation.OverrideRequestMapping;

/**
 * @author Jasmin Larouche for Not So Complex
 *
 */
@Controller
@RequestMapping("/")
public class NewHomePageController extends HomePageController {
	
	@OverrideRequestMapping
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView  home() {
		return new ModelAndView("newhome");
	}
}
