package com.example.up_down_android.ui.main.info

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.up_down_android.databinding.FragmentInfoBinding
import com.example.up_down_android.databinding.FragmentStockBinding
import com.example.up_down_android.dialog.LogoutDialog
import com.example.up_down_android.ui.SplashActivity
import com.example.up_down_android.ui.history.HistoryActivity
import com.example.up_down_android.ui.profile.ProfileActivity
import com.example.up_down_android.ui.write.WriteActivity
import com.example.up_down_android.util.PrefUtil
import org.koin.android.ext.android.inject


class InfoFragment : Fragment() {
    private var _infoBinding : FragmentInfoBinding? = null
    val infoBinding get() = _infoBinding

    private val prefUtil : PrefUtil by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (_infoBinding == null) {
            _infoBinding =
                FragmentInfoBinding.inflate(layoutInflater, container, false)
        }
        return infoBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        infoBinding?.viewProfile?.setOnClickListener {
            requireActivity().startActivity(Intent(requireActivity(), ProfileActivity::class.java))
        }

        infoBinding?.viewWrite?.setOnClickListener {
            requireActivity().startActivity(Intent(requireActivity(), HistoryActivity::class.java))
        }

        infoBinding?.viewLogout?.setOnClickListener {
            LogoutDialog{
                prefUtil.logout()
                requireActivity().startActivity(Intent(requireActivity(),SplashActivity::class.java))
                requireActivity().finishAffinity()
            }.show(childFragmentManager,null)
        }
    }
}