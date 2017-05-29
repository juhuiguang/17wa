package com.alienlab.wa17.entity.main;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by 橘 on 2017/5/20.
 */
public class MainTbInclude {
    private long includeId;
    private String includeName;
    private String includeType;

    @Id
    @Column(name = "include_id")
    public long getIncludeId() {
        return includeId;
    }

    public void setIncludeId(long includeId) {
        this.includeId = includeId;
    }

    @Column(name = "include_name")
    public String getIncludeName() {
        return includeName;
    }

    public void setIncludeName(String includeName) {
        this.includeName = includeName;
    }
    @Column(name = "include_type")
    public String getIncludeType() {
        return includeType;
    }

    public void setIncludeType(String includeType) {
        this.includeType = includeType;
    }
}
