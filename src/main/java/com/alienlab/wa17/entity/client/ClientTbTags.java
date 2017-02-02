package com.alienlab.wa17.entity.client;

import javax.persistence.*;

/**
 * Created by juhuiguang on 2017/2/1.
 */
@Entity
@Table(name = "tb_tags", schema = "17wa_main", catalog = "")
public class ClientTbTags {
    private long tagId;
    private String tagName;
    private String tagType;

    @Id
    @Column(name = "tag_id")
    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    @Basic
    @Column(name = "tag_name")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Basic
    @Column(name = "tag_type")
    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbTags that = (ClientTbTags) o;

        if (tagId != that.tagId) return false;
        if (tagName != null ? !tagName.equals(that.tagName) : that.tagName != null) return false;
        if (tagType != null ? !tagType.equals(that.tagType) : that.tagType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (tagId ^ (tagId >>> 32));
        result = 31 * result + (tagName != null ? tagName.hashCode() : 0);
        result = 31 * result + (tagType != null ? tagType.hashCode() : 0);
        return result;
    }
}
