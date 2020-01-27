package com.example.showtracker.add

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.showtracker.R
import com.example.showtracker.model.Show
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.util.*

class AddActivity : AppCompatActivity() {

    private lateinit var addViewModel: AddViewModel
    private var reminder: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { onSubmitClick() }

        etShowReminder.setOnClickListener { onEditReminderClick() }

        etShowReminder.setOnFocusChangeListener { _: View?, hasFocus: Boolean ->
            if (hasFocus) {
                onEditReminderClick()
            }
        }
    }

    private fun initViewModel() {
        addViewModel = ViewModelProviders.of(this).get(AddViewModel::class.java)
    }

    private fun onSubmitClick() {
        val toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)

        when {
            etShowTitle.text.isNullOrBlank() -> {
                toast.setText(R.string.val_title)
                toast.show()
            }

            etShowEpisode.text.isNullOrBlank() -> {
                toast.setText(R.string.val_rating)
                toast.show()
            }
            else -> {
                addViewModel.insertShow(
                    Show(
                        null,
                        etShowTitle.text.toString(),
                        etShowEpisode.text.toString(),
                        reminder!!
                    //add todo
                    )
                )

                val snackbar = Snackbar.make(addContent, getString(R.string.item_added,
                    etShowTitle.text.toString()), Snackbar.LENGTH_LONG)
                snackbar.show()

                etShowTitle.text?.clear()
                etShowEpisode.text?.clear()
                etShowReminder.text?.clear()
// add todo


            }

        }
    }

    @Suppress("DEPRECATION")
    private fun onEditReminderClick() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)

        val calendarInstance = Calendar.getInstance()
        val year = calendarInstance.get(Calendar.YEAR)
        val month = calendarInstance.get(Calendar.MONTH)
        val day = calendarInstance.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, yearDatePicker, monthOfYear, dayOfMonth ->
                if (Date(yearDatePicker, monthOfYear, dayOfMonth, 0, 0)
                        .before(Date(year, month, day, 0, 0))) {
                    val toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
                    toast.setText(R.string.val_date_after_today)
                    toast.show()
                } else {
                    reminder =
                        Date(yearDatePicker, monthOfYear, dayOfMonth, 0, 0)
                    val reminderDateText = "${dayOfMonth}/${monthOfYear + 1}/${yearDatePicker}"
                    etShowReminder.setText(reminderDateText)
                }
            }, year, month, day
        )
        datePickerDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return try {
            finish()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}