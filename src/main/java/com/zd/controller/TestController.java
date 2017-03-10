package com.zd.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("test")
//@SessionAttributes({"fileTree","uu"})  //该注解可以将原本放在request中的属性同样给session一份
public class TestController {
	
	@RequestMapping(value="")
	public ResponseEntity<String> index(HttpSession session, Model model, HttpServletRequest req, HttpServletResponse res,
			@RequestParam("millis") long lastModifiedMillis,
		      //浏览器验证文档内容是否修改时传入的Last-Modified
		    @RequestHeader (value = "If-Modified-Since", required = false) Date ifModifiedSince){
		
//		String id = session.getId();
//		System.out.println("sessionId: " + id);
//		model.addAttribute("name", "zhangDa");
//		
//		ObjectMapper om = new ObjectMapper();
//		try {
//			model.addAttribute("books", om.writeValueAsString(Lists.newArrayList("shuiHu","xiYouJi","hongLouMeng")));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		model.addAttribute("friend", "{\"name\" : \"gouShiQi\", \"age\": 26}");
//		
//		
//		System.out.println(req.getProtocol());
//		System.out.println(req.isSecure());
//		
//		Enumeration<String> headerNames = req.getHeaderNames();
//		while (headerNames.hasMoreElements()) {
//			String name = headerNames.nextElement();
//			System.out.println(name + " : " + req.getHeader(name));
//		}
		
		
		 //当前系统时间
	    long now = System.currentTimeMillis();
	    //文档可以在浏览器端/proxy上缓存多久
	    long maxAge = 20;
	    
		
		//判断内容是否修改了，此处使用等值判断
	    if(ifModifiedSince != null && ifModifiedSince.getTime() == lastModifiedMillis) {
	        return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);
	    }	
	    
	    DateFormat gmtDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
	    String body = "<a href=''>点击访问当前链接</a>";
	    MultiValueMap<String, String> headers = new HttpHeaders();
	    
	    //文档修改时间
	    headers.add("Last-Modified", gmtDateFormat.format(new Date(lastModifiedMillis)));
	    //当前系统时间
	    headers.add("Date", gmtDateFormat.format(new Date(now)));
	    //过期时间 http 1.0支持
	    headers.add("Expires", gmtDateFormat.format(new Date(now + maxAge)));
	    //文档生存时间 http 1.1支持
	    headers.add("Cache-Control", "max-age=" + maxAge);
	    return new ResponseEntity<String>(body, headers, HttpStatus.OK);
	    
		
		//return "test/index";
	}
	
	@RequestMapping(value="test1", method=RequestMethod.GET)
	public String test1(ModelMap map, @RequestParam(required=false) String name, HttpServletRequest request, Model model) throws Exception{
		//  验证@SessionAttributes
		String json = (String) request.getSession().getAttribute("fileTree");
		System.out.println(json);
		System.out.println(request.getParameter("name"));
		System.out.println(request.getAttribute("name"));
		//request.setAttribute("name", name);
		//map.put("name", name);
		//model.addAttribute("name", name);
		//model.addAttribute("fileTree", json);
		
		@SuppressWarnings("unchecked")
		// item是之前方法test4加了注解@ModelAttribute，这样item值会加在modelMap中
		List<String> list = (List<String>) map.get("item");
		System.out.println(list.get(0) + ", " + list.get(1));
		
		// 请求转发到下一个controller, 这时就算在model里面没有设置，在下一个controller里面也能从model里面得到
		//return "forward:/switchSubSystem/test2";
		
		// 重定向到下一个controller
		return "redirect:/test/test3";
	}
	
	// 请求转发
	@RequestMapping(value="test2", method=RequestMethod.GET)
	public String test2(ModelMap map, HttpServletRequest request, 
				@ModelAttribute("fileTree") String fileTree, 
				Model model, 
				@ModelAttribute("name") String name) {
		
		// 加了@ModelAttribute("fileTree")之后，在modelMap里面就有了session里面的fileTree，所以通常和@SessionAttributes一起使用
		// 前提是得先在session里面put一下fileTree, 当然还可以从request中获取值，可以是param也可以是attribute，@ModelAttribute注解就是往model中放置属性，记住，只在放置属性
		System.out.println(fileTree);
		// request里面保留之前设置的值，不设置的话就没有
		System.out.println(request.getParameter("name"));
		System.out.println(request.getAttribute("name"));
		// 请求转发之后，ModelMap里面会保留model或request里面的值，甚至是request里面param的值，不用直接去设置
		System.out.println(map.get("name"));
		System.out.println(model.containsAttribute("name"));
		System.out.println(name);
		
		@SuppressWarnings("unchecked")
		// item是之前方法test4加了注解@ModelAttribute，这样item值会加在modelMap中
		List<String> list = (List<String>) map.get("item");
		System.out.println(list.get(0) + ", " + list.get(1));
		
		return "fileTreeMenu";
	}
	
	@RequestMapping(value="test12", method=RequestMethod.GET)
	public String test12(@RequestParam(required=false) String name, HttpServletRequest request) throws Exception{
		
		String json = (String) request.getSession().getAttribute("fileTree");
		System.out.println(json);
		System.out.println(request.getParameter("name"));
		System.out.println(request.getAttribute("name"));
		request.setAttribute("name", name);
		
		return "redirect:/homePage/test3";
	}
	
	// 重定向，本controller内，加了sessionAttribute之后，不能再重定向了！！！！！！
	@RequestMapping(value="test3", method=RequestMethod.GET)
	public String test3(Model model, HttpServletRequest request, @ModelAttribute("name") String name, 
			@ModelAttribute("fileTree") String fileTree) throws Exception{
		System.out.println(request.getAttribute("name"));
		return "fileTreeMenu";
	}
	
	@ModelAttribute("item") // 该方法的返回值被保存到model里
	public List<String> test4(ModelMap map, HttpServletRequest request) throws Exception {
		List<String> list = Lists.newArrayList("A","B");
		return list;
	}
	
}
