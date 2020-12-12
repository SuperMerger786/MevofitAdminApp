package com.app.newuidashboardadmin.clienttab.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.clienttab.callback.SelectedClient;
import com.app.newuidashboardadmin.clienttab.sevices.AdminUtil;
import com.app.newuidashboardadmin.clienttab.sevices.ClientList;
import com.megogrid.megoauth.AuthorisedPreference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {
    Context context;
    public ArrayList<ClientList> clientList = new ArrayList<>();
    public int  selectedposition;
    boolean tobebookedclients;
    SelectedClient selectedClient;
    ImageView previousselectedview;
    public void setClientList(ArrayList<ClientList> clientList) {
        this.clientList = clientList;
    }

    public ClientAdapter( Context context) {
        super();
        this.context = context;

    }

    public ClientAdapter(Context context,  SelectedClient selectedClient, boolean tobebookedclients) {
        this.context = context;
        this.selectedClient = selectedClient;
        this.tobebookedclients = tobebookedclients;
    }

    public void setSelectedposition(int selectedposition) {
        this.selectedposition = selectedposition;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (tobebookedclients)
            view= inflater.inflate(R.layout.client_list_items_tobebooked, viewGroup, false);
        else
            view= inflater.inflate(R.layout.client_list_items, viewGroup, false);

        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, final int i) {

        final ClientList client = clientList.get(i);
        if (!AdminUtil.isValid(client.user_name) && AdminUtil.isValidEmail(client.user_email))
            client.user_name = client.user_email.split("@")[0];

        if(tobebookedclients){
            if(selectedposition==i) {
                holder.clientSelection.setImageResource(R.drawable.selected_client);
                previousselectedview=holder.clientSelection;
            }
            else
                holder.clientSelection.setImageResource(R.drawable.deselected_client);

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedposition!=i) {
                        holder.clientSelection.setImageResource(R.drawable.selected_client);
                        previousselectedview.setImageResource(R.drawable.deselected_client);
                        selectedposition=i;
                        previousselectedview=holder.clientSelection;
                        selectedClient.selectedposition(i);
                    }

                }
            });

        }

        else {
            if (client.is_last_session) {
                holder.dateLinear.setVisibility(View.VISIBLE);
                holder.session.setVisibility(View.VISIBLE);
                holder.newClient.setVisibility(View.GONE);
                holder.session.setText("Last Session | " + client.last_plan_session_title.replace(" Session", ""));
                String lastSessionDate = client.last_session_slot_date + " | " + client.last_session_start_time + " - " + client.last_session_end_time;

                try {
                    SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd | HH:mm - HH:mm", Locale.US);
                    SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM | hh:mm - hh:mm a", Locale.US);
                    Date date = originalFormat.parse(lastSessionDate);
                    lastSessionDate = targetFormat.format(date);
                } catch (ParseException e) {
                    System.out.println("ClientAdapter.onBindViewHolder");
                }
                holder.lastSessionDate.setText(lastSessionDate);

            } else {
                holder.dateLinear.setVisibility(View.VISIBLE);
                holder.session.setVisibility(View.VISIBLE);
                holder.newClient.setVisibility(View.GONE);

            }

            holder.currency.setText(AdminUtil.getCurrencySymbol(client.currency_symbol));
            holder.price.setText(client.variant_discounted_price);

        }
        String url = client.user_profilepic;
        if (!AdminUtil.isValid(url))
            holder.userProfile.setImageResource(R.drawable.profile);
        else
            AdminUtil.makeImageRequest(holder.mainProgress,holder.userProfile,url,context);

        holder.clientType.setText(client.reg_user_type);
        holder.city.setText(client.user_city);
        holder.country.setText(client.user_country);
        holder.user_name.setText(client.user_name);
        holder.gender.setText(client.user_gender+", ");
        holder.age.setText(client.user_age);

    }



    @Override
    public int getItemCount() {
        return  clientList.size();
    }



    class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView user_name, price, currency, newClient, session,
                clientType,city,country,lastSessionDate,lastSessionTime,gender,age;
        View view;
        CardView cardOrderStatus, cardBookedUsers;
        ImageView userProfile,clientSelection;
        View priceBg,dateLinear,proFileDetails,redirect;
        LinearLayout mainProgress;
        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            user_name = itemView.findViewById(R.id.user_name);
            userProfile = itemView.findViewById(R.id.user_profile);
            mainProgress= itemView.findViewById(R.id.main_progress);
            proFileDetails = itemView.findViewById(R.id.profiledetail);
            clientSelection = itemView.findViewById(R.id.selected_client);

            price = itemView.findViewById(R.id.price);
            clientType = itemView.findViewById(R.id.client_type);
            currency = itemView.findViewById(R.id.currency);
            newClient= itemView.findViewById(R.id.new_client);
            session = itemView.findViewById(R.id.session);
            priceBg = itemView.findViewById(R.id.bg_price);
            dateLinear=itemView.findViewById(R.id.date_linear);
            city = itemView.findViewById(R.id.city);
            country = itemView.findViewById(R.id.country);
            gender = itemView.findViewById(R.id.gender);
            age = itemView.findViewById(R.id.age);
            lastSessionDate = itemView.findViewById(R.id.date);
            lastSessionTime = itemView.findViewById(R.id.time);

            AuthorisedPreference authorisedPreference=new AuthorisedPreference(context);
            int color=Color.parseColor(authorisedPreference.getThemeColor());

            city.setTextColor(color);
            country.setTextColor(color);
            if(!tobebookedclients) {
                currency.setTextColor(color);
                price.setTextColor(color);
                priceBg.setBackgroundColor(color);
            }

        }
    }

}