package ramizbek.aliyev.codialapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import ramizbek.aliyev.codialapp.databinding.FragmentAddGroupsBinding
import ramizbek.aliyev.codialapp.db.MyDBHelper
import ramizbek.aliyev.codialapp.models.Groups
import ramizbek.aliyev.codialapp.models.Mentors
import ramizbek.aliyev.codialapp.utils.MyObject
import ramizbek.aliyev.codialapp.utils.MyObject.courses
import ramizbek.aliyev.codialapp.utils.MyObject.mentors

class AddGroupsFragment : Fragment() {
    lateinit var binding: FragmentAddGroupsBinding
    lateinit var myDBHelper: MyDBHelper
    lateinit var arrayListMentors: ArrayList<Mentors>
    lateinit var arrayListMentorsString: ArrayList<String>
    lateinit var arrayListTime: ArrayList<String>
    lateinit var arrayListDay: ArrayList<String>
    lateinit var arrayAdapterTimes: ArrayAdapter<String>
    lateinit var arrayAdapterDays: ArrayAdapter<String>
    lateinit var arrayAdapterMentors: ArrayAdapter<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadData()
        binding.spinnerTimes.setAdapter(arrayAdapterTimes)
        binding.spinnerDays.setAdapter(arrayAdapterDays)
        binding.spinnerMentors.setAdapter(arrayAdapterMentors)

        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.spinnerMentors.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                mentors = arrayListMentors[position]
            }
        }

        binding.imageSave.setOnClickListener {
            if (binding.edtGroupsName.text.toString().trim()
                    .isNotEmpty() && binding.spinnerMentors.text.toString().trim()
                    .isNotEmpty() && binding.spinnerTimes.text.toString().trim().isNotEmpty()
                && binding.spinnerDays.text.toString().trim().isNotEmpty()
            ) {
                saveGroups()
            }
        }

        return binding.root
    }

    private fun saveGroups() {
        val groupName = binding.edtGroupsName.text.toString().trim()
        val groupMentor = mentors
        val groupTime = binding.spinnerTimes.text.toString().trim()
        val groupDay = binding.spinnerDays.text.toString().trim()
        val groupCourses = courses
        val open = false
        val groups = Groups(
            groupName, groupMentor, groupTime, groupDay, groupCourses, open
        )
        val boolean = myDBHelper.createGroups(groups, requireActivity())
        if (boolean) {
            binding.edtGroupsName.text!!.clear()
            binding.spinnerMentors.text.clear()
            binding.spinnerTimes.text.clear()
            binding.spinnerDays.text.clear()
        }
    }


    private fun loadData() {
        binding = FragmentAddGroupsBinding.inflate(layoutInflater)
        myDBHelper = MyDBHelper(requireActivity())
        arrayListMentors = ArrayList()
        arrayListMentorsString = ArrayList()
        arrayListTime = ArrayList()
        arrayListDay = ArrayList()
        arrayListTime.add("10:00 - 12:00")
        arrayListTime.add("12:00 - 14:00")
        arrayListTime.add("14:00 - 16:00")
        arrayListTime.add("16:00 - 18:00")
        arrayListTime.add("18:00 - 20:00")
        arrayListDay.add("Monday/Wednesday/Friday")
        arrayListDay.add("Tuesday/Thursday/Saturday")

        arrayListMentors = myDBHelper.getAllMentorsByID(courses.id!!)
        for (i in 0 until arrayListMentors.size) {
            arrayListMentorsString.add("${arrayListMentors[i].name!!} ${arrayListMentors[i].surname!!}")
        }

        arrayAdapterTimes = ArrayAdapter(requireActivity(), R.layout.item_spinner, arrayListTime)
        arrayAdapterDays = ArrayAdapter(requireActivity(), R.layout.item_spinner, arrayListDay)
        arrayAdapterMentors =
            ArrayAdapter(requireActivity(), R.layout.item_spinner, arrayListMentorsString)
    }
}