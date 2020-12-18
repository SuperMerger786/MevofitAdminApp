package com.app.newuidashboardadmin.clienttab.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.newuidashboardadmin.MyLogger;
import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.Utility.AppPrefernce;
import com.app.newuidashboardadmin.clienttab.adapter.ClientAdapter;
import com.app.newuidashboardadmin.clienttab.callback.SelectedClient;
import com.app.newuidashboardadmin.clienttab.sevices.ClientList;
import com.app.newuidashboardadmin.clienttab.sevices.GetUserListRequest;
import com.app.newuidashboardadmin.clienttab.sevices.GetUserListResponse;
import com.app.newuidashboardadmin.clienttab.sevices.ProfileSetUpResponse;
import com.app.newuidashboardadmin.clienttab.sevices.ProfileSetupRequest;
import com.app.newuidashboardadmin.plan.bean.request.GetSellerBookingSlotsRequest;
import com.app.newuidashboardadmin.services.Response;
import com.app.newuidashboardadmin.services.RestApiController;
import com.google.gson.Gson;
import com.megogrid.megoauth.AuthUtility;
import com.megogrid.megoauth.AuthorisedPreference;
import com.megogrid.megouser.MegoUserSDK;
import com.migital.digiproducthelper.MevoSellerDetailsActivity;
import com.migital.digiproducthelper.util.DigiHelperPreference;

import java.util.ArrayList;
public class AddUserActivity extends AppCompatActivity implements Response, SelectedClient {

