package com.alienlab.wa17.entity.main;

import org.hibernate.annotations.ListIndexBase;

import javax.persistence.*;

/**
 * Created by juhuiguang on 2017/2/1.
 */
@Entity
@Table(name = "tb_color_series", schema = "17wa_main", catalog = "")
public class MainTbColorSeries {
    private long seriesId;
    private String seriesName;
    private String seriesRgb;

    @Basic
    @Column(name="series_rgb")
    public String getSeriesRgb() {
        return seriesRgb;
    }

    public void setSeriesRgb(String seriesRgb) {
        this.seriesRgb = seriesRgb;
    }


    @Id
    @Column(name = "series_id")
    public long getSeriesId() {
        return seriesId;
    }



    public void setSeriesId(long seriesId) {
        this.seriesId = seriesId;
    }

    @Basic
    @Column(name = "series_name")
    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainTbColorSeries that = (MainTbColorSeries) o;

        if (seriesId != that.seriesId) return false;
        if (seriesName != null ? !seriesName.equals(that.seriesName) : that.seriesName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (seriesId ^ (seriesId >>> 32));
        result = 31 * result + (seriesName != null ? seriesName.hashCode() : 0);
        return result;
    }
}
