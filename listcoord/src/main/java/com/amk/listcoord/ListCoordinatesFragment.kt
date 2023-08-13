package com.amk.listcoord

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.amk.core.Result
import com.amk.listcoord.databinding.FragmentListCoordBinding
import com.amk.listcoord.presenter.ListCoordinatesViewModel
import com.amk.listcoord.ui.CoordinatesAdapter
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "coordinates"

@AndroidEntryPoint
class ListCoordinatesFragment : Fragment() {

//    private var param1: String? = null
    private lateinit var binding: FragmentListCoordBinding

    private val viewModel by viewModels<ListCoordinatesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        recyclerView = container?.findViewById(R.id.recyclerView)!!
        binding = FragmentListCoordBinding.inflate(inflater, container, false)
        val manager = LinearLayoutManager(this.context)
        binding.recyclerView.layoutManager = manager
        viewModel.state.observe(viewLifecycleOwner) { result ->
            Log.d("MKV2", "observe:result ${result} ")
            when (result) {
                is Result.Error -> {}
                is Result.Loading -> {}
                is Result.Success -> {
                    binding.recyclerView.adapter = CoordinatesAdapter(result.data)
                }
            }
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(/*param1: String*/) =
            ListCoordinatesFragment()/*.apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }*/
    }
}