package com.mbt925.realestate.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbt925.realestate.feature.api.RealEstatesModel
import com.mbt925.realestate.feature.api.models.RealEstate
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
    private val model: RealEstatesModel,
) : ViewModel() {

    private var job: Job? = null
    val state = model.state

    fun fetchListings() {
        job?.cancel()
        job = viewModelScope.launch {
            model.fetchListings()
        }
    }

    fun getRealEstate(id: String): RealEstate? = state.value.listings
        ?.items
        ?.firstOrNull { it.id == id }

}
