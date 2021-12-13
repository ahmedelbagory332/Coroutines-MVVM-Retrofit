package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.getAllMovies()


       lifecycleScope.launch {

            viewModel.movieList.collect {movieList->
                Log.d("Bego", "movie list \n:$movieList ")
            }

            viewModel.errorMessage.collect { message->

                Log.d("Bego", "error messag :$message ")

            }
        }

    }



}