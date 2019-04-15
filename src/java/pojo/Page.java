package pojo;


public class Page  {

    private Integer pageIndex;    //当前页
    private Integer count;        //总条数
    private Integer pageSize;     //每页显示条数
    private Integer pageAll;      //总页数


    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageAll() {
        return pageAll;
    }

    public void setPageAll(Integer pageAll) {
        this.pageAll = pageAll;
    }
}
