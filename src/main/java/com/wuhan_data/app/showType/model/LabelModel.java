package com.wuhan_data.app.showType.model;

import java.util.List;
import java.util.Map;

/**
 * @author Kim小根
 * @date 2020/9/24 10:55
 * <p>Description:</p>
 */
public class LabelModel {
    private String labelName;

    private List<ThemeModel> themeList;

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public List<ThemeModel> getThemeList() {
        return themeList;
    }

    public void setThemeList(List<ThemeModel> themeList) {
        this.themeList = themeList;
    }
}