    LinearLayout existingUser;
    //private AuthorisedPreference authorisedPreference;
    RelativeLayout newUser;
    EditText searchText,edName,edPhone,edEmail;
    ImageView searchIcon;
    TextView confirmNewUser,txtTime;
    RecyclerView userListRecycler;
    ClientAdapter listAdapter;
    String boxid;
    ArrayList<ClientList> allUserList=new ArrayList<>();
    GetSellerBookingSlotsRequest request;
    int page=1,currentList;
    boolean loading;
    RestApiController controller;
    MegoUserSDK sdk;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        //authorisedPreference = new AuthorisedPreference(this);
        //AuthUtility.setThemeColorInStatusBar(this);
        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(AuthUtility.getDarkColor(Color.parseColor("#00A6bc")));
        }
        boxid=getIntent().getStringExtra("boxid");
        setUpViews();

    }
    int selectedposition;
    public void AddNewUser(View v)
    {
        if(isnewUser) {
            if (edName.getText().toString().equalsIgnoreCase("")) {
                edEmail.setError(null);
                edPhone.setError(null);
                edName.setError("Please enter name");
            } else if (edPhone.getText().toString().equalsIgnoreCase("")) {
                edEmail.setError(null);
                edPhone.setError("Please enter phone");
                edName.setError(null);
            } else if (edEmail.getText().toString().equalsIgnoreCase("")) {

                edPhone.setError(null);
                edName.setError(null);
                edEmail.setError("Please enter email");
            } else {
                ProfileSetupRequest request =
                        new ProfileSetupRequest(this, edEmail.getText().toString(),
                                edName.getText().toString(), edPhone.getText().toString());
                RestApiController apiController = new RestApiController(this, this, RestApiController.REGISTRATIONREQUEST);
                apiController.makemebasedRequest(request, true);

            }
        }
        else
        {
            if(allUserList!=null && !allUserList.isEmpty()) {
                AppPrefernce appPrefernce = new AppPrefernce(this);
                AuthorisedPreference authorisedPreference = new AuthorisedPreference(this);
                DigiHelperPreference digiHelperPreference = DigiHelperPreference.getInstance(this);
                ClientList clientList=allUserList.get(selectedposition);
                digiHelperPreference.setBoolean(DigiHelperPreference.ISADMIN, true);
                digiHelperPreference.setString(DigiHelperPreference.TokenKey, clientList.user_tk);
                digiHelperPreference.setString(DigiHelperPreference.MewardID, clientList.user_meward_id);

                Intent intent = new Intent(this, MevoSellerDetailsActivity.class);
                intent.putExtra("instance_id", appPrefernce.getInstanceBoxid());
                intent.putExtra("sellerid", authorisedPreference.getString("app_sellerrid"));
                startActivity(intent);
                finish();
            }
        }
    }
    boolean isnewUser;
    private void setUpViews()
    {
        newUser= (RelativeLayout) findViewById(R.id.new_user);
        existingUser= (LinearLayout) findViewById(R.id.existing_user);
        searchIcon= (ImageView) findViewById(R.id.search_icon);
        confirmNewUser= (TextView) findViewById(R.id.confirm_new_user);
        searchText= (EditText) findViewById(R.id.search_text);
        edEmail= (EditText) findViewById(R.id.ed_email);
        edName= (EditText) findViewById(R.id.ed_name);
        edPhone= (EditText) findViewById(R.id.ed_phone);
        txtTime= (TextView) findViewById(R.id.txt_timing);
        userListRecycler=(RecyclerView)findViewById(R.id.user_list);
        final LinearLayoutManager manager=new LinearLayoutManager(this);
        userListRecycler.setLayoutManager(manager);
        listAdapter=new ClientAdapter(this,this,true);
        listAdapter.setClientList(allUserList);
        userListRecycler.setAdapter(listAdapter);
        userListRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem=manager.findLastCompletelyVisibleItemPosition();
                if(!loading&&lastVisibleItem==allUserList.size()-1&&currentList==12)
                {
                    loading=true;
                    request.setPaginationID(String.valueOf(page++));
                    controller.makemebasedRequest(request,false);
                }

            }
        });
        isnewUser= getIntent().getBooleanExtra("newUser",false);
        TextView toolTitle =findViewById(R.id.tool_title);
        if(isnewUser) {

            toolTitle.setText("Add New Client");
            newUser.setVisibility(View.VISIBLE);
            existingUser.setVisibility(View.GONE);
        }
        else {
            toolTitle.setText("Choose Clients");
            newUser.setVisibility(View.GONE);
            existingUser.setVisibility(View.VISIBLE);
        }

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.toString().equalsIgnoreCase(""))
                {
                    setRequest("");
                }


            }

            @Override
            public void afterTextChanged(Editable s)
            {




            }
        });
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setRequest(searchText.getText().toString());
            }
        });
        if(getIntent().getStringExtra("time")!=null)
            txtTime.setText(getIntent().getStringExtra("time"));
        ImageView back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        GradientDrawable drawable= (GradientDrawable) confirmNewUser.getBackground();
        drawable.setColor(Color.parseColor(new AuthorisedPreference(this).getThemeColor()));
        controller=new RestApiController(this,this,RestApiController.GETUSERS);
        request=new GetSellerBookingSlotsRequest("GetBookedUsers",this);
        setRequest("");
    }

    private void setRequest(String s)
    {
        page = 1;
        request.setPaginationID(String.valueOf(page++));
        request.name = s;
        loading = true;


        RestApiController controller = new RestApiController(AddUserActivity.this, new Response() {
            @Override
            public void onResponseObtained(Object response, int responseType, boolean isCachedData) {
                MyLogger.println("AdduserActivity "+response.toString());
                loading = false;
                GetUserListResponse userList = new Gson().fromJson(response.toString(), GetUserListResponse.class);
                allUserList.clear();
                if (userList.data != null) {
                    for (ClientList users : userList.data)
                        allUserList.add(users);
                }
                currentList = allUserList.size();
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorObtained(String errormsg, int responseType) {
                MyLogger.println("AdduserActivity "+errormsg);

                loading = false;
                currentList = 0;
                allUserList.clear();
                listAdapter.notifyDataSetChanged();

            }
        }, 0);

        controller.makemebasedRequest(request, true);
    }

    @Override
    public void onResponseObtained(Object response, int responseType, boolean isCachedData) {

        Gson gson=new Gson();
        if(responseType==RestApiController.GETUSERS)
        {
            loading=false;
            GetUserListResponse userList=gson.fromJson(response.toString(),GetUserListResponse.class);
            if(userList.data!=null&&!userList.data.isEmpty())
            {
                currentList=userList.data.size();
                int lastSize=allUserList.size();
                for(ClientList users:userList.data)
                    allUserList.add(users);

                listAdapter.notifyItemRangeChanged(lastSize,currentList);
            }
            else
                Toast.makeText(this,"No user is found", Toast.LENGTH_LONG).show();
        }
        else if(responseType==RestApiController.REGISTRATIONREQUEST)
        {
            ProfileSetUpResponse profileSetUpResponse=new Gson().fromJson(response.toString(),ProfileSetUpResponse.class);

            if(profileSetUpResponse!=null){
                AppPrefernce appPrefernce = new AppPrefernce(this);
                AuthorisedPreference authorisedPreference = new AuthorisedPreference(this);
                DigiHelperPreference digiHelperPreference = DigiHelperPreference.getInstance(this);
                digiHelperPreference.setBoolean(DigiHelperPreference.ISADMIN, true);
                digiHelperPreference.setString(DigiHelperPreference.TokenKey, profileSetUpResponse.tokenkey);
                digiHelperPreference.setString(DigiHelperPreference.MewardID, profileSetUpResponse.mewardid);

                Intent intent = new Intent(this, MevoSellerDetailsActivity.class);
                intent.putExtra("instance_id", appPrefernce.getInstanceBoxid());
                intent.putExtra("sellerid", authorisedPreference.getString("app_sellerrid"));
                startActivity(intent);
                finish();
            }


        }

    }

    @Override
    public void onErrorObtained(String errormsg, int responseType)
    {
        if(responseType==RestApiController.REGISTRATIONREQUEST)
            Toast.makeText(this,errormsg, Toast.LENGTH_LONG).show();
        else if(responseType==RestApiController.GETUSERS)
            loading=false;

    }

    @Override
    public void selectedposition(int selectedposition) {
        this.selectedposition=selectedposition;
    }
}
