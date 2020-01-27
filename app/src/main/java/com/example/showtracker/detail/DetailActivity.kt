package com.example.showtracker.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.showtracker.R
import com.example.showtracker.model.Show
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.btnConfirm
import kotlinx.android.synthetic.main.activity_detail.toolbar
import kotlinx.android.synthetic.main.content_detail.*
import java.lang.Exception
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        initViews()
    }

    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        val bundle = intent.extras
        if (bundle != null) {
            val show: Show = bundle.get("Show") as Show
            showTitle.setText(show.title)
            showRating.setText(show.rating)
            showReminder.text = show.notification.toString()
            // add todo
            btnConfirm.setOnClickListener { show.id?.let { it1 -> onUpdateClick(it1) } }
        }
    }

    private fun onUpdateClick(showId: Long) {
        detailViewModel.updateShow(
            Show(
                showId,
                showTitle.text.toString(),
                showRating.text.toString(),
                Date(showReminder.text.toString())
                // add todo
            ))
        val snackbar = Snackbar.make(detailContent, getString(R.string.item_updated,
            showTitle.text.toString()), Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    private fun onDeleteClick(show: Show) {
        detailViewModel.deleteShow(show)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return try {
            finish()
            true
        } catch (e: Exception) {
            false
        }
    }
}