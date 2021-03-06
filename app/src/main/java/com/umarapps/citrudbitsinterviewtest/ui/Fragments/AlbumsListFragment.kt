package com.umarapps.citrudbitsinterviewtest.ui.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadrosid.svgloader.SvgLoader
import com.umarapps.citrudbitsinterviewtest.Adapters.AlbumsListAdapter
import com.umarapps.citrudbitsinterviewtest.Adapters.UsersListAdapter
import com.umarapps.citrudbitsinterviewtest.R
import com.umarapps.citrudbitsinterviewtest.databinding.FragmentAlbumsListBinding
import com.umarapps.citrudbitsinterviewtest.viewmodels.AlbumsListViewModel
import com.umarapps.citrudbitsinterviewtest.viewmodels.UsersListViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumsListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var userId:Int=0;
    private lateinit var mAdapter: AlbumsListAdapter
    private lateinit var mViewModel: AlbumsListViewModel
    private lateinit var mFragmentBinding: FragmentAlbumsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userId= getArguments()?.getInt("UserId")!!;
        Log.i("User_Id","is :"+userId);
        mFragmentBinding= DataBindingUtil.inflate(
            inflater, R.layout.fragment_albums_list, container, false);
        return mFragmentBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(AlbumsListViewModel::class.java)

        mFragmentBinding.viewModel = mViewModel
        mFragmentBinding.lifecycleOwner = this

        initializeRecyclerView()
        initializeObservers()
    }
    private fun initializeRecyclerView() {
        mAdapter = AlbumsListAdapter()
        mAdapter.onItemClick={
                AlbumItem ->
            Log.i("User_email", "is: "+AlbumItem.id)
            val bundle = Bundle()
            bundle.putInt("albumId",AlbumItem.id)
            val fragment = PhotosListFragment()
            fragment.arguments=bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                ?.commit()
        }
        mFragmentBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun initializeObservers() {
        mViewModel.fetchCountriesFromServer(false,userId).observe(this, Observer { kt ->
            mAdapter.setData(kt)
        })
        mViewModel.mShowApiError.observe(this, Observer {
            Log.i("error_msg","is: "+it);
            AlertDialog.Builder(activity!!).setMessage(it).show()
        })
        mViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                mFragmentBinding.progressBar.visibility = View.VISIBLE

            } else {
                mFragmentBinding.progressBar.visibility = View.GONE

            }
        })
        mViewModel.mShowNetworkError.observe(this, Observer {
            AlertDialog.Builder(activity!!).setMessage(R.string.app_no_internet_msg).show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
       // SvgLoader.pluck().close()
    }
}