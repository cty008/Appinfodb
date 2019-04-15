package test;

import dao.impl.BizClaimVoucherDaoImpl;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.BizClaimVoucher;
import pojo.BizClaimVoucherDetail;
import service.BizClaimVoucherService;
import util.HibernateUtil;

public class Test1 {
    public static void main(String[] args) throws Exception {

//        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
//         SysEmployeeService s= (SysEmployeeService)ctx.getBean("sysEmployeeService");
//            SysEmployee sysEmployee=new SysEmployee();
//            sysEmployee.setSn("A1");
//            sysEmployee.setPassword("123");
//            SysEmployee emp = s.loginService(sysEmployee);
//        System.out.println(emp.getName());
//        System.out.println(emp.getSn());

//        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
//        BizClaimVoucherService b= (BizClaimVoucherService)ctx.getBean("bizClaimVoucherService");
//
//        BizClaimVoucher biz=new BizClaimVoucher();
//        BizCheckResult result=new BizCheckResult();
//        biz.setCreateTime(Tool.strToDate("2019-3-1","yyyy-mm-dd"));
//        result.setCheckTime(Tool.strToDate("2019-3-30","yyyy-mm-dd"));
//        biz.setStatus("全部");
//
//        List<BizClaimVoucher> ss = b.findByConditionListService(biz,result);
//        for (BizClaimVoucher aa:ss){
//            System.out.println("getStatus="+aa.getStatus());
//            System.out.println("CreateTime="+aa.getCreateTime().toString());
//        }

//        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
//        BizClaimVoucherService b= (BizClaimVoucherService)ctx.getBean("bizClaimVoucherService");
//
//        BizClaimVoucher biz = b.getBizClaimVoucherInfoById(1);
//
//        Collection<BizCheckResult> bizCheckResultsById = biz.getBizCheckResultsById();
//        for (BizCheckResult aa: bizCheckResultsById){
//            System.out.println(aa.getSysEmployeeByCheckerSn().getName());
//            System.out.println(aa.getSysEmployeeByCheckerSn().getSysPositionByPositionId().getNameCn());
//        }



    }
}
