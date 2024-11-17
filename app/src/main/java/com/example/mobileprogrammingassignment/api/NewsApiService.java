package com.example.mobileprogrammingassignment.api;

import com.example.mobileprogrammingassignment.model.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// This should be an interface, not a class.
public interface NewsApiService {
    // Define the API endpoint to get articles. This assumes the endpoint is `https://newsapi.org/v2/everything`
    @GET("v2/everything")  // The full endpoint
    Call<NewsResponse> getArticles(@Query("q") String query, @Query("apiKey") String apiKey);
}
