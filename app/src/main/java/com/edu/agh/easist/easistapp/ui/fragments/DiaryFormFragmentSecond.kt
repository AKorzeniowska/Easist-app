package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.AuthApiConnector
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.DiaryEntry
import com.edu.agh.easist.easistapp.models.DiaryEntryData
import com.edu.agh.easist.easistapp.models.UserData
import com.edu.agh.easist.easistapp.ui.adapters.SymptomRowAdapter
import com.edu.agh.easist.easistapp.ui.models.SymptomRowModel
import com.edu.agh.easist.easistapp.utils.getToken
import com.edu.agh.easist.easistapp.utils.openNewFragment
import com.edu.agh.easist.easistapp.utils.saveToken
import kotlinx.android.synthetic.main.fragment_sign_up_first.signUpButton
import kotlinx.android.synthetic.main.fragment_sign_up_second.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber


class DiaryFormFragmentSecond : Fragment() {
    private lateinit var symptomRowModels: ArrayList<SymptomRowModel>
    private lateinit var diaryEntryData: DiaryEntryData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary_from_second, container, false)

        getBundle()

        val recycler = view.findViewById<RecyclerView>(R.id.symptomRecyclerView)
        symptomRowModels = ArrayList()
        symptomRowModels.add(SymptomRowModel(1, "Anxiety", 0))
        symptomRowModels.add(SymptomRowModel(2, "Jeszcze gorzej", 0))
        val adapter = SymptomRowAdapter(symptomRowModels)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun getBundle(){
        val bundle = arguments
        val diaryEntryData: DiaryEntryData = bundle!!.getSerializable("diaryEntry") as DiaryEntryData
        this.diaryEntryData = diaryEntryData
    }

    private fun sendDiaryEntrySubmitRequest(){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient.addDiaryEntry(getToken(activity)!!, diaryEntryData)
                Timber.d(response.toString())
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(context, R.string.info__save_successful, Toast.LENGTH_SHORT).show()
                    openNewFragment(activity, DiaryFragment())
                } else {
                    Toast.makeText(
                            activity,
                            "Error: ${response.message()}",
                            Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(activity,
                        "Error while connecting: ${e.message}",
                        Toast.LENGTH_LONG).show()
            }
        }
    }
}
