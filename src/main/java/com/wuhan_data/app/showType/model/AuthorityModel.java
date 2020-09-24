package com.wuhan_data.app.showType.model;

import java.util.List;

/**
 * @author Kim小根
 * @date 2020/9/24 10:55
 * <p>Description:</p>
 */
public class AuthorityModel {
    private String typeId;

    private String typeName;

    private List<ThemeModel> labelList;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<ThemeModel> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<ThemeModel> labelList) {
        this.labelList = labelList;
    }
}
