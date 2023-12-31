package com.example.assignment

import com.example.assignment.dataclass.GitIssues
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiEndPoints {

    @GET("repos/{username}/{projectname}/issues?state=all")
    fun getIssues(
        @Path("username") username: String,
        @Path("projectname") projectname: String
    ): Call<GitIssues>

    @GET("repos/{username}/{projectname}/issues?state=closed")
    fun getIssuesclosed(
        @Path("username") username: String,
        @Path("projectname") projectname: String
    ): Call<GitIssues>

    @GET("repos/{username}/{projectname}/issues?state=open")
    fun getIssuesopen(
        @Path("username") username: String,
        @Path("projectname") projectname: String
    ): Call<GitIssues>
}