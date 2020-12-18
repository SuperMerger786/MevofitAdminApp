package newui_food.foodnewui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.newuidashboardadmin.R
import com.app.newuidashboardadmin.newadmin.BookedList
import com.app.newuidashboardadmin.plan.UserDetailsActivity
import com.app.newuidashboardadmin.plan.bean.SlotData
import java.util.*


class MyUpCommingClassAdminAdapter(val mContext: Context, private val versionList: ArrayList<BookedList>)  : RecyclerView.Adapter<MyUpCommingClassAdminAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.upcomming_listitem, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(mContext, versionList[position])
    }

    override fun getItemCount(): Int {
        return versionList.size
//        return 5
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(mContext: Context, version: BookedList) {
            val textView = itemView.findViewById<TextView>(R.id.tv_title_name)
            val textdesc = itemView.findViewById<TextView>(R.id.tv_upcome_duration)
            val viewdt =itemView.findViewById<TextView>(R.id.id_view);
            textView.text = version.itemTitle+" | "+version.resturantName
            textdesc.text =version.resturantAddress
            viewdt.setOnClickListener {
                val slotData = SlotData()
                slotData.user_name = version.customername
                slotData.user_profilepic = version.profilepic
                slotData.user_gender = "male"
                slotData.user_age = "28"
                slotData.user_city = "varanasi"
                slotData.user_country = version.resturantAddress

                val intent = Intent(mContext, UserDetailsActivity::class.java)
                intent.putExtra("data", slotData)
                mContext.startActivity(intent)
            }




        }
    }
}