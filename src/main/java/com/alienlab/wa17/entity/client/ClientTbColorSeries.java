package com.alienlab.wa17.entity.client;

import javax.persistence.*;

/**
 * Created by juhuiguang on 2017/2/1.
 */
@Entity
@Table(name = "tb_color_series", schema = "17wa_main", catalog = "")
public class ClientTbColorSeries {
    private long seriesId;
    private String seriesName;

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

        ClientTbColorSeries that = (ClientTbColorSeries) o;

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
