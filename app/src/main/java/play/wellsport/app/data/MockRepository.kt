package play.wellsport.app.data

import android.annotation.SuppressLint
import android.content.Context
import play.wellsport.app.R

object MockRepository {
    private val imageList = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6,
        R.drawable.image7,
        R.drawable.image8,
        R.drawable.image9,
        R.drawable.image10,
        R.drawable.image11,
        R.drawable.image12,
        R.drawable.image13,
        R.drawable.image14,
        R.drawable.image15
    )

    fun getSportsNews(context: Context): List<SportsNews> {

        @SuppressLint("RestrictedApi")
        val headLineList = listOf(
            context.getString(R.string.news1),
            context.getString(R.string.news2),
            context.getString(R.string.news3),
            context.getString(R.string.news4),
            context.getString(R.string.news5),
            context.getString(R.string.news6),
            context.getString(R.string.news7),
            context.getString(R.string.news8),
            context.getString(R.string.news9),
            context.getString(R.string.news10),
            context.getString(R.string.news11),
            context.getString(R.string.news12),
            context.getString(R.string.news13),
            context.getString(R.string.news14),
            context.getString(R.string.news15)
        )

        val sportsNewsList = mutableListOf<SportsNews>()
        for(x in imageList.indices) {
            val sportsNews = SportsNews(
                title = headLineList[x].toString(),
                imageList[x]

            )
            sportsNewsList.add(sportsNews)
        }
        return sportsNewsList
    }
}