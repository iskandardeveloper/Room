package dev.iskandar.room.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.iskandar.room.databinding.FragmentAddBinding
import dev.iskandar.room.db.AppDatabase
import dev.iskandar.room.models.User

class AddFragment : Fragment() {

    private val binding by lazy { FragmentAddBinding.inflate(layoutInflater) }
    private lateinit var appDatabase: AppDatabase
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        appDatabase = AppDatabase.getInstance(binding.root.context)

        try {
            user = arguments?.getSerializable("keyUser") as User
            editUser()
        } catch (e: Exception) {
            addUser()
        }

        return binding.root
    }

    private fun addUser() {
        binding.apply {
            buttonSave.setOnClickListener {
                user = User(editName.text.toString(), editNumber.text.toString())
                appDatabase.myDao().addUser(user!!)
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun editUser() {
        binding.apply {
            editName.setText(user?.name)
            editNumber.setText(user?.number)

            buttonSave.setOnClickListener {
                user?.name = editName.text.toString()
                user?.number = editNumber.text.toString()
                appDatabase.myDao().editUser(user!!)
                Toast.makeText(context, "Edited", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }
}