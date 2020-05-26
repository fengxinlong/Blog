package com.feng.web.admin;
import com.feng.po.User;
import com.feng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {
	@Autowired
	private UserService userService;

	/*
	* 全局路径
	* */
	@GetMapping
	public String loginPage(){
		return "admin/login";
	}
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, HttpSession session
					, RedirectAttributes attributes){

		User user=userService.checkUser(username,password);
		if(user!=null){
			/*
			* 不能把密码传过去
			* */
			user.setPassword(null);
			session.setAttribute("user",user);
			return "admin/index";
		}else {
			/*
			*
			* 错误提醒
			* */
			attributes.addFlashAttribute("message","用户名和密码错误");

			//重定向返回的页面拿不到
//			model.addAttribute("message","用户名和密码错误");

			/*
			* 再次登录返回路径有问题
			* */
			return "redirect:/admin";
		}

	}
	@GetMapping("/logout")
	public String loginout(HttpSession session){
		session.removeAttribute("user");
		return "redirect:/admin";

	}


}
