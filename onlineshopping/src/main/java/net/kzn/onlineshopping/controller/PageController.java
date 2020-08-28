package net.kzn.onlineshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.kzn.onlineshopping.exception.ProductNotFoundException;
import net.kzn.shoppingbackend.dao.CategoryDAO;
import net.kzn.shoppingbackend.dao.ProductDAO;
import net.kzn.shoppingbackend.dto.Category;
import net.kzn.shoppingbackend.dto.Product;

@Controller
public class PageController {
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping(value= {"/", "/home","/index"})
	public ModelAndView index()
	{
		ModelAndView mv= new ModelAndView("page");
		mv.addObject("title","Home");
		mv.addObject("userClickHome",true);
		mv.addObject("categories", categoryDAO.list());
		return mv;
	}
	
	@RequestMapping("/about")
	public ModelAndView about()
	{
		ModelAndView mv= new ModelAndView("page");
		mv.addObject("title","About Us");
		mv.addObject("userClickAbout",true);
		return mv;
	}
	
	@RequestMapping("/contact")
	public ModelAndView contact()
	{
		ModelAndView mv= new ModelAndView("page");
		mv.addObject("title","Contact Us");
		mv.addObject("userClickContact",true);
		return mv;
	}
	
	@RequestMapping("/show/all/products")
	public ModelAndView showAllProducts()
	{
		ModelAndView mv= new ModelAndView("page");
		mv.addObject("title","All Products");
		mv.addObject("userClickAllProducts",true);
		mv.addObject("categories", categoryDAO.list());
		return mv;
	}
	
	@RequestMapping("/show/category/{id}/products")
	public ModelAndView showCategoryProducts(@PathVariable("id") Integer id)
	{
		ModelAndView mv= new ModelAndView("page");
		Category category=null;
		category=categoryDAO.get(id);
		mv.addObject("title",category.getName());
		mv.addObject("userClickCategoryProducts",true);
		mv.addObject("categories", categoryDAO.list());
		mv.addObject("category", category);
		return mv;
	}
	
	/*
	 * Viewing a single product
	 * */
	
	@RequestMapping(value = "/show/{id}/product") 
	public ModelAndView showSingleProduct(@PathVariable int id) throws ProductNotFoundException {
		
		ModelAndView mv = new ModelAndView("page");
		
		Product product = productDAO.get(id);
		
		if(product == null) throw new ProductNotFoundException();
		
		// update the view count
		product.setViews(product.getViews() + 1);
		productDAO.update(product);
		//---------------------------
		
		mv.addObject("title", product.getName());
		mv.addObject("product", product);
		
		mv.addObject("userClickShowProduct", true);
		
		
		return mv;
		
	}
	
	
//	@RequestMapping(value= {"/test"})
//	public ModelAndView test(@RequestParam("greeting") String greeting)
//	{
//		ModelAndView mv= new ModelAndView("page");
//		mv.addObject("greeting",greeting);
//		return mv;
//	}
	@RequestMapping(value= {"/test/{greeting}"})
	public ModelAndView test(@PathVariable("greeting") String greeting)
	{
		ModelAndView mv= new ModelAndView("page");
		mv.addObject("greeting",greeting);
		return mv;
	}
	
}
