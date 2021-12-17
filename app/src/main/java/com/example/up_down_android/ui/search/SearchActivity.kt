package com.example.up_down_android.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.up_down_android.R
import com.example.up_down_android.adapter.CompanyAdapter
import com.example.up_down_android.base.BaseActivity
import com.example.up_down_android.databinding.ActivitySearchBinding
import com.example.up_down_android.ui.detail.DetailActivity
import com.example.up_down_android.ui.main.MainActivity
import com.google.gson.Gson
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchActivity : BaseActivity() {

    private val searchViewBinding : ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private val companyAdapter = CompanyAdapter(){
        startActivity(Intent(this,DetailActivity::class.java).putExtra("value",Gson().toJson(it)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(searchViewBinding.root)

        searchViewBinding.listCompany.adapter = companyAdapter
        searchViewBinding.textCancle.setOnClickListener { finish() }

        searchViewBinding.editCompany.textChanges()
            .debounce(200,TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.isEmpty()) return@subscribe
                else drawSearchCompany(it.toString())
            },{}).addTo(compositeDisposable)
    }

    private fun drawSearchCompany(query : String){
        updownServiceUtil.getCompany(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                companyAdapter.companyModel = it
            }, {
            }).addTo(compositeDisposable)
    }
}