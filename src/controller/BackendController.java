package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import pojo.AppInfo;
import pojo.BackendUser;
import service.backend.BackendService;
import tools.Constants;

@Controller
@RequestMapping(value="/manager")
public class BackendController {

	@Autowired
	private BackendService backendService;
	
	// 跳转到登陆页面
	@RequestMapping(value = "/login")
	public String Login() {
		System.out.println("LoginBack");
		return "backendlogin";
	}

	// 登陆判断
	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public String doLogin(@RequestParam String userCode, @RequestParam String userPassword, HttpSession session,
			HttpServletRequest request) {
		
		request.getSession().setAttribute("isLogin", userCode );  //拦截登陆
		
		BackendUser loginBack = backendService.LoginBack(userCode, userPassword);
		if (loginBack != null) {
			session.setAttribute(Constants.userSession, loginBack);
			return "redirect:/manager/main";
		} else {
			request.setAttribute("error", "用户名或密码不正确");
			return "backendlogin";
		}
	}

	// 判断session里是否存有用户
	@RequestMapping(value = "/main")
	public String main(HttpSession session) {
		System.out.println("main");
		if (session.getAttribute(Constants.userSession) == null) {
			return "redirect:/manager/login";
		}
		return "backend/main";
	}

	// 注销
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
			System.out.println("logout");
			session.removeAttribute(Constants.userSession);
			return "backendlogin";
		}

	// 跳转查询列表方法
	@RequestMapping(value = "/backend/app/list")
	public String list() {
		System.out.println("跳转查询列表方法");
		return "redirect:/manager/BackInfo";
	}
	
	// 查询列表
	@RequestMapping(value = "/BackInfo")
	public String _appInfolist(String querySoftwareName, String queryFlatformId,
			String queryCategoryLevel1, String queryCategoryLevel2, String queryCategoryLevel3,
			String pageIndex,
			HttpSession session, AppInfo appinfo, Model model) {
		System.out.println("查询列表");
		
		if (querySoftwareName != null) {
			appinfo.setSoftwareName(querySoftwareName);
		}
		if (queryFlatformId != null && !"".equals(queryFlatformId)) {
			appinfo.setFlatformId(Integer.valueOf(queryFlatformId));
		}
		if (queryCategoryLevel1 != null && !"".equals(queryCategoryLevel1)) {
			appinfo.setCategoryLevel1(Integer.valueOf(queryCategoryLevel1));
		}
		if (queryCategoryLevel2 != null && !"".equals(queryCategoryLevel2)) {
			appinfo.setCategoryLevel2(Integer.valueOf(queryCategoryLevel2));
		}
		if (queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")) {
			appinfo.setCategoryLevel3(Integer.valueOf(queryCategoryLevel3));
		}

		int currentPageNo = 1;// 当前页
		if (pageIndex != null) {// 判断首页不能为空
			currentPageNo = Integer.valueOf(pageIndex);
		}
		int totalCount = backendService.countPage(appinfo);// 根据实体类对象查询记录条数
		// 计算总页数
		int totalPageCount = totalCount % Constants.pageSize == 0 ? (totalCount / Constants.pageSize): (totalCount / Constants.pageSize + 1);
		appinfo.setPageSize(Constants.pageSize); // 每页显示的数量
		appinfo.setCurrPage((currentPageNo - 1) * Constants.pageSize); // 当前页=当前页-1*每页显示的数量

		model.addAttribute("totalPageCount", totalPageCount);// 获取表的总页数
		model.addAttribute("totalCount", totalCount);// 表记录条数
		model.addAttribute("currentPageNo", currentPageNo);// 当前页

		model.addAttribute("flatFormList", backendService.getflatformId());// 所属平台下拉框
		model.addAttribute("categoryLevel1List", backendService.getcategoryLevel1());// 一级分类下拉框
		model.addAttribute("querySoftwareName", querySoftwareName);// 软件名称
		model.addAttribute("queryFlatformId", queryFlatformId);// 所属平台
		if (queryCategoryLevel1!=null) {
			model.addAttribute("queryCategoryLevel1",queryCategoryLevel1);//一级分类
		}
		if (queryCategoryLevel2!=null) {
			model.addAttribute("categoryLevel2List",backendService.getChildrenByParentId(Integer.parseInt(queryCategoryLevel1)));//二级分类
		}
		if (queryCategoryLevel3!=null) {
			model.addAttribute("categoryLevel3List",backendService.getChildrenByParentId(Integer.parseInt(queryCategoryLevel2)));//三级分类
		}
		model.addAttribute("appInfoList", backendService.BackInfo(appinfo)); // 转发页面 查询方法保存到作用域里
		
		return "backend/applist";
	}

	//异步查询(ajax异步)  二三级分类(解决JSON数据乱码)
	@RequestMapping(value = "/categorylevellist.json", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
	@ResponseBody
	public Object categorylevellist(String pid) {
		System.out.println("异步查询 二三级分类");
		String jsonString = "";
		if (pid != null && pid != "") { // 根据一级分类查询二三级分类
			jsonString = JSONArray.toJSONString(backendService.getChildrenByParentId(Integer.valueOf(pid)));
		} else { // 一级分类
			jsonString = JSONArray.toJSONString(backendService.getcategoryLevel1());
		}
		System.out.println(jsonString);
		return jsonString;

	}

	//跳转审核信息页面
	@RequestMapping(value="/check")
	public String check(String aid,String vid,
			Model model) {
		System.out.println("审核信息");
		model.addAttribute("appInfo",backendService.appview(Integer.parseInt(aid))); //APP基础信息
		model.addAttribute("appVersion",backendService.UpVersionInfo(Integer.parseInt(vid)));//最新版本信息
		return "backend/appcheck";
	}

	//审核信息(修改状态)
	@RequestMapping(value="/checksave",method=RequestMethod.POST)
	public String checksave(String id,String status) {
		System.out.println("审核信息(修改状态)");
		int updateInfo = backendService.UpdateInfo(Integer.parseInt(status), Integer.parseInt(id));
		if (updateInfo>0) {
			return "redirect:/manager/BackInfo"; //修改成功
		}
		return "/manager/check";//修改失败
	}
}
