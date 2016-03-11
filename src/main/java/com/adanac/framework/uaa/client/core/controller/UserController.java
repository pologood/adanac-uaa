package com.adanac.framework.uaa.client.core.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adanac.framework.page.QueryResult;
import com.adanac.framework.uaa.client.core.entity.User;
import com.adanac.framework.uaa.client.core.service.UserManager;
import com.adanac.framework.uaa.client.core.web.PagerBean;
import com.adanac.framework.uaa.client.core.web.QueryParamExt;
import com.adanac.framework.utils.MD5;
import com.adanac.framework.web.controller.BaseController;
import com.adanac.framework.web.controller.BaseResult;
import com.alibaba.fastjson.JSON;

/**
 * 用户列表页面控制器
 * @author adanac
 * @version 1.0
 */
@Controller("uaaUsercontroller")
@RequestMapping("/uaa")
public class UserController extends BaseController {
	@Autowired
	private UserManager userManager;

	/**
	 * 展示某个部门下的用户.
	 *
	 * @param model
	 * @param pagerBean
	 * @param request
	 * @return
	 */
	@RequestMapping("/userListOnDepartment")
	public String userLists(Model model, @ModelAttribute PagerBean<Map<String, Object>> pagerBean,
			HttpServletRequest request) {
		String departmentId = request.getParameter("departmentId");
		model.addAttribute("departmentId", departmentId);
		QueryParamExt<Map<String, Object>> pager = pagerBean.getPager();
		// 封装查询参数 开始
		Map<String, Object> authorizationMap = new HashMap<String, Object>();
		authorizationMap.put(pager.getProperty(), pager.getKeyword());
		authorizationMap.put("departmentId", departmentId);
		// 封装查询参数 结束
		pager.setQueryParam(authorizationMap);
		QueryResult<User> dataList = null;
		if (StringUtils.isNotBlank(departmentId)) {
			// 从数据库中查询用户的相关信息
			dataList = userManager.getUsersByPage(pager);
		}
		if (dataList != null) {
			model.addAttribute("dataList", dataList);
		} else {
			model.addAttribute("dataList", new QueryResult<User>());
		}
		model.addAttribute("pager", pager);
		return "user/user_list.ftl";
	}

	/**
	 * 判断用户是否已经存在.
	 * @param userId  用户ID
	 * @param request
	 * @return 存在返回true，不存在返回false.
	 */
	@RequestMapping("/isExistUser")
	public String isExistUser(@RequestParam("userId") String userId, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(userId)) {
			return ajaxJsonErrorMessage(response, "用户字段缺失");
		}
		User queryUser = new User();
		queryUser.setUserId(userId);
		List<User> userList = userManager.queryUser(queryUser);
		if (userList == null || userList.isEmpty()) {
			return ajaxJsonSuccessMessage(response, "false");
		}
		return ajaxJsonSuccessMessage(response, "true");
	}

	/**
	 * 新增用户.
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addUser")
	public String addUser(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response) {
		if (user.getUserId() == null || StringUtils.isEmpty(user.getPassword()) || user.getDepartmentId() == null
				|| user.getNickName() == null) {
			return ajaxJsonErrorMessage(response, "用户字段缺失");
		}
		user.setPassword(MD5.encode(user.getPassword()));
		Boolean addedFlag = userManager.addUser(user);
		if (addedFlag) {
			return ajaxJsonSuccessMessage(response, "true");
		}
		return ajaxJsonErrorMessage(response, "添加用户失败");
	}

	/**
	 * 删除用户.
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/deleteUser")
	public String deleteUser(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response) {
		if (user.getId() == null) {
			return ajaxJsonErrorMessage(response, "用户字段缺失");
		}
		/**查询用户信息 开始*/
		User queryUser = new User();
		queryUser.setId(user.getId());
		List<User> userList = userManager.queryUser(queryUser)
		/**查询用户信息 结束*/
		;
		User deleteUser = userList.get(0);
		Boolean deletedFlag = userManager.deleteUser(deleteUser);
		if (deletedFlag) {
			return ajaxJsonSuccessMessage(response, "true");
		}
		return ajaxJsonSuccessMessage(response, "false");
	}

	/**
	 * 编辑用户.
	 * @param request
	 * @param response
	 * @return 编辑成功 返回true， 编辑失败 返回 false
	 */
	@RequestMapping("/editUser")
	public String editUser(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response) {
		if (user.getUserId() == null || user.getDepartmentId() == null || user.getNickName() == null) {
			return ajaxJsonErrorMessage(response, "用户字段缺失");
		}
		/**查询用户信息 开始*/
		User queryUser = new User();
		queryUser.setUserId(user.getUserId());
		List<User> userList = userManager.queryUser(queryUser)
		/**查询用户信息 结束*/
		;
		User editUser = userList.get(0);
		editUser.setNickName(user.getNickName());
		Boolean editedFlag = userManager.editUser(editUser);
		if (editedFlag) {
			return ajaxJsonSuccessMessage(response, "true");
		}
		return ajaxJsonSuccessMessage(response, "false");
	}

	/**
	 * 编辑用户.
	 * @param request
	 * @param response
	 * @return 编辑成功 返回true， 编辑失败 返回 false
	 */
	@RequestMapping("/resetPassword")
	public String resetPassword(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response) {
		if (user.getUserId() == null || StringUtils.isEmpty(user.getPassword())) {
			return ajaxJsonErrorMessage(response, "用户字段缺失");
		}
		/**查询用户信息 开始*/
		User queryUser = new User();
		queryUser.setUserId(user.getUserId());
		List<User> userList = userManager.queryUser(queryUser)
		/**查询用户信息 结束*/
		;
		User editUser = userList.get(0);
		editUser.setPassword(MD5.encode(user.getPassword()));
		Boolean editedFlag = userManager.editUser(editUser);
		if (editedFlag) {
			return ajaxJsonSuccessMessage(response, "true");
		}
		return ajaxJsonSuccessMessage(response, "false");
	}

	@RequestMapping("/user_role_page")
	public String userRolePage(String userIdentity, String identityType, String property, String keyword,
			HttpServletRequest request, Model model) {
		model.addAttribute("userIdentity", userIdentity);
		model.addAttribute("identityType", identityType);
		model.addAttribute("property", property);
		model.addAttribute("keyword", keyword);
		return "user/user_role_Page.ftl";
	}

	@RequestMapping("/user_login")
	public void userLogin(HttpServletRequest request, HttpServletResponse response, String userName, String password) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		BaseResult br = userManager.login(userName, password);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(JSON.toJSONString(br));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/get_user")
	public void getUser(HttpServletRequest request, HttpServletResponse response, String userName) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		User queryUser = new User();
		queryUser.setUserId(userName);
		List<User> users = userManager.queryUser(queryUser);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if (CollectionUtils.isNotEmpty(users)) {
				out.write(JSON.toJSONString(users.get(0)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
