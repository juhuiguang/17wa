package com.alienlab.wa17.entity.client;

import javax.persistence.*;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_shop_tags", schema = "17wa_client", catalog = "")
public class ClientTbShopTags {
    private long id;
    private Integer shopId;
    private Integer tagId;
    private Integer tagSort;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "shop_id")
    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    @Basic
    @Column(name = "tag_id")
    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Basic
    @Column(name = "tag_sort")
    public Integer getTagSort() {
        return tagSort;
    }

    public void setTagSort(Integer tagSort) {
        this.tagSort = tagSort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbShopTags that = (ClientTbShopTags) o;

        if (id != that.id) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (tagId != null ? !tagId.equals(that.tagId) : that.tagId != null) return false;
        if (tagSort != null ? !tagSort.equals(that.tagSort) : that.tagSort != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (tagId != null ? tagId.hashCode() : 0);
        result = 31 * result + (tagSort != null ? tagSort.hashCode() : 0);
        return result;
    }
}
