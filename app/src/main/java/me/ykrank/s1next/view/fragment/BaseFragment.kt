package me.ykrank.s1next.view.fragment

import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.View
import com.github.ykrank.androidtools.ui.LibBaseFragment
import me.ykrank.s1next.App
import me.ykrank.s1next.data.User
import me.ykrank.s1next.data.api.UserValidator
import me.ykrank.s1next.util.ErrorUtil
import javax.inject.Inject

abstract class BaseFragment : LibBaseFragment() {

    @Inject
    internal lateinit var mUserValidator: UserValidator
    @Inject
    internal lateinit var mUser: User

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.appComponent.inject(this)
    }

    fun showRetrySnackbar(throwable: Throwable, onClickListener: View.OnClickListener) {
        val context = context ?: return
        showRetrySnackbar(ErrorUtil.parse(context, throwable), onClickListener)
    }
}
