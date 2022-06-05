package com.rickyandrean.herbapedia.ui.main.ui.home

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rickyandrean.herbapedia.R
import com.rickyandrean.herbapedia.databinding.FragmentHomeBinding
import com.rickyandrean.herbapedia.ui.main.MainActivity
import com.rickyandrean.herbapedia.ui.register.RegisterViewModel
import com.rickyandrean.herbapedia.ui.scan.ScanActivity

class HomeFragment : Fragment(), View.OnFocusChangeListener {
    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textInputSearch.onFocusChangeListener = this

        binding.cvHomeScan.setOnClickListener {
            val intent = Intent(requireActivity(), ScanActivity::class.java)
            startActivity(intent)
        }

        homeViewModel.plant.observe(viewLifecycleOwner) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFocusChange(v: View?, p1: Boolean) {
        if (v?.id == R.id.text_input_search) {
            binding.textInputSearch.onFocusChangeListener = null

            val navView: BottomNavigationView = requireActivity().findViewById(R.id.nav_view) as BottomNavigationView
            navView.menu.getItem(1).isChecked = true

            MainActivity.stack.add(1)
            MainActivity.searchAnimation = true

            val extras = FragmentNavigatorExtras(binding.cvHome to "plants", binding.cvHomeSearch to "plants_search", binding.cvHomeScan to "plants_scan")
            findNavController().navigate(
                R.id.action_navigation_home_to_navigation_plants,
                null,
                null,
                extras
            )
        }
    }
}