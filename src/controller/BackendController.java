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
	
	// ��ת����½ҳ��
	@RequestMapping(value = "/login")
	public String Login() {
		System.out.println("LoginBack");
		return "backendlogin";
	}

	// ��½�ж�
	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public String doLogin(@RequestParam String userCode, @RequestParam String userPassword, HttpSession session,
			HttpServletRequest request) {
		
		request.getSession().setAttribute("isLogin", userCode );  //���ص�½
		
		BackendUser loginBack = backendService.LoginBack(userCode, userPassword);
		if (loginBack != null) {
			session.setAttribute(Constants.userSession, loginBack);
			return "redirect:/manager/main";
		} else {
			request.setAttribute("error", "�û��������벻��ȷ");
			return "backendlogin";
		}
	}

	// �ж�session���Ƿ�����û�
	@RequestMapping(value = "/main")
	public String main(HttpSession session) {
		System.out.println("main");
		if (session.getAttribute(Constants.userSession) == null) {
			return "redirect:/manager/login";
		}
		return "backend/main";
	}

	// ע��
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
			System.out.println("logout");
			session.removeAttribute(Constants.userSession);
			return "backendlogin";
		}

	// ��ת��ѯ�б���
	@RequestMapping(value = "/backend/app/list")
	public String list() {
		System.out.println("��ת��ѯ�б���");
		return "redirect:/manager/BackInfo";
	}
	
	// ��ѯ�б�
	@RequestMapping(value = "/BackInfo")
	public String _appInfolist(String querySoftwareName, String queryFlatformId,
			String queryCategoryLevel1, String queryCategoryLevel2, String queryCategoryLevel3,
			String pageIndex,
			HttpSession session, AppInfo appinfo, Model model) {
		System.out.println("��ѯ�б�");
		
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

		int currentPageNo = 1;// ��ǰҳ
		if (pageIndex != null) {// �ж���ҳ����Ϊ��
			currentPageNo = Integer.valueOf(pageIndex);
		}
		int totalCount = backendService.countPage(appinfo);// ����ʵ��������ѯ��¼����
		// ������ҳ��
		int totalPageCount = totalCount % Constants.pageSize == 0 ? (totalCount / Constants.pageSize): (totalCount / Constants.pageSize + 1);
		appinfo.setPageSize(Constants.pageSize); // ÿҳ��ʾ������
		appinfo.setCurrPage((currentPageNo - 1) * Constants.pageSize); // ��ǰҳ=��ǰҳ-1*ÿҳ��ʾ������

		model.addAttribute("totalPageCount", totalPageCount);// ��ȡ�����ҳ��
		model.addAttribute("totalCount", totalCount);// ���¼����
		model.addAttribute("currentPageNo", currentPageNo);// ��ǰҳ

		model.addAttribute("flatFormList", backendService.getflatformId());// ����ƽ̨������
		model.addAttribute("categoryLevel1List", backendService.getcategoryLevel1());// һ������������
		model.addAttribute("querySoftwareName", querySoftwareName);// �������
		model.addAttribute("queryFlatformId", queryFlatformId);// ����ƽ̨
		if (queryCategoryLevel1!=null) {
			model.addAttribute("queryCategoryLevel1",queryCategoryLevel1);//һ������
		}
		if (queryCategoryLevel2!=null) {
			model.addAttribute("categoryLevel2List",backendService.getChildrenByParentId(Integer.parseInt(queryCategoryLevel1)));//��������
		}
		if (queryCategoryLevel3!=null) {
			model.addAttribute("categoryLevel3List",backendService.getChildrenByParentId(Integer.parseInt(queryCategoryLevel2)));//��������
		}
		model.addAttribute("appInfoList", backendService.BackInfo(appinfo)); // ת��ҳ�� ��ѯ�������浽��������
		
		return "backend/applist";
	}

	//�첽��ѯ(ajax�첽)  ����������(���JSON��������)
	@RequestMapping(value = "/categorylevellist.json", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
	@ResponseBody
	public Object categorylevellist(String pid) {
		System.out.println("�첽��ѯ ����������");
		String jsonString = "";
		if (pid != null && pid != "") { // ����һ�������ѯ����������
			jsonString = JSONArray.toJSONString(backendService.getChildrenByParentId(Integer.valueOf(pid)));
		} else { // һ������
			jsonString = JSONArray.toJSONString(backendService.getcategoryLevel1());
		}
		System.out.println(jsonString);
		return jsonString;

	}

	//��ת�����Ϣҳ��
	@RequestMapping(value="/check")
	public String check(String aid,String vid,
			Model model) {
		System.out.println("�����Ϣ");
		model.addAttribute("appInfo",backendService.appview(Integer.parseInt(aid))); //APP������Ϣ
		model.addAttribute("appVersion",backendService.UpVersionInfo(Integer.parseInt(vid)));//���°汾��Ϣ
		return "backend/appcheck";
	}

	//�����Ϣ(�޸�״̬)
	@RequestMapping(value="/checksave",method=RequestMethod.POST)
	public String checksave(String id,String status) {
		System.out.println("�����Ϣ(�޸�״̬)");
		int updateInfo = backendService.UpdateInfo(Integer.parseInt(status), Integer.parseInt(id));
		if (updateInfo>0) {
			return "redirect:/manager/BackInfo"; //�޸ĳɹ�
		}
		return "/manager/check";//�޸�ʧ��
	}
}
