package com.mbt925.realestate.service.local

import android.content.Context
import androidx.datastore.dataStore

val Context.listingResultDataSource by dataStore(
    fileName = "cache",
    serializer = ListingsResultDaoSerializer,
)
