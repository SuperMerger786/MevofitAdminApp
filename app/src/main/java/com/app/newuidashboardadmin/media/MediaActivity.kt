package com.app.newuidashboardadmin.media

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.app.newuidashboardadmin.R
import com.app.newuidashboardadmin.clienttab.adapter.BootstrapPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.megogrid.megoauth.AuthorisedPreference
import java.util.*

class MediaActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewpager: ViewPager? = null
    var pagerAdapter: BootstrapPagerAdapter? = null
    var parent: LinearLayout? = null;
    var toolbar: android.widget.LinearLayout? = null

    val PERMISSION_TAKE_PICTURE = arrayOf("android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")
    val PERMISSION_TAKE_VIDEO = arrayOf("android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")
    val PERMISSION_STORAGE = arrayOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.media_activity)
        initView();
//        openLocationPermission()
    }

    private fun setView() {
        val authorisedPreference = AuthorisedPreference(this)
        toolbar?.setBackgroundColor(Color.parseColor("#3e" + authorisedPreference.themeColor.substring(1)))
        val all = MediaFragment()
        val bundle = Bundle()
        bundle.putString("type", "All")
        all.arguments = bundle
        val media = MediaFragment()
        val bundle2 = Bundle()
        bundle2.putString("type", "Media")
        media.arguments = bundle2
        val docs = MediaFragment()
        val bundle3 = Bundle()
        bundle3.putString("type", "Docs")
        docs.arguments = bundle3
        tabLayout?.addTab(tabLayout!!.newTab().setText("All"))
        tabLayout?.addTab(tabLayout!!.newTab().setText("Media"))
        tabLayout?.addTab(tabLayout!!.newTab().setText("Docs"))
        tabLayout?.setTabTextColors(Color.parseColor("#000000"), Color.parseColor(authorisedPreference.themeColor))
        tabLayout?.setSelectedTabIndicatorColor(Color.parseColor(authorisedPreference.themeColor))
        tabLayout?.setTabGravity(TabLayout.GRAVITY_FILL)
        val fragments = ArrayList<Fragment>()
        fragments.add(all)
        fragments.add(media)
        fragments.add(docs)
        pagerAdapter = BootstrapPagerAdapter(supportFragmentManager, fragments)
        viewpager?.setAdapter(pagerAdapter)
        viewpager?.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        viewpager?.setOffscreenPageLimit(fragments.size)
        tabLayout?.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewpager?.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun initView() {
        tabLayout = findViewById(R.id.tabLayout) as TabLayout
        viewpager = findViewById(R.id.viewpager) as ViewPager
        toolbar = findViewById(R.id.toolbar) as LinearLayout
        setPermission()
    }

    /* protected fun requestPermission(permissions: Array<String>, code: Int) {
         var permissions = permissions
         if (Build.VERSION.SDK_INT >= 23) {
             val deniedPermissions: List<String> = getDeniedPermissions(this, permissions)
             if (deniedPermissions.isEmpty()) {
                 onPermissionGranted(code)
             } else {
                 permissions = arrayOfNulls(deniedPermissions.size)
 //                deniedPermissions.toArray<String>(permissions)
                 ActivityCompat.requestPermissions(this, permissions, code)
             }
         } else {
             onPermissionGranted(code)
         }
     }
 //    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>?, grantResults: IntArray) {
 //        if (isGrantedResult(*grantResults)) onPermissionGranted(requestCode) else onPermissionDenied(requestCode)
 //    }

     protected fun onPermissionGranted(code: Int) {}

     protected fun onPermissionDenied(code: Int) {}

     fun bye() {
         onBackPressed()
     }

     private fun getDeniedPermissions(context: Context, permissions: Array<String>): List<String> {
         val deniedList: MutableList<String> = ArrayList(2)
         for (permission in permissions) {
             if (PermissionChecker.checkSelfPermission(context, permission!!) !== PermissionChecker.PERMISSION_GRANTED) {
                 deniedList.add(permission!!)
             }
         }
         return deniedList
     }

     private fun isGrantedResult(vararg grantResults: Int): Boolean {
         for (result in grantResults) {
             if (result == PackageManager.PERMISSION_DENIED) return false
         }
         return true
     }*/

    fun setPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
            openLocationPermission()
        } else {
            setView();
//            Toast.makeText(mContext,"check Permission",Toast.LENGTH_LONG).show();
        }
    }

    open fun openLocationPermission() {
        val localBuilder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.DialogTheme))
        localBuilder.setTitle(" Location Permission")
        localBuilder.setIcon(R.drawable.app_icon)
        localBuilder.setMessage("This permission is required to scan the band quickly and establish a smooth connection between the band and app.")
        localBuilder.setPositiveButton("Ok") { paramAnonymousDialogInterface, paramAnonymousInt -> ActivityCompat.requestPermissions((this), PERMISSION_STORAGE, Io_Permission) }
        localBuilder.setNegativeButton("Cancel") { paramAnonymousDialogInterface, paramAnonymousInt -> }
        localBuilder.setCancelable(false).create()
        localBuilder.show()
    }

    val Io_Permission = 111

    @TargetApi(23)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Io_Permission) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    setView();
                } else {
                    /* var str = ""
                     var str2 = ""
                     if (i == 0) {
                         str = "Access Location"
                         str2 = "This permission is required to scan the band quickly and establish a smooth connection between the band and app."
                     } else if (i == 1) {
                         str = "Access Location"
                         str2 = "This permission is required to scan the band quickly and establish a smooth connection between the band and app."
                     } else if (i == 2) {
                         str = "Bluetooth Access"
                         str2 = "Please allow bluetoth permission so that the band can be easily connected with your phone."
                     } else if (i == 3) {
                         str = "Bluetooth Access"
                         str2 = "Please allow bluetoth permission so that the band can be easily connected with your phone."
                     } *//*else if (i == 4) {
                        str = "Write external Storage";
                        str2 = "This permission is required to store your customized settings for the device in your phone.";
                    }*//*
                    if (dialog != null) dialog.dismiss()
                    dialog = PermissionUtils.onCreateDialog(mContext.get(), str, str2, object : Permissioncallback() {
                        fun onFailure() {
//                            finish();
                        }

                        fun onRetry() {
                            ActivityCompat.requestPermissions((mContext.get() as Activity), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH), PermissionUtils.Io_Permission)
                        }
                    })*/
                }
            }

//            getPermission();
        }
    }
}