package com.internshala.bookhub.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.app.VoiceInteractor
import android.content.Context
import android.content.Intent
import android.media.audiofx.BassBoost
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.bookhub.R
import com.internshala.bookhub.adapter.DashboardRecyclerAdapter
import model.Book
import okhttp3.Headers
import okhttp3.Interceptor
import org.json.JSONException
import util.ConnectionManager
import java.io.IOException


class DashboardFragment : androidx.fragment.app.Fragment() {
    lateinit var recyclerDashBoard:RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var btnCheckInternet:Button
    lateinit var recyclerAdapter:DashboardRecyclerAdapter
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar

 var bookInfoList = arrayListOf<Book>()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerDashBoard=view.findViewById(R.id.recyclerDashBoard)
        layoutManager= LinearLayoutManager(activity)
        recyclerAdapter=DashboardRecyclerAdapter(activity as Context,bookInfoList)
                recyclerDashBoard.adapter=recyclerAdapter
        recyclerDashBoard.layoutManager=layoutManager
        btnCheckInternet=view.findViewById(R.id.btnCheckInternet)
        progressLayout=view.findViewById(R.id.progressLayout)
        progressBar=view.findViewById(R.id.progressBar)
        progressBar.visibility=View.VISIBLE
        btnCheckInternet.setOnClickListener{
            if (ConnectionManager().checkConnectivity(activity as Context)){
                val dialog=AlertDialog.Builder(activity as Context)
                dialog.setTitle("success")
                dialog.setMessage("Internet Connection Found")
                dialog.setPositiveButton("ok"){text,listener->
                }
                dialog.setNegativeButton("cancel"){text,listener->

                }
                dialog.create()
                dialog.show()
            }
            else{ val dialog=AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Connection not Found")
                dialog.setPositiveButton("ok"){text,listener->
                }
                dialog.setNegativeButton("cancel"){text,listener->

                }
                dialog.create()
                dialog.show()

            }
        }
        recyclerDashBoard.addItemDecoration(
                DividerItemDecoration(
                        recyclerDashBoard.context,
                        (layoutManager as LinearLayoutManager).orientation
                )
        )
        val queue=Volley.newRequestQueue(activity as Context)
        val url="http://13.235.250.119/v1/book/fetch_books/"
        if (ConnectionManager().checkConnectivity(activity as Context)){
            val jsonObjectRequest= object:JsonObjectRequest(Request.Method.GET, url, null, com.android.volley.Response.Listener {
                      try {
                          progressLayout.visibility=View.GONE
                          val Success = it.getBoolean("success")
                          if (Success) {
                              val data = it.getJSONArray("data")
                              for (i in 0 until data.length()) {
                                  val bookJsonObject = data.getJSONObject(i)
                                  val bookObject = Book(
                                          bookJsonObject.getString("book_id"),
                                          bookJsonObject.getString("name"),
                                          bookJsonObject.getString("author"),
                                          bookJsonObject.getString("rating"),
                                          bookJsonObject.getString("price"),
                                          bookJsonObject.getString("image")

                                  )
                                  bookInfoList.add(bookObject)
                                  recyclerAdapter = DashboardRecyclerAdapter(activity as Context, bookInfoList)
                                  recyclerDashBoard.adapter = recyclerAdapter
                                  recyclerDashBoard.layoutManager = layoutManager
                                  recyclerDashBoard.addItemDecoration(
                                          DividerItemDecoration(
                                                  recyclerDashBoard.context,
                                                  (layoutManager as LinearLayoutManager).orientation
                                          )
                                  )


                              }


                          } else {
                              Toast.makeText(activity as Context, "some error occured!!", Toast.LENGTH_SHORT).show()
                          }

                      }catch(e: JSONException){
                          Toast.makeText(activity as Context,"Some error occured!!",Toast.LENGTH_SHORT).show()
                      }


            }, com.android.volley.Response.ErrorListener {
              Toast.makeText(activity as Context,"Some error occured!!",Toast.LENGTH_SHORT).show()

            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "9bf534118365f1"
                    return headers
                }

            }
            queue.add(jsonObjectRequest)

        }
        else{
            val dialog=AlertDialog.Builder(activity as Context)
            dialog.setTitle("error")
            dialog.setMessage("Internet connection not found")
            dialog.setPositiveButton("open setting"){ text,listner ->
                val settingIntent=Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()

            }
            dialog.setNegativeButton("Exit"){ text,listner->
                ActivityCompat.finishAffinity(activity as Activity)

            }
            dialog.create()
            dialog.show()
        }



        return view
    }


}


