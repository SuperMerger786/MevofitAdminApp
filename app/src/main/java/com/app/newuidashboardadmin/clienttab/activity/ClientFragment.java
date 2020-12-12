package com.app.newuidashboardadmin.clienttab.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.clienttab.adapter.ClientAdapter;
import com.app.newuidashboardadmin.clienttab.sevices.BookingSummaryResponse;
import com.app.newuidashboardadmin.clienttab.sevices.ClientList;
import com.app.newuidashboardadmin.clienttab.sevices.GetUserListRequest;
import com.app.newuidashboardadmin.plan.bean.request.GetSellerBookingSlotsRequest;
import com.app.newuidashboardadmin.services.Response;
import com.app.newuidashboardadmin.services.RestApiController;
import com.google.gson.Gson;
import java.util.ArrayList;


public class ClientFragment extends Fragment implements Response
{
    RecyclerView orderlist_recycler;
    Boolean loading = false;
    int TotalSize;
    RestApiController controller;
    GetSellerBookingSlotsRequest req;
    ProgressBar progressBar;
    RelativeLayout noOrder;
    int i ;
    int currentList;
    public ArrayList<ClientList> ordersummaryList=new ArrayList<>();
    ClientAdapter listAdapter;
    String requestType,parent_bookingid;
    SwipeRefreshLayout swipeToRefresh;
    TextView notask;
    Context context;
    boolean isVisibleToUser,isCreated,isRequested;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate is getting called");

        requestType=getArguments().getString("type");
        parent_bookingid=getArguments().getString("parent_bookingid");

        if(!isVisibleToUser)
            isVisibleToUser=getArguments().getBoolean("isVisibleToUser",false);
        isCreated=true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_frag_layout,container,false);
        initView(view);
        return view;
    }



    private void initView(View view)
    {
        swipeToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        swipeToRefresh.setColorSchemeColors(Color.parseColor("#c7c7c7"));
        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRequest();

            }
        });


        orderlist_recycler = (RecyclerView)view.findViewById(R.id.orderlist);
        progressBar=(ProgressBar) view.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        notask = (TextView) view.findViewById(R.id.notask);
        notask.setVisibility(View.GONE);
        noOrder=(RelativeLayout)view.findViewById(R.id.notask_parent) ;
        noOrder.setVisibility(View.GONE);

        view.findViewById(R.id.add_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AddUserActivity.class);
                intent.putExtra("newUser",true);
                context.startActivity(intent);
            }
        });

        view.findViewById(R.id.search_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AddUserActivity.class);
                context.startActivity(intent);
            }
        });

        setClientList();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isVisibleToUser)
            setRequest();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        if(isVisibleToUser && isCreated)
            setRequest();
    }

    private void setRequest()
    {
        if(!isRequested) {
            isRequested=true;
            i = 1;
            TotalSize = 0;
            currentList = 0;
            ordersummaryList.clear();
            listAdapter.notifyDataSetChanged();
            controller = new RestApiController(getContext(), this, 5);
            req=new GetSellerBookingSlotsRequest(requestType,context);
            req.setPaginationID(null);
            loading = true;

            controller.makemebasedRequest(req, true);
            System.out.println("StandAlone.onResume");
        }

    }
    private void setClientList()
    {
        listAdapter = new ClientAdapter(getContext());

        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        orderlist_recycler.setLayoutManager(manager);
        orderlist_recycler.setAdapter(listAdapter);
        orderlist_recycler.addOnScrollListener(new RecyclerView.OnScrollListener()
                                               {
                                                   @Override
                                                   public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                                       super.onScrolled(recyclerView, dx, dy);

                                                       int latVisibleItem = manager.findLastCompletelyVisibleItemPosition();

                                                       if (!loading && (TotalSize-1==latVisibleItem)&& currentList == 12)
                                                       {
                                                           loading = true;
                                                           System.out.println("OrdersFragment.onScrolled ------1");
                                                           progressBar.setVisibility(View.VISIBLE);
                                                           req.setPaginationID(String.valueOf(i));
                                                           ++i;
                                                           controller.makemebasedRequest(req,false);

                                                       }
                                                   }
                                               }

        );
    }

    @Override
    public void onResponseObtained(Object response, int responseType, boolean isCachedData)
    {
        System.out.println("Onerror onResponseObtained");
        Gson gson = new Gson();
        BookingSummaryResponse response1 = gson.fromJson(response.toString(), BookingSummaryResponse.class);
        noOrder.setVisibility(View.GONE);

        isRequested=false;
        int previousSize=ordersummaryList.size();

        for (ClientList clientList : response1.bookedList)
        {
            ordersummaryList.add(clientList);

        }
        System.out.println("StandAlone.onscroll total="+TotalSize);
        currentList=response1.bookedList.size();
        TotalSize = TotalSize + currentList ;
        response1.bookedList=ordersummaryList;
        listAdapter.setClientList(ordersummaryList);
        listAdapter.notifyItemRangeChanged(previousSize, currentList);

        progressBar.setVisibility(View.GONE);
        loading = false;


        swipeToRefresh.setRefreshing(false);

    }




    @Override
    public void onErrorObtained(String errormsg, int responseType)
    {
        System.out.println("Onerror obtained"+errormsg);
        if(i==1) {
            noOrder.setVisibility(View.VISIBLE);
            notask.setVisibility(View.VISIBLE);
            //  notask.setText(R.string.no_booking_task);


            ordersummaryList.clear();
            // listAdapter.setlist(new BookingSummaryResponse());
            listAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
            isRequested=false;
        }

        swipeToRefresh.setRefreshing(false);

    }

}
