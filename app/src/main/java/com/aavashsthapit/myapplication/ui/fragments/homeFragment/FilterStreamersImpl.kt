package com.aavashsthapit.myapplication.ui.fragments.homeFragment

import com.aavashsthapit.myapplication.data.entity.StreamerViewModel
import com.aavashsthapit.myapplication.data.repo.StreamerRepo
import java.util.*
import javax.inject.Inject

class FilterStreamersImpl @Inject constructor(
    val repo: StreamerRepo
) : FilterStreamers {

    override fun searchStreamers(query: String?): List<StreamerViewModel> {
        repo
        query?.let {
            return if (query.isNotEmpty()) {
                val tempList: List<StreamerViewModel> = if (this.repo.getAllStreamers().isEmpty()) {
                    repo.getTestStreamersRe().filter {
                        it.display_name.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))
                    }
                } else {
                    repo.getAllStreamers().filter {
                        it.display_name.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))
                    }
                }
                tempList
            } else {
                repo.getAllStreamers()
            }
        }
        return repo.getAllStreamers()
    }
}
