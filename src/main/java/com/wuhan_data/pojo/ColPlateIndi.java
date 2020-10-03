package com.wuhan_data.pojo;

public class ColPlateIndi implements Comparable<ColPlateIndi> {
    int indi_id;
    int plate_id;
    String search_indi_id;
    String indi_new_name;
    String indi_old_name;
    String show_type;
    String show_color;
    int indi_weight;
    int is_show;


    public int getIndi_id() {
        return indi_id;
    }

    public void setIndi_id(int indi_id) {
        this.indi_id = indi_id;
    }

    public int getPlate_id() {
        return plate_id;
    }

    public void setPlate_id(int plate_id) {
        this.plate_id = plate_id;
    }

    public String getShow_type() {
        return show_type;
    }

    public void setShow_type(String show_type) {
        this.show_type = show_type;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public String getSearch_indi_id() {
        return search_indi_id;
    }

    public void setSearch_indi_id(String search_indi_id) {
        this.search_indi_id = search_indi_id;
    }

    public String getIndi_new_name() {
        return indi_new_name;
    }

    public void setIndi_new_name(String indi_new_name) {
        this.indi_new_name = indi_new_name;
    }

    public String getIndi_old_name() {
        return indi_old_name;
    }

    public void setIndi_old_name(String indi_old_name) {
        this.indi_old_name = indi_old_name;
    }

    public String getShow_color() {
        return show_color;
    }

    public void setShow_color(String show_color) {
        this.show_color = show_color;
    }

    public int getIndi_weight() {
        return indi_weight;
    }

    public void setIndi_weight(int indi_weight) {
        this.indi_weight = indi_weight;
    }


    @Override
    public int compareTo(ColPlateIndi o) {
        return this.getIndi_weight() - o.getIndi_weight();
    }
}
