package com.mpowloka.gsd.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.mpowloka.gsd.R
import com.mpowloka.gsd.common.GsdApplication
import com.mpowloka.gsd.common.NavigationComponent
import com.mpowloka.gsd.common.ViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user_details.*

class UserDetailsFragment : Fragment() {

    private lateinit var viewModel: UserDetailsViewModel

    private lateinit var userDetailsDisposable: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = activity?.application ?: return

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(application as GsdApplication)
        ).get(UserDetailsViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()

        userDetailsDisposable = viewModel.currentUserDetails
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { userDetails ->
                val navigationComponent = activity as? NavigationComponent
                navigationComponent?.setupActionBar(this, userDetails.login)


                login_tv.text = userDetails.login
                organization_tv.text = userDetails.organization
                Glide
                    .with(context ?: return@subscribe)
                    .load(userDetails.avatarUrl)
                    .into(user_picture_iv)
            }
    }

    override fun onPause() {
        super.onPause()

        userDetailsDisposable.dispose()
    }

    companion object {

        fun newInstance() = UserDetailsFragment()

    }

}