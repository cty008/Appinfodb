package dao.impl;

import dao.SysEmployeeDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import pojo.SysEmployee;

import java.util.List;

public class SysEmployeeDaoImpl extends HibernateDaoSupport implements SysEmployeeDao {

    @Override
    public List<SysEmployee> login(SysEmployee sysEmployee) {
        return this.getHibernateTemplate().find("from SysEmployee as emp where emp.sn = '"+sysEmployee.getSn()+"' and emp.password = '"+sysEmployee.getPassword()+"' ");
    }



}
