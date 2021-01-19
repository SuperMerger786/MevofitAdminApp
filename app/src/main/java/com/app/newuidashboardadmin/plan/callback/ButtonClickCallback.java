package com.app.newuidashboardadmin.plan.callback;

import com.app.newuidashboardadmin.plan.bean.PendingListData;

public interface ButtonClickCallback {
    void OnDateSelect(PendingListData pendingListData,boolean isCancel);
}
