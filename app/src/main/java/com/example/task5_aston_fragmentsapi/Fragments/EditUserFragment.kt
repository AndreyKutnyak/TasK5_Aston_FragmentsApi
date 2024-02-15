package com.example.task5_aston_fragmentsapi.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.task5_aston_fragmentsapi.DataManager
import com.example.task5_aston_fragmentsapi.R
import com.example.task5_aston_fragmentsapi.User

class EditUserFragment : Fragment() {

    private lateinit var user: User
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneNumberEditText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_user, container, false)
        firstNameEditText = view.findViewById(R.id.firstNameEditText)
        lastNameEditText = view.findViewById(R.id.lastNameEditText)
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText)
        user = requireArguments().getParcelable(ARG_USER) ?: throw IllegalArgumentException("User must not be null")
        firstNameEditText.setText(user.firstName)
        lastNameEditText.setText(user.lastName)
        phoneNumberEditText.setText(user.phoneNumber)
        view.findViewById<Button>(R.id.saveButton).setOnClickListener {
            saveUserChanges()
        }
        return view
    }

    private fun saveUserChanges() {
        val firstName = firstNameEditText.text.toString()
        val lastName = lastNameEditText.text.toString()
        val phoneNumber = phoneNumberEditText.text.toString()
        val updatedUser = user.copy(firstName = firstName, lastName = lastName, phoneNumber = phoneNumber)

           DataManager.updateUser(updatedUser)
        setFragmentResult(REQUEST_KEY_EDIT_USER, bundleOf(KEY_UPDATED_USER to updatedUser))
        requireActivity().supportFragmentManager.popBackStack()
    }

    companion object {
        const val ARG_USER = "user"
        const val REQUEST_KEY_EDIT_USER = "request_key_edit_user"
        const val KEY_UPDATED_USER = "key_updated_user"
        fun newInstance(user: User) = EditUserFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_USER, user)
            }
        }
    }
}