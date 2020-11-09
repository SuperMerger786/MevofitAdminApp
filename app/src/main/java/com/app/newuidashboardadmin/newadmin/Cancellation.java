
package com.app.newuidashboardadmin.newadmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cancellation {

    @SerializedName("is_cancellation")
    @Expose
    private Boolean isCancellation;

    public Boolean getIsCancellation() {
        return isCancellation;
    }

    public void setIsCancellation(Boolean isCancellation) {
        this.isCancellation = isCancellation;
    }

}
