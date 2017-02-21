package com.alienlab.wa17.entity.client;

import javax.persistence.*;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_color_cus", schema = "17wa_client", catalog = "")
public class ClientTbColorCus {
    private long colorId;
    private String colorName;
    private String colorSeries;
    private String colorRgb;

    @Id
    @Column(name = "color_id")
    public long getColorId() {
        return colorId;
    }

    public void setColorId(long colorId) {
        this.colorId = colorId;
    }

    @Basic
    @Column(name = "color_name")
    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Basic
    @Column(name = "color_series")
    public String getColorSeries() {
        return colorSeries;
    }

    public void setColorSeries(String colorSeries) {
        this.colorSeries = colorSeries;
    }

    @Basic
    @Column(name = "color_rgb")
    public String getColorRgb() {
        return colorRgb;
    }

    public void setColorRgb(String colorRgb) {
        this.colorRgb = colorRgb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbColorCus that = (ClientTbColorCus) o;

        if (colorId != that.colorId) return false;
        if (colorName != null ? !colorName.equals(that.colorName) : that.colorName != null) return false;
        if (colorSeries != null ? !colorSeries.equals(that.colorSeries) : that.colorSeries != null) return false;
        if (colorRgb != null ? !colorRgb.equals(that.colorRgb) : that.colorRgb != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (colorId ^ (colorId >>> 32));
        result = 31 * result + (colorName != null ? colorName.hashCode() : 0);
        result = 31 * result + (colorSeries != null ? colorSeries.hashCode() : 0);
        result = 31 * result + (colorRgb != null ? colorRgb.hashCode() : 0);
        return result;
    }
}
