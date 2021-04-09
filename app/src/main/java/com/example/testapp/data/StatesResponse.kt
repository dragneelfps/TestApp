package com.example.testapp.data

import kotlinx.serialization.Serializable

@Serializable
data class State(
    val districtData: Map<String, DistrictData>,
    val statecode: String
)

@Serializable
data class DistrictData(
    val notes: String,
    val active: Int,
    val confirmed: Int,
    val deceased: Int,
    val recovered: Int,
    val delta: Delta
)

@Serializable
data class Delta(
    val confirmed: Int,
    val deceased: Int,
    val recovered: Int
)


sealed class CovidItem(val name: String) {
    class StateItem(name: String) : CovidItem(name)
    class DistrictItem(name: String, val data: DistrictData) : CovidItem(name)
}