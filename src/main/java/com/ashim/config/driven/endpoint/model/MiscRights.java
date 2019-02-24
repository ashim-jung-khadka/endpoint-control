package com.ashim.config.driven.endpoint.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ashimjk on 2/23/2019
 */
@Getter
@Setter
@ToString
public class MiscRights {

    private boolean productViewable;
    private boolean order;
    private boolean processing;
    private boolean hasItem;

    public MiscRights() {
        this.order = true;
        this.processing = true;
    }

}
