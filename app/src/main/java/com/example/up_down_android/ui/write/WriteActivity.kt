package com.example.up_down_android.ui.write

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.example.up_down_android.base.BaseActivity
import com.example.up_down_android.databinding.ActivityWriteBinding
import com.google.gson.JsonObject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class WriteActivity : BaseActivity() {

    private val writeViewBinding: ActivityWriteBinding by lazy {
        ActivityWriteBinding.inflate(layoutInflater)
    }

    private val id: Int by lazy {
        intent.getIntExtra("id", 0)
    }

    private val comment: String by lazy {
        intent.getStringExtra("comment") ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(writeViewBinding.root)
        writeViewBinding.icBack.setOnClickListener { finish() }

        writeViewBinding.editCommunity.addTextChangedListener {
            writeViewBinding.textWrite.isEnabled = it?.toString()?.isEmpty() != true
        }

        if (id > 0) writeViewBinding.editCommunity.setText(comment)

        writeViewBinding.textWrite.setOnClickListener {
            if (id == 0) postCommunity() else patchCommunity()
        }
    }

    private fun postCommunity() {
        val body = JsonObject().apply {
            addProperty("content", writeViewBinding.editCommunity.text.toString())
        }
        updownServiceUtil.postCommunity(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ finish() }, {}).addTo(compositeDisposable)
    }

    private fun patchCommunity() {
        val body = JsonObject().apply {
            addProperty("content", writeViewBinding.editCommunity.text.toString())
        }
        updownServiceUtil.patchCommunity(id, body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ finish() }, {}).addTo(compositeDisposable)
    }
}