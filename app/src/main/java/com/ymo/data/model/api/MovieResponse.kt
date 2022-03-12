package com.ymo.data.model.api

import android.os.Parcelable
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize

data class MovieResponse(

    @field:SerializedName("dates")
    @field:TypeConverters(DatesConverter::class)
    val dates: Dates? = null,

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<MovieItem?>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)

@Parcelize
@Entity(tableName = "movie")
data class MovieItem(

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("original_language")
    val originalLanguage: String? = null,

    @field:SerializedName("original_title")
    val originalTitle: String? = null,

    @field:SerializedName("video")
    val video: Boolean? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("genre_ids")
    @field:TypeConverters(IntTypeConverter::class)
    val genreIds: List<Int?>? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("id")
    @PrimaryKey val id: Int,

    @field:SerializedName("adult")
    val adult: Boolean? = null,

    @field:SerializedName("vote_count")
    val voteCount: Int? = null,

    var isFavourite: Boolean? = null,

    var isPopular: Boolean? = null,

    var isUpcoming: Boolean? = null
):Parcelable

data class Dates(

    @field:SerializedName("maximum")
    val maximum: String? = null,

    @field:SerializedName("minimum")
    val minimum: String? = null
)

class IntTypeConverter {

    @TypeConverter
    fun saveIntList(list: List<Int>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun getIntList(list: String): List<Int> {
        return Gson().fromJson(
            list.toString(),
            object : TypeToken<List<Int?>?>() {}.type
        )
    }
}

class DatesConverter {

    @TypeConverter
    fun saveDates(dates: Dates): String? {
        return Gson().toJson(dates)
    }

    @TypeConverter
    fun getDates(dates: String): Dates {
        return Gson().fromJson(
            dates.toString(),
            object : TypeToken<Dates?>() {}.type
        )
    }
}
