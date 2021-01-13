package com.app.newuidashboardadmin.media

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.newuidashboardadmin.R
import java.io.File

class MediaFragment : Fragment() {

    var requestType: String? = null
//    lateinit var lv: ;
//    var context: Context? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
//        context=context;
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate is getting called")
        /* requestType = arguments!!.getString("type")
         parent_bookingid = arguments!!.getString("parent_bookingid")
         if (!isVisibleToUser) isVisibleToUser = arguments!!.getBoolean("isVisibleToUser", false)
         isCreated = true*/
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.media_frag_layout, container, false)
        requestType = arguments!!.getString("type")
//        initView(view)
//        if (isVisibleToUser) setRequest()
       var recyclerView:RecyclerView =view.findViewById(R.id.lv) as RecyclerView
//        lv!!.setAdapter(PdfCustomAdapter(this.context, getPDFs()))

//        ArrayList<VersionMarchandise> versionArrayList = new ArrayList<>();
//        versionArrayList.addAll(VersionMarchandise.Companion.getList());
        val linearLayoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        //        recyclerView.setHasFixedSize(true);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        val glm = GridLayoutManager(activity, 3)
        recyclerView.setLayoutManager(glm)
        recyclerView.setAdapter(FliesShoeAdapter(this.context!!, getPDFs()!!,"media"))
        return view
    }

    private fun getPDFs(): ArrayList<PDFDoc>? {
        val pdfDocs: ArrayList<PDFDoc> = ArrayList()
        //TARGET FOLDER
//        val downloadsFolder: File = Environment.getExternalStoragePublicDirectory("MevoFit device/WorkoutVideos")
        val downloadsFolder: File = File(Environment.getExternalStorageDirectory(),"WorkoutVideos")
        println("heck>>>>>>>document11111111 ${downloadsFolder.exists()}")
        var pdfDoc: PDFDoc
        if (downloadsFolder.exists()) {
            //GET ALL FILES IN DOWNLOAD FOLDER
            println("heck>>>>>>>document ${downloadsFolder.listFiles().size}")
            val files: Array<File> = downloadsFolder.listFiles()

            //LOOP THRU THOSE FILES GETTING NAME AND URI
            for (i in files.indices) {
                val file: File = files[i]
                println("heck>>>>>>>document0 ${file.getPath()}")
//                if (file.getPath().endsWith("pdf") || file.getPath().endsWith("txt")) {
                    pdfDoc = PDFDoc()
                    pdfDoc.setName(file.getName())
                    pdfDoc.setPath(file.getAbsolutePath())
                    pdfDoc.setType("pdf")
                    pdfDocs.add(pdfDoc)
//                }
            }
        }
        return pdfDocs
    }
}
