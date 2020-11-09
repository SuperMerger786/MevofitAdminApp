package newui_food.foodnewui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.newuidashboardadmin.R
import com.app.newuidashboardadmin.newadmin.BookedList
import java.util.ArrayList

class MyUpCommingClassAdminAdapter(val mContext: Context, private val versionList: ArrayList<BookedList>)  : RecyclerView.Adapter<MyUpCommingClassAdminAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.upcomming_listitem, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(mContext,versionList[position])
    }

    override fun getItemCount(): Int {
        return versionList.size
//        return 5
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(mContext: Context, version: BookedList) {
            val textView = itemView.findViewById<TextView>(R.id.tv_title_name)
            val textdesc = itemView.findViewById<TextView>(R.id.tv_upcome_duration)

            textView.text = version.itemTitle+" | "+version.resturantName
            textdesc.text =version.resturantAddress

        }
    }
}