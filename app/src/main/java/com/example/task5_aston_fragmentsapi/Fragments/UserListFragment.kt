package com.example.task5_aston_fragmentsapi.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task5_aston_fragmentsapi.DataManager
import com.example.task5_aston_fragmentsapi.R
import com.example.task5_aston_fragmentsapi.User
import com.example.task5_aston_fragmentsapi.UserAdapter

class UserListFragment : Fragment() {
    private val userList = ArrayList<User>()
    private lateinit var adapter: UserAdapter
    private lateinit var recyclerView: RecyclerView
    private var isDataLoaded = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        if (!isDataLoaded) {
            DataManager.loadUsers(requireContext())
            userList.addAll(DataManager.getAllUsers())
            isDataLoaded = true
        }
        adapter = UserAdapter(userList) { user ->
            navigateToEditUser(user)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener(EditUserFragment.REQUEST_KEY_EDIT_USER, viewLifecycleOwner) { _, bundle ->
            val updatedUser = bundle.getParcelable<User>(EditUserFragment.KEY_UPDATED_USER)
            updateUserInList(updatedUser)
        }
    }

    private fun updateUserInList(updatedUser: User?) {
        updatedUser ?: return
        val index = userList.indexOfFirst { it.id == updatedUser.id }
        if (index != -1) {
            userList[index] = updatedUser
        } else {
            userList.add(updatedUser)
        }
        adapter.notifyDataSetChanged()
        saveUsers()
    }

    private fun saveUsers() {
        DataManager.saveUsers(requireContext(), userList)
    }

    private fun navigateToEditUser(user: User) {
        val editUserFragment = EditUserFragment.newInstance(user)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, editUserFragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance() = UserListFragment()
    }
}