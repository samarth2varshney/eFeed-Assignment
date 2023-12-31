package com.example.assignment

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.dataclass.GitIssues
import com.example.assignment.dataclass.GitIssuesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MainActivityViewModel : ViewModel() {

    private val _myDataList = MutableLiveData<List<GitIssuesItem>>()
    val myDataList: LiveData<List<GitIssuesItem>> = _myDataList

    private val _selected = MutableLiveData<String>()
    val selected: LiveData<String> = _selected

    fun changeSelected(button:String){
        _selected.value = button
    }

    fun apicall(){
        ApiClient.service.getIssues(
            "hmkcode",
            "Android"
        ).enqueue(object : retrofit2.Callback<GitIssues> {
            override fun onResponse(call: Call<GitIssues>, response: Response<GitIssues>) {
                if (response.isSuccessful) {
                    val data = response.body() as GitIssues
                    _myDataList.value = data
                    Log.i("NW res is successful", "$data")
                } else {
                    Log.i("NW res isn't successful", "$response")
                }
            }
            override fun onFailure(call: Call<GitIssues>, t: Throwable) {
                Log.i("Cart request failed", "error ${t.message}")
            }
        })
    }

    fun apicallforclosed(){
        ApiClient.service.getIssuesclosed(
            "hmkcode",
            "Android"
        ).enqueue(object : retrofit2.Callback<GitIssues> {
            override fun onResponse(call: Call<GitIssues>, response: Response<GitIssues>) {
                if (response.isSuccessful) {
                    val data = response.body() as GitIssues
                    _myDataList.value = data
                    Log.i("NW res is successful", "$data")

                } else {
                    Log.i("NW res isn't successful", "$response")
                }
            }
            override fun onFailure(call: Call<GitIssues>, t: Throwable) {
                Log.i("Cart request failed", "error ${t.message}")
            }
        })
    }

    fun apicallforopen(){
        ApiClient.service.getIssuesopen(
            "hmkcode",
            "Android"
        ).enqueue(object : retrofit2.Callback<GitIssues> {
            override fun onResponse(call: Call<GitIssues>, response: Response<GitIssues>) {
                if (response.isSuccessful) {
                    val data = response.body() as GitIssues
                    _myDataList.value = data
                    Log.i("NW res is successful", "$data")

                } else {
                    Log.i("NW res isn't successful", "$response")
                }
            }
            override fun onFailure(call: Call<GitIssues>, t: Throwable) {
                Log.i("Cart request failed", "error ${t.message}")
            }
        })
    }

}