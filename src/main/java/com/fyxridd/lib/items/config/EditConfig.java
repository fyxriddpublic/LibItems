package com.fyxridd.lib.items.config;

import com.fyxridd.lib.core.api.config.basic.Path;

@Path("edit")
public class EditConfig {
    @Path("per")
    private String editPer;

    public String getEditPer() {
        return editPer;
    }
}
