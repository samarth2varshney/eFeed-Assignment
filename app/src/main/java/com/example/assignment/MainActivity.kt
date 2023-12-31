package com.example.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GitIssuesAdapter
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var allbutton: Button
    private lateinit var openedbutton: Button
    private lateinit var closedbutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        allbutton = binding.all
        closedbutton = binding.closed
        openedbutton = binding.open

        viewModel.myDataList.observe(this) {
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            adapter = GitIssuesAdapter(it)
            binding.recyclerView.adapter = adapter
        }

        if(viewModel.myDataList.value==null) {
            viewModel.apicall()
            viewModel.changeSelected("all")
        }

        when(viewModel.selected.value){
            "all" -> allbutton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue)
            "closed" -> closedbutton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue)
            "open" -> openedbutton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

        closedbutton.setOnClickListener {
            closedbutton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue)
            openedbutton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.off_white)
            allbutton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.off_white)
            viewModel.apicallforclosed()
            viewModel.changeSelected("closed")
        }

        allbutton.setOnClickListener {
            allbutton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue)
            closedbutton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.off_white)
            openedbutton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.off_white)
            viewModel.apicall()
            viewModel.changeSelected("all")
        }

        openedbutton.setOnClickListener {
            openedbutton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue)
            closedbutton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.off_white)
            allbutton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.off_white)
            viewModel.apicallforopen()
            viewModel.changeSelected("open")
        }

    }

}