package com.example.project4

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray

// --------------------------------//
// CHANGE THIS TO BE YOUR API KEY  //
// --------------------------------//
//private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed" // codepath provided api key

/*
 * The class for the only fragment in the app, which contains the progress bar,
 * recyclerView, and performs the network calls to the NY Times API.
 */
class LatestMoviesFragment : Fragment(), OnListFragmentInteractionListener {

    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_latest_movies_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()
        Log.d("why", "update adapter start")

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        // Using the client, perform the HTTP request
        val params = RequestParams()
//        params["api-key"] = API_KEY
        params["limit"] = "5"
        params["page"] = "1"
//      client["https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
        client["https://api.themoviedb.org/3/movie/now_playing?api_key=724ff4a5905f67a36b8726f9165330e6",
                params,
                object : JsonHttpResponseHandler()
//                object : TextHttpResponseHandler()
                {
                    /*
                     * The onSuccess function gets called when
                     * HTTP response status is "200 OK"
                     */
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        //TODO - Parse JSON into Models


                        // Look for this in Logcat:
                        Log.d("LatestMoviesFragment", "response successful")

                        // Filter the JSON
//                        val resultsJSON : Int = json.jsonObject.get("page") as Int

                        val resultsJSON : JSONArray = json.jsonObject.get("results") as JSONArray

//                        val moviesRawJSON : String = resultsJSON.get("title").toString()
                        val moviesRawJSON : String = resultsJSON.toString()

                        Log.d("LatestMoviesFragment", resultsJSON.toString())


//                        // Using gson to find JSON keys that match @SerializedName tags in model class and fill that data into model objects
                        val gson = Gson()
                        val arrayMovieType = object : TypeToken<List<LatestMovie>>() {}.type
//
                        val models : List<LatestMovie>? = gson.fromJson(moviesRawJSON, arrayMovieType)
//                        val models : List<LatestMovie>? = null
                        recyclerView.adapter = models?.let { LatestMoviesRecyclerViewAdapter(it, this@LatestMoviesFragment) }


                    }

                    /*
                     * The onFailure function gets called when
                     * HTTP response status is "4XX" (eg. 401, 403, 404)
                     */
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        // If the error is not null, log it!
                        t?.message?.let {
//                            Log.e("why", statusCode.toString())

                            Log.e("LatestMoviesFragment", errorResponse)
                        }
                    }
                }]


    }

    /*
     * What happens when a particular book is clicked.
     */
    override fun onItemClick(item: LatestMovie) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }

}