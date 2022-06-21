package ramizbek.aliyev.codialapp.utils

import android.app.Activity
import androidx.navigation.NavController
import ramizbek.aliyev.codialapp.adapters.CoursesAdapter
import ramizbek.aliyev.codialapp.databinding.FragmentCourseBinding
import ramizbek.aliyev.codialapp.db.MyDBHelper
import ramizbek.aliyev.codialapp.models.Courses


class ReadCourses(
    private val activity: Activity,
    private val binding: FragmentCourseBinding,
    val findNavController: NavController
) {
    private lateinit var arrayListCourses: ArrayList<Courses>
    private lateinit var coursesAdapter: CoursesAdapter
    private lateinit var myDBHelper: MyDBHelper

    fun readCourses() {
        loadData()
        coursesAdapter = CoursesAdapter(arrayListCourses, object : CoursesAdapter.RVClickCourses {
            override fun onClick(courses: Courses) {
                MyObject.courses = courses
                findNavController.navigate(MyObject.navigationID)
            }
        })
        binding.recyclerCourses.adapter = coursesAdapter
    }

    private fun loadData() {
        arrayListCourses = ArrayList()
        myDBHelper = MyDBHelper(activity)
        arrayListCourses = myDBHelper.readCourses()
    }
}