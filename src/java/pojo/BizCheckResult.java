package pojo;

import java.sql.Time;
import java.util.Date;

public class BizCheckResult {
    private int id;
    private Date checkTime;
    private String result;
    private String comm;
    private BizClaimVoucher bizClaimVoucherByClaimId;
    private SysEmployee sysEmployeeByCheckerSn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BizCheckResult that = (BizCheckResult) o;

        if (id != that.id) return false;
        if (checkTime != null ? !checkTime.equals(that.checkTime) : that.checkTime != null) return false;
        if (result != null ? !result.equals(that.result) : that.result != null) return false;
        if (comm != null ? !comm.equals(that.comm) : that.comm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = id;
        result1 = 31 * result1 + (checkTime != null ? checkTime.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (comm != null ? comm.hashCode() : 0);
        return result1;
    }

    public BizClaimVoucher getBizClaimVoucherByClaimId() {
        return bizClaimVoucherByClaimId;
    }

    public void setBizClaimVoucherByClaimId(BizClaimVoucher bizClaimVoucherByClaimId) {
        this.bizClaimVoucherByClaimId = bizClaimVoucherByClaimId;
    }

    public SysEmployee getSysEmployeeByCheckerSn() {
        return sysEmployeeByCheckerSn;
    }

    public void setSysEmployeeByCheckerSn(SysEmployee sysEmployeeByCheckerSn) {
        this.sysEmployeeByCheckerSn = sysEmployeeByCheckerSn;
    }
}
