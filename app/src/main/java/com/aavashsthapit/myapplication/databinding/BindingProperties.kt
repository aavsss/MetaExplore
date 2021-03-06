package com.aavashsthapit.myapplication.databinding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import com.aavashsthapit.myapplication.other.Extensions.gone
import com.aavashsthapit.myapplication.other.Extensions.visible
import com.bumptech.glide.RequestManager
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BindingProperties @Inject constructor (
    val glide: RequestManager
) {

    @BindingAdapter("app:displayImage")
    fun displayImage(view: ImageView, urlThumbnail: String) {
        glide.load(urlThumbnail)
            .into(view)
    }

    @BindingAdapter("app:visible_or_gone")
    fun setVisibleIfOnline(view: View, isLive: Boolean) {
        if (isLive) view.visible() else view.gone()
    }

    @BindingAdapter("app:formatted_start_date")
    fun formatStartDate(view: TextView, startDate: String) {
        view.text = "Start time: ${convertISOTime(startDate)}"
    }

    @BindingAdapter("app:searchQueryListener")
    fun searchQueryListener(view: SearchView?, callback: ((String?) -> Unit)?) {
        val searchCallBack = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (callback != null) {
                    callback(newText)
                }
                return false
            }
        }
        view?.setOnQueryTextListener(searchCallBack)
    }

    private fun convertISOTime(dateString: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val date = try {
            format.parse(dateString)
        } catch (e: Exception) {
            println(e)
            format.parse("2021-21-05T09:27:37Z")
        }
        return date!!.toString()
    }
}
