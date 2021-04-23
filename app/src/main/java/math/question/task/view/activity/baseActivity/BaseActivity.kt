package math.question.task.view.activity.baseActivity

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import math.question.task.MyApplication
import math.question.task.R
import math.question.task.databinding.ActivityBaseBinding
import math.question.task.observer.OnAskUserAction
import math.question.task.util.*
import math.question.task.view.fragments.menu.MenuFragment
import java.util.*
import kotlin.properties.Delegates

abstract class BaseActivity(
    var activityTitleId: Int,
    var showSideTitle: Boolean,
    var showCenterTitle: Boolean,
    var drawHeader: Boolean,
    var showBack: Boolean,
    var showMenu: Boolean,
    var showNotification: Boolean,
    var showHomeButton: Boolean,
    var appBarWhite: Boolean
) : AppCompatActivity() {

    protected abstract fun doOnCreate(arg0: Bundle?)
    abstract fun initializeViews()
    abstract fun setListener()

    fun updateLocale() {
        //update activities locale
        if (Preferences.getApplicationLocale().compareTo("en") == 0) {
            forceLTRIfSupported()
        } else {
            forceRTLIfSupported()
        }
        //Update the locale here before loading the layout to get localized strings for activity.
        LocaleHelper.updateLocale(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateAndroidSecurityProvider()
        updateLocale()
        baseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        //set default color for appbar
        setTranslucentAppBar()
        application = getApplication() as MyApplication
        application.context = this

        baseBinding.viewModel =
            ViewModelProvider(this, BaseViewModelFactory(application))
                .get(BaseActivityViewModel::class.java)
        baseBinding.viewModel!!.baseViewModelObserver = baseViewModelObserver
        baseBinding.lifecycleOwner = this
        liveDataObservers()
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        //set actionbar
        setDrawHeader(
            drawHeader,
            getString(activityTitleId),
            showSideTitle,
            showCenterTitle,
            showBack,
            showMenu,
            showNotification,
            showHomeButton,
            appBarWhite
        )
        doOnCreate(savedInstanceState)
        setListener()
    }

    var baseViewModelObserver = object : BaseActivityViewModel.BaseViewModelObserver {

        override fun onBackButtonClicked() {
            hideKeyPad(baseBinding.ivBackIconCustomActionBar)
            onBackPressed()
        }

        override fun onMenuButtonClicked() {
            if (baseBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                closeMenu()
            } else {
                openMenu()
            }
        }

        override fun onAny1ButtonClicked() {
        }

        override fun onAny2ButtonClicked() {
        }

        override fun onSearchClicked() {
        }

        override fun onLoginAgain() {
        }

        override fun openLoginToUseFeature() {
        }

        override fun onRestartApp(message: Int) {
            restartApp(message)
        }
    }

    fun restartApp(message: Int) {
    }

    private fun liveDataObservers() {
    }

    var application: MyApplication by Delegates.notNull()
    var imm: InputMethodManager by Delegates.notNull()

    lateinit var baseBinding: ActivityBaseBinding

    fun putContentView(activityLayout: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            layoutInflater,
            activityLayout,
            baseBinding.baseFragment,
            true
        )
    }

    private fun updateAndroidSecurityProvider() {
    }

    fun setBackIconVisibility(isShowBackIcon: Boolean, appBarWhite: Boolean) {
        baseBinding.ivBackIconCustomActionBar.visibility =
            if (isShowBackIcon) View.VISIBLE else View.GONE
        //set icons
        if (appBarWhite) {
            baseBinding.ivBackIconCustomActionBar.setImageResource(R.drawable.back_white_icon)
        } else {
            baseBinding.ivBackIconCustomActionBar.setImageResource(R.drawable.back_white_icon)
        }
    }

    fun setMenuIconVisibility(isVisible: Boolean) {
        baseBinding.ivMenuCustomActionBar.visibility =
            if (isVisible) View.VISIBLE else View.GONE
        initializeSlideMenu()
        //enable or disable slide menu
        setDrawerState(isVisible)
    }

    fun setAnyIcon1Visibility(isVisible: Boolean) {
        baseBinding.layoutAnyIcon1CustomActionbar.visibility =
            if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    fun setAnyIcon1NotifyVisibility(isVisible: Boolean) {
        baseBinding.tvNotifyAnyIcon1CustomActionbar.visibility =
            if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    fun setHomeButtonVisibility(isVisible: Boolean) {
        baseBinding.ivHomeButton.visibility =
            if (isVisible) View.VISIBLE else View.GONE
        if (isVisible) {
            val anim = ScaleAnimation(
                0.8f, 1f,  // Start and end values for the X axis scaling
                0.8f, 1f,  // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f,  // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f
            ) // Pivot point of Y scaling
            // Needed to keep the result of the animation
            anim.fillAfter = true
            anim.duration = 100
            anim.repeatCount = 8
            anim.repeatMode = Animation.REVERSE
            baseBinding.ivHomeButton.startAnimation(anim)
        }
    }

    fun setActionBarVisibilty(isVisible: Boolean) {
        baseBinding.layoutContainerActionBar.visibility =
            if (isVisible) View.VISIBLE else View.GONE
    }

    internal fun setTranslucentAppBar() {
        val fullScreen = window.attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN != 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (fullScreen) {
                baseBinding.layoutContainerActionBar.visibility = View.GONE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
                }
            } else {
                setAppBarGradient()
            }
        } else {
            if (!fullScreen) {
                setAppBarGradient()
            }
        }
    }

    fun setHeaderTitle(title: String) {
        baseBinding.tvCenterTitleCustomActionBar.text = title
        baseBinding.tvSideTitleCustomActionBar.text = title
    }

    fun setTitlesVisibility(
        showSideTitle: Boolean,
        showCenterTitle: Boolean,
    ) {
        baseBinding.tvCenterTitleCustomActionBar.visibility =
            if (showCenterTitle) View.VISIBLE else View.GONE
        baseBinding.tvSideTitleCustomActionBar.visibility =
            if (showSideTitle) View.VISIBLE else View.GONE
    }

    fun setDrawHeader(
        showHeader: Boolean,
        title: String,
        showSideTitle: Boolean,
        showCenterTitle: Boolean,
        showBack: Boolean,
        showMenu: Boolean,
        showNotification: Boolean,
        showHomeButton: Boolean,
        appBarWhite: Boolean
    ) {
        setActionBarVisibilty(showHeader)
        setTitlesVisibility(showSideTitle, showCenterTitle)
        setBackIconVisibility(showBack, appBarWhite)
        setMenuIconVisibility(showMenu)
        setAnyIcon1Visibility(showNotification)
        setAnyIcon1NotifyVisibility(false)
        setHomeButtonVisibility(showHomeButton)
        setHeaderTitle(title)

        if (appBarWhite) {
            setAppBarlightAndStatusBarDark(R.color.white)
            baseBinding.tvSideTitleCustomActionBar.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorAccent
                )
            )
            baseBinding.tvCenterTitleCustomActionBar.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorAccent
                )
            )
        } else {
            setAppBarGradient()
            baseBinding.layoutContainerActionBar.setBackgroundResource(R.color.colorPrimary)
            baseBinding.tvSideTitleCustomActionBar.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            baseBinding.tvCenterTitleCustomActionBar.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
        }
    }

    fun setCenterTitleGravity(gravity: Int) {
        baseBinding.tvCenterTitleCustomActionBar.gravity = gravity
    }

    override fun onBackPressed() {
        if (showBack) super.onBackPressed()
        else if (showMenu) {
            if (baseBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                closeMenu()
            } else {
                super.onBackPressed()
            }
        } else
            super.onBackPressed()
    }

    var fragmentMenu = MenuFragment()
    fun updateMenuNamAndImage(name: String, image: String) {
//        fragmentMenu.binding.viewModel!!.name.value = name
//        fragmentMenu.binding.viewModel!!.imageURl.value = image
    }

    internal fun initializeSlideMenu() {
        // slide menu def
        if (supportFragmentManager.findFragmentByTag("menu") == null) supportFragmentManager.beginTransaction()
            .replace(
                R.id.menu,
                fragmentMenu,
                "menu"
            ).commit()

        baseBinding.drawerLayout.addDrawerListener(object :
            androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerStateChanged(newState: Int) {
                if (newState == androidx.drawerlayout.widget.DrawerLayout.STATE_IDLE) {
                    if (baseBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    } else {
                    }
                }
            }
        })
    }

    fun setDrawerState(isEnabled: Boolean) {
        if (isEnabled) {
            baseBinding.drawerLayout.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED)
        } else {
            baseBinding.drawerLayout.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }

    fun closeMenu() {
        baseBinding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    fun openMenu() {
        baseBinding.drawerLayout.openDrawer(GravityCompat.START)
    }

    fun setAppBarlightAndStatusBarDark(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //make statusbar dark text and icons starting from KITKAT
            baseBinding.layoutContainerBaseActivity.systemUiVisibility =
                baseBinding.layoutContainerBaseActivity.systemUiVisibility or
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, color)
            window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
        }
        baseBinding.layoutContainerActionBar.setBackgroundResource(color)
        window.setBackgroundDrawableResource(R.color.black)
    }

    fun setAppBarGradient() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //make statusbar dark text and icons starting from KITKAT
            baseBinding.layoutContainerBaseActivity.systemUiVisibility =
                baseBinding.layoutContainerBaseActivity.systemUiVisibility and
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()

