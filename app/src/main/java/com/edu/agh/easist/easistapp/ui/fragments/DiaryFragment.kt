package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.DiaryEntry
import com.edu.agh.easist.easistapp.ui.adapters.DiaryRowAdapter
import com.edu.agh.easist.easistapp.ui.models.DiaryRowModel
import com.edu.agh.easist.easistapp.utils.*
import kotlinx.android.synthetic.main.fragment_diary.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber


class DiaryFragment : Fragment() {
    lateinit var diaryRowModels: ArrayList<DiaryRowModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_diary, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        showMenu(requireActivity(), true)
        progressBar.visibility = View.VISIBLE
        diaryEntryAddEditEntry.visibility = View.INVISIBLE
        if (getRole(activity) == ROLE_PATIENT) {
            getUserDiaryEntries(getUsername(activity)!!)
            setButtonFunctions()
            showToolbar(requireActivity(), false)
        } else {
            showToolbar(requireActivity(), true)
            setToolbar(requireActivity(), getBrowsedUserFullName(activity)!!, getBrowsedUser(activity))
            getUserDiaryEntries(getBrowsedUser(activity)!!)
        }
    }

    private fun setButtonFunctions(){
        diaryEntryAddEditEntry.setOnClickListener{
            openNewFragment(activity, DiaryFormFragmentFirst(), addToBackStack = true)
        }
    }

    private fun getUserDiaryEntries(username: String){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient.getDiaryEntries(getToken(activity)!!, username)
                Timber.d(response.toString())
                if (response.isSuccessful && response.body() != null) {
                    setDiaryEntries(response.body()!!)
                } else {
                    showToast(
                            activity,
                            "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                showToast(activity,
                        "Error while connecting: ${e.message}")
            }
        }
    }

    private fun setDiaryEntries(entries: Collection<DiaryEntry>){
        var existsTodayEntry = false
        val sorted = entries.toList().sortedWith(compareByDescending { it.date })
        diaryRowModels = ArrayList()
        for (entry in sorted){
            val rowModel = DiaryRowModel(entry.id!!, entry.date!!.toString(),
                    entry.content!!, entry.mood!!, entry.sleepEntry!!.hourCount!!)
            diaryRowModels.add(rowModel)
            if (parseDateToString(entry.date) == getCurrentDateAsString())
                existsTodayEntry = true
        }

        if (existsTodayEntry)
            diaryEntryAddEditEntry.setText(R.string.diary_edit_today_entry)
        progressBar.visibility = View.INVISIBLE

        if (getRole(activity) == ROLE_PATIENT)
            diaryEntryAddEditEntry.visibility = View.VISIBLE

        val adapter = DiaryRowAdapter(diaryRowModels, requireContext())
        diaryEntriesRecycler.adapter = adapter
        diaryEntriesRecycler.layoutManager = LinearLayoutManager(context)
        diaryEntriesRecycler.addItemDecoration(DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL))
    }
}
