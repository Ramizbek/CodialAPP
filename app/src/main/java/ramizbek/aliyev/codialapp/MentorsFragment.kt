package ramizbek.aliyev.codialapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ramizbek.aliyev.codialapp.databinding.FragmentMentorsBinding
import ramizbek.aliyev.codialapp.utils.MyObject
import ramizbek.aliyev.codialapp.utils.MyObject.courses
import ramizbek.aliyev.codialapp.utils.ReadMentors


class MentorsFragment : Fragment() {
    lateinit var binding: FragmentMentorsBinding
    private lateinit var showMentors: ReadMentors
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        loadData()
        showWindow()
        setOnClick()

        return binding.root
    }

    private fun setOnClick() {
        binding.lyAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mentorsFragment_to_addMentorsFragment)
        }
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadData() {
        binding = FragmentMentorsBinding.inflate(layoutInflater)
        showMentors = ReadMentors(requireActivity(), binding)
    }

    private fun showWindow() {
        binding.tvCoursesName.text = courses.name
        showMentors.readMentors()
    }

}