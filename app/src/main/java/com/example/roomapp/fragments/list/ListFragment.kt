package com.example.roomapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)



        // Recyclerview
        val adapter = ListAdapter()
        view.recyclerView.adapter = adapter


        // UserViewModel
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->

            adapter.setData(user)

        })

        // Button_AddUser ..
        view.Button_AddUser.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // to show the menu in this fragment ..
        setHasOptionsMenu(true)

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_Delete -> {deleteAllUser()}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUser() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Delete")
        builder.setMessage("Do you want to delete all Users ?")
        // Positive Button ..
        builder.setPositiveButton("Yes"){_,_ ->
            mUserViewModel.deleteAllUser()
            Toast.makeText(activity,"All Users are Deleted",Toast.LENGTH_SHORT).show()
        }
        // Negative Button ..
        builder.setNegativeButton("No"){_,_ -> }
        builder.show()

    }
}