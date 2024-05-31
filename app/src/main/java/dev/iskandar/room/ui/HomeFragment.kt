package dev.iskandar.room.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.iskandar.room.R
import dev.iskandar.room.adapters.RvAdapter
import dev.iskandar.room.databinding.FragmentHomeBinding
import dev.iskandar.room.db.AppDatabase
import dev.iskandar.room.models.User

class HomeFragment : Fragment(), RvAdapter.RvAction {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var appDatabase: AppDatabase
    private lateinit var rvAdapter: RvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            appDatabase = AppDatabase.getInstance(requireContext())
            onResume()
            add.setOnClickListener {
                findNavController().navigate(R.id.addFragment)
            }
        }

        return binding.root
    }

    override fun moreClick(user: User, position: Int, imageView: ImageView) {
        val popupMenu = PopupMenu(requireContext(), imageView)
        popupMenu.inflate(R.menu.my_menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_edit -> {
                    findNavController().navigate(R.id.addFragment, bundleOf("keyUser" to user))
                }

                R.id.menu_delete -> {
                    appDatabase.myDao().deleteUser(user)
                    onResume()
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        rvAdapter = RvAdapter(appDatabase.myDao().getAllUsers() as ArrayList, this@HomeFragment)
        binding.rv.adapter = rvAdapter
    }
}