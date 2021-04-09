package com.example.testapp.api

import com.example.testapp.data.CovidItem
import com.example.testapp.data.State
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET

interface StatesApi {

    @GET("state_district_wise.json")
    suspend fun getStatesData(): Map<String, State>
}

object StatesService {

    suspend fun getData(): List<CovidItem> {
        val items = mutableListOf<CovidItem>()
        api.getStatesData().filter { (name, _) -> name != "State Unassigned" }
            .forEach { (stateName, stateInfo) ->
                items += CovidItem.StateItem(stateName)
                stateInfo.districtData.forEach { (districtName, data) ->
                    items += CovidItem.DistrictItem(districtName, data)
                }
            }
        return items
    }


    private val api = Retrofit.Builder()
        .baseUrl("https://api.covid19india.org/")
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
        .build()
        .create<StatesApi>()
}