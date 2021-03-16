package com.example.roomapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.lang.Exception

class UpdateFragment : Fragment() {

    // this args variable to save the data of current User ..
    private val args by navArgs<UpdateFragmentArgs>() // here we must add the compile Option and kotlin Option inside build gradle in android {} ..

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.updateFirstName_et.setText(args.currentUserArgs.firstName) // to
        view.updateLastName_et.setText(args.currentUserArgs.lastName)
        view.updateAge_et.setText(args.currentUserArgs.age.toString())

        // Button Update ..
        view.Button_Update.setOnClickListener {
            updateItem()
        }

        // to show the menu to this fragment ..
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_Delete -> { deleteItem() }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun deleteItem() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Delete")
        builder.setMessage("Do you want to delete ${args.currentUserArgs.firstName} ?")
        // Positive Button..
        builder.setPositiveButton("yes"){_,_ ->
            mUserViewModel.deleteUser(args.currentUserArgs) // here we pass all data of current user ..
            Toast.makeText(activity,"${args.currentUserArgs.firstName} is deleted",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        // Negative Button ..
        builder.setNegativeButton("NO"){_,_ -> }
        builder.show()

    }


    private fun updateItem(){
        try {
            val firstName = updateFirstName_et.text.toString()
            val lastName = updateLastName_et.text.toString()
//        val age = Integer.parseInt(updateAge_et.text.toString())
            val age = updateAge_et.text.toString()
            if(inputCheck(firstName, lastName, updateAge_et.text)){
                // Create User Object
                val updatedUser = User(args.currentUserArgs.id, firstName, lastName, age)
                // Update Current User
                mUserViewModel.updateUser(updatedUser)
                Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
                // Navigate Back
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }else{
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            }
        }catch (ex:Exception){
            Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
        }


    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

}