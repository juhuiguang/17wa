package com.alienlab.wa17.entity.client.dto;

import com.alienlab.wa17.entity.client.ClientTbColorCus;
import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbSizeCus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘 on 2017/3/6.
 */
@ApiModel(value = "产品数据对象")
public class ProductDto extends ClientTbProduct {
    @ApiModelProperty(value="产品对应颜色列表")
    List<ClientTbColorCus> colors=new ArrayList<ClientTbColorCus>();
    @ApiModelProperty(value="产品对应尺码列表")
    List<ClientTbSizeCus> sizes=new ArrayList<ClientTbSizeCus>();

    public List<ClientTbColorCus> getColors() {
        return colors;
    }

    public void setColors(List<ClientTbColorCus> colors) {
        this.colors = colors;
    }

    public List<ClientTbSizeCus> getSizes() {
        return sizes;
    }

    public void setSizes(List<ClientTbSizeCus> sizes) {
        this.sizes = sizes;
    }
}
