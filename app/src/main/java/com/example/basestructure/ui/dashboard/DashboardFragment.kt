package com.example.basestructure.ui.dashboard

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basestructure.MyApplication
import com.example.basestructure.R
import com.example.basestructure.databinding.FragmentDashboardBinding
import com.example.basestructure.model.entities.User
import com.example.basestructure.model.preference.AppsPreferences
import com.example.basestructure.model.remote.AppClient
import com.example.basestructure.model.remote.AppService
import com.example.basestructure.model.repositories.AppDatabase
import com.example.basestructure.model.repositories.AppRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private lateinit var userAdapter: DashboardAdapter
    lateinit var json: AppService
    private val compositeDisposable = CompositeDisposable()
    private var _binding: FragmentDashboardBinding? = null
    private val dashboardViewModel: DashboardViewModel by lazy {
        val userdao = AppDatabase.getInstance(requireContext()).userDao()
        val remote = AppClient.apiInterface
        val appPreferences = AppsPreferences(requireContext())

        val appRepository = AppRepository(userdao, remote,appPreferences)
        ViewModelProviders.of(this, DashboardViewModelFactory(appRepository))
            .get(DashboardViewModel::class.java)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        view.listUser.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardViewModel.getALlUser().observe(viewLifecycleOwner) {
            it?.let { user ->
                run {
                    userAdapter = DashboardAdapter(user)
                    view.listUser.adapter = userAdapter
                }
            }
        }

        theme.setOnClickListener {
            val set = setOf<String>("test", "val")
            dashboardViewModel.savePreference(set)
        }

        dashboardViewModel.getUserFromApi()
    }

    private fun showInfo(l: List<User>?) {
        println("info11111 size = ${l?.size}")
        if (l != null) {
            for (l in l) {
                println("info11111 = ${l.status.toString()}")
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}