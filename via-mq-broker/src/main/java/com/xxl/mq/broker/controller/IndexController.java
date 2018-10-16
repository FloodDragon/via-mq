package com.xxl.mq.broker.controller;

import com.xxl.mq.broker.controller.annotation.PermessionLimit;
import com.xxl.mq.broker.controller.interceptor.PermissionInterceptor;
import com.xxl.mq.broker.core.result.ReturnT;
import com.xxl.mq.client.rpc.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 * index controller
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
public class IndexController {
	public static final String CONFIG_FILE = "config.properties";

	@RequestMapping("/")
	@PermessionLimit(limit=false)
	public String index(Model model, HttpServletRequest request) {
		if (!PermissionInterceptor.ifLogin(request)) {
			return "redirect:/toLogin";
		}
		return "redirect:/mq";
	}
	
	@RequestMapping("/toLogin")
	@PermessionLimit(limit=false)
	public String toLogin(Model model, HttpServletRequest request) {
		if (PermissionInterceptor.ifLogin(request)) {
			return "redirect:/";
		}
		return "login";
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	@ResponseBody
	@PermessionLimit(limit=false)
	public ReturnT<String> loginDo(HttpServletRequest request, HttpServletResponse response, String userName, String password, String ifRemember){
		if (!PermissionInterceptor.ifLogin(request)) {
			Properties prop = PropertiesUtil.loadProperties(CONFIG_FILE);
			if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)
					&& PropertiesUtil.getString(prop, "login.username").equals(userName)
					&& PropertiesUtil.getString(prop, "login.password").equals(password)) {
				boolean ifRem = false;
				if (StringUtils.isNotBlank(ifRemember) && "on".equals(ifRemember)) {
					ifRem = true;
				}
				PermissionInterceptor.login(response, ifRem);
			} else {
				return new ReturnT<String>(500, "账号或密码错误");
			}
		}
		return ReturnT.SUCCESS;
	}
	
	@RequestMapping(value="logout", method=RequestMethod.POST)
	@ResponseBody
	@PermessionLimit(limit=false)
	public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response){
		if (PermissionInterceptor.ifLogin(request)) {
			PermissionInterceptor.logout(request, response);
		}
		return ReturnT.SUCCESS;
	}
	
	@RequestMapping("/help")
	public String help() {
		return "help";
	}
	
}
