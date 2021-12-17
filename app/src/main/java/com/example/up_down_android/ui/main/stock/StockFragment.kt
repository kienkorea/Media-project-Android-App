package com.example.up_down_android.ui.main.stock

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.up_down_android.R
import com.example.up_down_android.data.CompanyModel
import com.example.up_down_android.data.UpdownServiceUtil
import com.example.up_down_android.databinding.FragmentStockBinding
import com.example.up_down_android.ui.detail.DetailActivity
import com.example.up_down_android.ui.search.SearchActivity
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject

class StockFragment : Fragment() {
    private var _stockBinding: FragmentStockBinding? = null
    val stockBinding get() = _stockBinding

    val compositeDisposable = CompositeDisposable()
    val updownServiceUtil: UpdownServiceUtil by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (_stockBinding == null) {
            _stockBinding =
                FragmentStockBinding.inflate(layoutInflater, container, false)
        }
        return stockBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stockBinding?.editCompany?.setOnClickListener {
            requireActivity().startActivity(Intent(requireActivity(), SearchActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getComanyInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun getComanyInfo() {
        updownServiceUtil.getCompany(null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val data = it.filter { it.isBookmarked }
                stockBinding?.listLikeCompany?.removeAllViews()
                stockBinding?.listPopularCompany?.removeAllViews()
                drawLikeCompany(data)
                drawPopularCompany(it)
            }, {
            }).addTo(compositeDisposable)
    }

    private fun drawLikeCompany(value: List<CompanyModel>?) {
        if (value?.isNullOrEmpty() == true) {
            stockBinding?.textCompanyEmpty?.visibility = View.VISIBLE
        } else {
            stockBinding?.textCompanyEmpty?.visibility = View.GONE
            stockBinding?.listLikeCompany?.removeAllViews()

            value.forEach { model ->
                val inflater =
                    requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val stockView = inflater.inflate(R.layout.item_stock, null)

                stockView.findViewById<ImageView>(R.id.ic_heart).visibility = View.VISIBLE
                stockView.findViewById<TextView>(R.id.text_stock_title).text = model.name
                stockView.findViewById<TextView>(R.id.text_stock_growth).text =
                    model.changeRate + "%"
                stockView.findViewById<TextView>(R.id.text_stock_price).text = model.stockPrice + "원"

                if (model.changeRate.contains("-")) {
                    stockView.findViewById<TextView>(R.id.text_stock_growth)
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.color_down))
                } else {
                    stockView.findViewById<TextView>(R.id.text_stock_growth)
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.text_red))
                }
                val imageView = stockView.findViewById<ImageView>(R.id.ic_company)

                Glide.with(this)
                    .load(model.companyImageUrl)
                    .fitCenter()
                    .into(imageView)

                stockView.setOnClickListener {
                    requireActivity().startActivity(
                        Intent(
                            requireActivity(),
                            DetailActivity::class.java
                        ).putExtra("value", Gson().toJson(model))
                    )
                }

                stockBinding?.listLikeCompany?.addView(stockView)
            }
        }
    }

    private fun drawPopularCompany(value: List<CompanyModel>) {
        stockBinding?.listPopularCompany?.removeAllViews()

        value.forEach { mode ->
            val inflater =
                requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val stockView = inflater.inflate(R.layout.item_stock, null)

            if (mode.isBookmarked) stockView.findViewById<ImageView>(R.id.ic_heart).visibility =
                View.VISIBLE
            stockView.findViewById<TextView>(R.id.text_stock_title).text = mode.name
            stockView.findViewById<TextView>(R.id.text_stock_growth).text = mode.changeRate + "%"
            stockView.findViewById<TextView>(R.id.text_stock_price).text = mode.stockPrice + "원"

            val imageView = stockView.findViewById<ImageView>(R.id.ic_company)

            Glide.with(this)
                .load(mode.companyImageUrl)
                .fitCenter()
                .into(imageView)

            if (mode.changeRate.contains("-")) {
                stockView.findViewById<TextView>(R.id.text_stock_growth)
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.color_down))
            } else {
                stockView.findViewById<TextView>(R.id.text_stock_growth)
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.text_red))
            }

            stockView.setOnClickListener {
                requireActivity().startActivity(
                    Intent(
                        requireActivity(),
                        DetailActivity::class.java
                    ).putExtra("value", Gson().toJson(mode))
                )
            }

            stockBinding?.listPopularCompany?.addView(stockView)
        }
    }
}