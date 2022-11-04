package com.freyapps.hearthstonedeckviewer.data.models.mappers

import android.util.Log
import com.freyapps.hearthstonedeckviewer.common.DAY_MONTH_YEAR
import com.freyapps.hearthstonedeckviewer.common.convertDateToLong
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.local.ManacostDeckInfo
import org.jsoup.Jsoup
import javax.inject.Inject

class TopDecksParser @Inject constructor() {

    fun parseManacostDecks(
        input: String,
        hearthstoneClass: HearthstoneClass
    ): List<ManacostDeckInfo> {
        val result: MutableList<ManacostDeckInfo> = arrayListOf()
        val document = Jsoup.parse(input)
        val decks = document.select("div.deck")
        for (element in decks) {
            val code = element.children().select("input").first()?.attr("value")
            if (code.isNullOrEmpty()) {
                Log.d(TAG, "skipped deck: code empty")
                continue
            }
            with(element.children()) {
                val deck = ManacostDeckInfo(
                    hearthstoneClass = hearthstoneClass,
                    name = select("h3.title").first()?.text() ?: "",
                    code = code,
                    likes = select("div.like").first()?.text()?.toInt() ?: 0,
                    dislikes = select("div.dislike").first()?.text()?.toInt() ?: 0,
                    date = select("h2.time").first()?.text()?.convertDateToLong(DAY_MONTH_YEAR) ?: 0
                )
                result.add(deck)
            }
        }
        return result
    }

    companion object {
        private const val TAG = "TopDecksParser"
    }
}