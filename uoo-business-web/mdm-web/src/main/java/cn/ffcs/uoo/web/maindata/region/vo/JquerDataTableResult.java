package cn.ffcs.uoo.web.maindata.region.vo;

public class JquerDataTableResult extends ResponseResult{
    private int draw=1;
    private int recordsTotal;
    private int recordsFiltered;
    public int getDraw() {
        return draw;
    }
    public void setDraw(int draw) {
        this.draw = draw;
    }
    public int getRecordsTotal() {
        return recordsTotal;
    }
    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }
    public int getRecordsFiltered() {
        return recordsFiltered;
    }
    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }
    public JquerDataTableResult(ResponseResult rr) {
        this.recordsFiltered=(int) rr.getTotalRecords(); 
        this.recordsTotal=(int) rr.getTotalRecords();
        this.setData(rr.getData());
        this.setState(rr.getState());
    }
    
}
