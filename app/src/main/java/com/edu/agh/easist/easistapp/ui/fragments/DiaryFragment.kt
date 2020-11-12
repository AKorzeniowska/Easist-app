package com.edu.agh.easist.easistapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.ui.adapters.DiaryRowAdapter
import com.edu.agh.easist.easistapp.ui.models.DiaryRowModel
import kotlinx.android.synthetic.main.fragment_diary.*

class DiaryFragment : Fragment() {
    lateinit var diaryRowModels: ArrayList<DiaryRowModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_diary, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.diaryEntriesRecycler)
        diaryRowModels = ArrayList()
        diaryRowModels.add(DiaryRowModel(1, "12-02-2020", "jasdhk sdhjfsjdfjs sjdf jsdfhg sjdhgfjsdffsdfsdfsdf sdf sdf sdf", "XD"))
        diaryRowModels.add(DiaryRowModel(2, "15-10-2020", "msdfhksjdksdf sdfhskdjfksjdf sdjkfhskdjfhksjdfhksjdfksjdfhksdjfksjdfhksjhfkjsdfkjsdhfksjdhfkjsdhfksjhfksjdfhkjsdhf nsdfnhdsjf", "DX"))
        val adapter = DiaryRowAdapter(diaryRowModels)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}
