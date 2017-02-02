package com.alienlab.wa17.entity.client;

import javax.persistence.*;

/**
 * Created by juhuiguang on 2017/2/1.
 */
@Entity
@Table(name = "tb_sizetype", schema = "17wa_main", catalog = "")
public class ClientTbSizetype {
    private long sizetypeId;
    private String sizetypeName;
    private String sizetypeStatus;

    @Id
    @Column(name = "sizetype_id")
    public long getSizetypeId() {
        return sizetypeId;
    }

    public void setSizetypeId(long sizetypeId) {
        this.sizetypeId = sizetypeId;
    }

    @Basic
    @Column(name = "sizetype_name")
    public String getSizetypeName() {
        return sizetypeName;
    }

    public void setSizetypeName(String sizetypeName) {
        this.sizetypeName = sizetypeName;
    }

    @Basic
    @Column(name = "sizetype_status")
    public String getSizetypeStatus() {
        return sizetypeStatus;
    }

    public void setSizetypeStatus(String sizetypeStatus) {
        this.sizetypeStatus = sizetypeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbSizetype that = (ClientTbSizetype) o;

        if (sizetypeId != that.sizetypeId) return false;
        if (sizetypeName != null ? !sizetypeName.equals(that.sizetypeName) : that.sizetypeName != null) return false;
        if (sizetypeStatus != null ? !sizetypeStatus.equals(that.sizetypeStatus) : that.sizetypeStatus != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (sizetypeId ^ (sizetypeId >>> 32));
        result = 31 * result + (sizetypeName != null ? sizetypeName.hashCode() : 0);
        result = 31 * result + (sizetypeStatus != null ? sizetypeStatus.hashCode() : 0);
        return result;
    }
}
