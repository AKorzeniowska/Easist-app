package com.edu.agh.easist.easistapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edu.agh.easist.easistapp.ui.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            val fragment = SignInFragment()
            supportFragmentManager.beginTransaction().replace(
                R.id.container,
                fragment,
                fragment.javaClass.simpleName
            ).commit()
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigationDiary -> {
                val fragment = DiaryFragment()
                supportFragmentManager.beginTransaction().replace(
                    R.id.container,
                    fragment,
                    fragment.javaClass.simpleName
                )
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationStatistics -> {
                val fragment = StatisticsFragment()
                supportFragmentManager.beginTransaction().replace(
                    R.id.container,
                    fragment,
                    fragment.javaClass.simpleName
                )
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationDoctorInfo -> {
                val fragment = DoctorPanelFragment()
                supportFragmentManager.beginTransaction().replace(
                    R.id.container,
                    fragment,
                    fragment.javaClass.simpleName
                )
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationPatientInfo -> {
                val fragment = PatientPanelFragment()
                supportFragmentManager.beginTransaction().replace(
                    R.id.container,
                    fragment,
                    fragment.javaClass.simpleName
                )
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}