package cn.ffcs.uoo.web.maindata.busipublic.dictionary.dto;


public class DictionaryItemVo {
    /**
     * 字典标识名称
     */
    private String dictionaryName;

    /**
     * 字典中文名称
     */
    private String dictionaryCnname;

    /**
     * 字典项值
     */
    private String itemValue;

    /**
     * 字典中文名称
     */
    private String itemCnname;

    /**
     * 排序
     */
    private Long sort;

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public String getDictionaryCnname() {
        return dictionaryCnname;
    }

    public void setDictionaryCnname(String dictionaryCnname) {
        this.dictionaryCnname = dictionaryCnname;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemCnname() {
        return itemCnname;
    }

    public void setItemCnname(String itemCnname) {
        this.itemCnname = itemCnname;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }
}
