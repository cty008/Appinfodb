package service.impl;

import dao.BizClaimVoucherDao;
import pojo.*;
import service.BizClaimVoucherService;

import java.util.List;

public class BizClaimVoucherServiceImpl implements BizClaimVoucherService {

    private  BizClaimVoucherDao bizClaimVoucherDao;

    @Override
    public List<BizClaimVoucher> findByConditionListService(Page page, Hiredate hiredate, BizClaimVoucher b) {
        List<BizClaimVoucher> byConditionList = bizClaimVoucherDao.findByConditionList(page,hiredate,b);
        if (byConditionList!=null && byConditionList.size()>0){
            return byConditionList;
        }
        return null;
    }

    @Override
    public List<BizClaimVoucher> query(Page page, String sn) {
        return bizClaimVoucherDao.query(page,sn);
    }

    @Override
    public int findCount(String sn) {
        return bizClaimVoucherDao.findCount(sn);
    }

    @Override
    public int findCountByName(Hiredate hiredate) {
        return bizClaimVoucherDao.findCountByName(hiredate);
    }

    @Override
    public int saveService(BizClaimVoucher biz) {
        return bizClaimVoucherDao.save(biz);
    }

    @Override
    public BizClaimVoucher getBizClaimVoucherInfoById(Integer id) {
        return bizClaimVoucherDao.getBizClaimVoucherInfoById(id);
    }

    @Override
    public void delete(Integer id) {
        bizClaimVoucherDao.delete(id);
    }

    @Override
    public void sub(BizClaimVoucher biz) {
        bizClaimVoucherDao.sub(biz);
    }

    @Override
    public void DelDetail() {  bizClaimVoucherDao.DelDetail(); }

    @Override
    public void update(BizClaimVoucher bizClaimVoucher,Integer id,String json) { bizClaimVoucherDao.update(bizClaimVoucher,id,json); }

    @Override
    public void doDetail234(BizClaimVoucher biz) {
        bizClaimVoucherDao.doDetail234(biz);
    }

    @Override
    public void doDetai3(BizCheckResult biz) {
        bizClaimVoucherDao.doDetai3(biz);
    }


    public BizClaimVoucherDao getBizClaimVoucherDao() {
        return bizClaimVoucherDao;
    }
    public void setBizClaimVoucherDao(BizClaimVoucherDao bizClaimVoucherDao) {
        this.bizClaimVoucherDao = bizClaimVoucherDao;
    }
}
