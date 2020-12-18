package com.app.newuidashboardadmin.firebase;


import com.app.newuidashboardadmin.Utility.AppPrefernce;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.megogrid.beans.SetDeviceRequest;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
//    MySharedPreference sharedPreference;


    @Override
    public void onTokenRefresh()
    {
        super.onTokenRefresh();
        //For registration of token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("tokenn ==== MyFirebaseInstanceIDService  "+refreshedToken);

//        sharedPreference = new MySharedPreference(MyFirebaseInstanceIDService.this);
//        sharedPreference.setStoreTokenKey(refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer();
    }


    private void sendRegistrationToServer()
    {
        AppPrefernce prefernce = new AppPrefernce(this);
        if(prefernce.getString("logIn") != null)
        {
            SetDeviceRequest request=new SetDeviceRequest(this);
//            RestApiController controller=new RestApiController(this,this,5);
//            controller.makemebasedRequest(request,false);

        }
    }


 /*   @Override
    public void onResponseObtained(Object response, int responseType, boolean isCachedData) {

    }

    @Override
    public void onErrorObtained(String errormsg, int responseType) {

    }*/

}
