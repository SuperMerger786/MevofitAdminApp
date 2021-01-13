package com.app.newuidashboardadmin.media

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.newuidashboardadmin.R

class FliesShoeAdapter(val mContext: Context, private val versionList: ArrayList<PDFDoc>,val type:String) : RecyclerView.Adapter<FliesShoeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.model, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(mContext, versionList[position],type)
    }

    override fun getItemCount(): Int {
        return versionList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var output: Bitmap? = null
        fun bindItems(mContext: Context, version: PDFDoc,typefile:String) {
            val textView = itemView.findViewById<TextView>(R.id.nameTxt)
            val imageView = itemView.findViewById<ImageView>(R.id.pdfImage)
//            val card_view =itemView.findViewById<LinearLayout>(R.id.card_view);
            textView.text = version.name
         if(typefile.equals("all")){

         }else  if(typefile.equals("all")){

         }else{

         }
//            Glide.with(itemView.context).load(version.catImageUrl).into(imageView)
            //        Glide.with(mContext).load(imgUri).into(imageView);


            itemView?.setOnClickListener {
                val i = Intent(mContext, PDFActivity::class.java)
                i.putExtra("PATH", version.path)
                mContext.startActivity(i)
                println("categaaries name>>>>>" + version.name + "==" + version.path);
//
            }
        }
        /* fun generateCircleBitmap(circleColor: Int, text: String?, imageView: ImageView): Bitmap? {
             val textColor = -0x1
             val diameterDP = 14f
             val metrics = Resources.getSystem().displayMetrics
             val diameterPixels = diameterDP * (metrics.densityDpi / 160f)
             val radiusPixels = diameterPixels / 2

             // Create the bitmap
             output = Bitmap.createBitmap(diameterPixels.toInt(), diameterPixels.toInt(),
                     Bitmap.Config.ARGB_8888)

             // Create the canvas to draw on
             val canvas = Canvas(output)
             canvas.drawARGB(0, 0, 0, 0)

             // Draw the circle
             val paintC = Paint()
             paintC.isAntiAlias = true
             paintC.color = circleColor
             canvas.drawCircle(radiusPixels, radiusPixels, radiusPixels, paintC)

             // Draw the text
             if (text != null && text.length > 0) {
                 val paintT = Paint()
                 paintT.color = textColor
                 paintT.isAntiAlias = true
                 paintT.textSize = radiusPixels
                 //            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),"font/Poppins-Medium.ttf");
 //            paintT.setTypeface(typeFace);
                 val textBounds = Rect()
                 paintT.getTextBounds(text[0].toString(), 0, 1, textBounds)
                 canvas.drawText(text[0].toString(), radiusPixels - textBounds.exactCenterX(), radiusPixels - textBounds.exactCenterY(), paintT)
             }
             imageView.setImageBitmap(output)
             imageView.scaleType = ImageView.ScaleType.CENTER_CROP
             return output
         }*/
    }

    open fun openPDFView(path: String) {

    }
}