//            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
        //set gradient color
        baseBinding.layoutContainerActionBar.setBackgroundResource(R.drawable.gradient_end_red_black_bg)
        window.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.gradient_end_red_black_bg
            )
        )
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun forceRTLIfSupported() {
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun forceLTRIfSupported() {
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        super.applyOverrideConfiguration(
            LocaleHelper.applyOverrideConfiguration(
                baseContext,
                overrideConfiguration
            )
        )
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleHelper.updateLocale(newBase)
        )
    }

    internal val callPermissionRequest = 4

    var number = ""
    fun callMobile(Number: String?) {
        try {
            number = Number!!
            if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), callPermissionRequest)
                return
            }
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:$Number")
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                callIntent.setPackage("com.android.server.telecom")
            } else {
                callIntent.setPackage("com.android.phone")
            }
            startActivity(callIntent)
        } catch (e: Exception) {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:$Number")
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(callIntent)
        }
    }

    internal fun requestReceiveSMSPermission() {
        if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.RECEIVE_SMS),
                RequestCodeSMSPERMISSIONActivity
            )
            return
        }
    }

    fun hasPermissions(context: Context?, permissions: Array<String>): Boolean {
        if (context != null && permissions != null) {
            for (p in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        p
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            callPermissionRequest ->
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    callMobile(number)
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            else -> {
            }
        }
    }

    open fun finish_activity() {
        finish()
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_from_left_to_right)
    }

    fun hideKeyPad(view: View) {
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyPad(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun showHideProgressDialog(isShow: Boolean) {
        if (isShow)
            ProgressDialogLoading.show(this@BaseActivity)
        else
            ProgressDialogLoading.dismiss(this@BaseActivity)
    }

    fun showHideMessageDialog(isShow: Boolean, title: String, message: String) {
        if (isShow)
            showMessage(
                this@BaseActivity, title,
                message,
                object : OnAskUserAction {
                    override fun onPositiveAction() {
                    }

                    override fun onNegativeAction() {
                    }

                },
                false,
                getString(R.string.cancel),
                getString(R.string.ok),
                true
            )
        else
            ProgressDialogLoading.dismiss(this@BaseActivity)
    }

}