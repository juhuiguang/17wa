package com.alienlab.wa17;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by 橘 on 2017/3/21.
 */
public class WaImageProp {
    String path="";

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
