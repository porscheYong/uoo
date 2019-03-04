package cn.ffcs.uoo.core.organization.vo;
/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/10/11
 */
public class TreeNodeVo {

    private String id;
    private Integer state;
    private String pid;
    private String icon;
    private String iconClose;
    private String iconOpen;
    private String name;
    private String open;
    private boolean isParent;
    private String level;
    private String checked;
    private String chkDisabled;

    //标准组织标识
    private String standardFlag;
    private String extField1;
    private String oper; //delete、add
    /**
     * 排序
     */
    private String sort;
    //渠道标识
    private String isChannel;
    //渠道编码
    private String channelNBR;

    public String getIsChannel() {
        return isChannel;
    }

    public void setIsChannel(String isChannel) {
        this.isChannel = isChannel;
    }

    public String getChannelNBR() {
        return channelNBR;
    }

    public void setChannelNBR(String channelNBR) {
        this.channelNBR = channelNBR;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public String getStandardFlag() {
        return standardFlag;
    }

    public void setStandardFlag(String standardFlag) {
        this.standardFlag = standardFlag;
    }

    public String getChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(String chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public String getExtField1() {
        return extField1;
    }

    public void setExtField1(String extField1) {
        this.extField1 = extField1;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconClose() {
        return iconClose;
    }

    public void setIconClose(String iconClose) {
        this.iconClose = iconClose;
    }

    public String getIconOpen() {
        return iconOpen;
    }

    public void setIconOpen(String iconOpen) {
        this.iconOpen = iconOpen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{id:\"");
        sb.append(id);
        sb.append("\",pId:\"");
        sb.append(pid);
        sb.append("\",name:\"");
        sb.append(name);
        sb.append("\",state:");
        sb.append(state);
        sb.append(",icon:\"");
        sb.append(icon);
        sb.append("\",iconClose:\"");
        sb.append(iconClose);
        sb.append("\",iconOpen:\"");
        sb.append(iconOpen);
        sb.append("\",open:\"");
        sb.append(open);
        sb.append("\",isParent:\"");
        sb.append(isParent);
        sb.append("\"}");
        return sb.toString();
    }
}
