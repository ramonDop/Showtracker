package com.example.showtracker.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.showtracker.R
import com.example.showtracker.main.MainActivity

import com.example.showtracker.model.Show
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.content_history.*


class History : AppCompatActivity() {


    private val shows = arrayListOf<Show>()
    private val showAdapater = HistoryAdapter(shows)

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        rvHistShows.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        rvHistShows.adapter = showAdapater

        createItemTouchHelper().attachToRecyclerView(rvHistShows)

        val sortOpts = resources.getStringArray(R.array.sortOpts)

        val sortSpinner = findViewById<Spinner>(R.id.spnHistSort)

        if (sortSpinner != null) {
            val adapter = ArrayAdapter(
                this,
                R.layout.spinner_item, sortOpts
            )
            sortSpinner.adapter = adapter

            sortSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    id: Long
                ) {
                    onSortClick(sortOpts[position])
                }
            }
        }

        fabhist.setOnClickListener {
            val intent = Intent(this, HistoryAddActivity::class.java)
            startActivity(intent)
        }
        fabhist2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViewModel() {
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)

        historyViewModel.allShows.observe(this, Observer { showList ->
            if (showList != null) {
                shows.clear()
                shows.addAll(showList)
                showAdapater.notifyDataSetChanged()

            }
        })
    }
    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
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
                historyViewModel.deleteShow(showToDelete)
                Snackbar.make(rvHistShows, "Succesfully deleted show", Snackbar.LENGTH_SHORT).show()
            }
        }
        return ItemTouchHelper(callback)
    }

    private fun onSortClick(sortProperty: String) {
        historyViewModel.allShows.observe(this, Observer { showList ->
            if (showList != null) {
                var sortedShows = showList
                if (sortProperty == "Rating") {
                    sortedShows = showList.sortedWith(compareBy(Show::rating))
                } else if (sortProperty == "Title") {
                    sortedShows = showList.sortedWith(compareBy(Show::title))
                }
                shows.clear()
                shows.addAll(sortedShows)
                showAdapater.notifyDataSetChanged()
            }
        })
    }
}
