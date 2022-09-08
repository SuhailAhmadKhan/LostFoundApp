package com.myshoppal.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A data model class for Product with required fields.
 */
@Parcelize
data class Item(
    val user_id: String = "",
    val user_name: String = "",
    val description: String = "",
    val location: String = "",
    val ContactDetails: String = "",
    val image: String = "",
    var item_id:String="",
) : Parcelable