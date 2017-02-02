package com.alienlab.wa17.entity.main;

import javax.persistence.*;

/**
 * Created by juhuiguang on 2017/2/1.
 */
@Entity
@Table(name = "tb_colors", schema = "17wa_main", catalog = "")
public class MainTbColors {
    private long colorId;
    private String colorName;
    private String colorRgb;
    private String colorSeries;
    private Long colorSeriesId;

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
    @Column(name = "color_rgb")
    public String getColorRgb() {
        return colorRgb;
    }

    public void setColorRgb(String colorRgb) {
        this.colorRgb = colorRgb;
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
    @Column(name = "color_series_id")
    public Long getColorSeriesId() {
        return colorSeriesId;
    }

    public void setColorSeriesId(Long colorSeriesId) {
        this.colorSeriesId = colorSeriesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainTbColors that = (MainTbColors) o;

        if (colorId != that.colorId) return false;
        if (colorName != null ? !colorName.equals(that.colorName) : that.colorName != null) return false;
        if (colorRgb != null ? !colorRgb.equals(that.colorRgb) : that.colorRgb != null) return false;
        if (colorSeries != null ? !colorSeries.equals(that.colorSeries) : that.colorSeries != null) return false;
        if (colorSeriesId != null ? !colorSeriesId.equals(that.colorSeriesId) : that.colorSeriesId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (colorId ^ (colorId >>> 32));
        result = 31 * result + (colorName != null ? colorName.hashCode() : 0);
        result = 31 * result + (colorRgb != null ? colorRgb.hashCode() : 0);
        result = 31 * result + (colorSeries != null ? colorSeries.hashCode() : 0);
        result = 31 * result + (colorSeriesId != null ? colorSeriesId.hashCode() : 0);
        return result;
    }
}
