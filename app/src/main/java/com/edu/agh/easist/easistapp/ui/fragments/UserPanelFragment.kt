package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.ui.adapters.PatientRowAdapter
import com.edu.agh.easist.easistapp.ui.models.PatientRowModel
import com.edu.agh.easist.easistapp.utils.openNewFragment
import kotlinx.android.synthetic.main.fragment_user_panel.*

class UserPanelFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_panel, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        setButtonFunctions()
    }

    private fun setButtonFunctions(){
        addSymptomButton.setOnClickListener{
            openNewFragment(activity, SymptomFormFragment(), addToBackStack = true)
        }
    }
}