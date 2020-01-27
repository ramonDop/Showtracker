package com.example.showtracker.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.showtracker.R
import com.example.showtracker.add.AddActivity
import com.example.showtracker.detail.DetailActivity
import com.example.showtracker.history.History
import com.example.showtracker.model.Show

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val shows = arrayListOf<Show>()
    private val showAdapter = MainAdapater(shows, onClick = {onItemClicked(it)})

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        rvShows.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        rvShows.adapter = showAdapter
        createItemTouchHelper().attachToRecyclerView(rvShows)

        val sortOpts = resources.getStringArray(R.array.sortOpts)
        val sortSpinner = findViewById<Spinner>(R.id.spnSort)

        if (sortSpinner != null) {
            val adapter = ArrayAdapter(this,
                R.layout.spinner_item, sortOpts)
            sortSpinner.adapter = adapter

            sortSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                    onSortClick(sortOpts[position])
                }
            }
        }

        fab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
        fab2.setOnClickListener {
            val intent = Intent (this, History::class.java)
            startActivity(intent)
        }
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        mainViewModel.allShows.observe(this, Observer { showList ->
            if (showList != null) {
                shows.clear()
                shows.addAll(showList)
                showAdapter.notifyDataSetChanged()

            }
        })
    }
    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val showToDelete = shows[position]
                mainViewModel.deleteShow(showToDelete)
                Snackbar.make(rvShows, "Succesfully deleted show", Snackbar.LENGTH_SHORT).show()
            }
        }
        return ItemTouchHelper(callback)
    }

    private fun onSortClick(sortProperty: String) {
        mainViewModel.allShows.observe(this, Observer { showList ->
            if (showList != null) {
                var sortedShows = showList
                if (sortProperty == "Rating") {
                    sortedShows = showList.sortedWith(compareBy(Show::rating))
                } else if (sortProperty == "Title") {
                    sortedShows = showList.sortedWith(compareBy(Show::title))
                }
                shows.clear()
                shows.addAll(sortedShows)
                showAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun onItemClicked(show: Show) {
        val intent = Intent(this, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("Show", show)
        intent.putExtras(bundle)
        startActivity(intent)

    }
}
