package com.example.loginandregister.fragments.homepage.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.loginandregister.models.ItemsModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.concurrent.TimeUnit

class SearchActivity : SearchFragment() {

    private lateinit var disposable: Disposable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()

        val textChangeStream = createTextChangeObservable()
            .toFlowable(BackpressureStrategy.BUFFER) // 2

        disposable = textChangeStream
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {  }
            .observeOn(Schedulers.io())
            .map { searchEngine.search(it) as List<ItemsModel> }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showResult(it)
            }
        Log.d("myTag", "This is my message")
    }

    private fun createTextChangeObservable(): Observable<String> {

        val textChangeObservable = Observable.create<String> { emitter ->

            val textWatcher = object : TextWatcher {

                override fun afterTextChanged(s: Editable?) = Unit

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    s?.toString()?.let { emitter.onNext(it) }
                }

            }

            search_box.addTextChangedListener(textWatcher)

            emitter.setCancellable {
                search_box.removeTextChangedListener(textWatcher)
            }
        }

        return textChangeObservable
            .filter { it.length >= 2 }
            .debounce(1000, TimeUnit.MILLISECONDS)

    }

}