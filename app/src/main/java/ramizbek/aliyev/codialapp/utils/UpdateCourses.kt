package ramizbek.aliyev.codialapp.utils

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import ramizbek.aliyev.codialapp.R
import ramizbek.aliyev.codialapp.adapters.GroupsAdapter
import ramizbek.aliyev.codialapp.databinding.DialogEditGroupsBinding
import ramizbek.aliyev.codialapp.db.MyDBHelper
import ramizbek.aliyev.codialapp.models.Groups
import ramizbek.aliyev.codialapp.models.Mentors

class UpdateCourses(var activity: Activity, var groups: Groups) {
    lateinit var binding: DialogEditGroupsBinding
    lateinit var dialog: AlertDialog
    lateinit var myDBHelper: MyDBHelper
    lateinit var arrayListMentors: ArrayList<Mentors>
    private lateinit var arrayListMentorsString: ArrayList<String>
    lateinit var arrayListTime: ArrayList<String>
    lateinit var arrayListDay: ArrayList<String>
    lateinit var arrayAdapterTimes: ArrayAdapter<String>
    lateinit var arrayAdapterDays: ArrayAdapter<String>
    lateinit var arrayAdapterMentors: ArrayAdapter<String>

    fun buildDialog(groupsAdapter: GroupsAdapter, position: Int) {
        loadData(groups)
        val alertDialog = AlertDialog.Builder(activity)
        binding.apply {
            edtGroupsName.setText(groups.name)
            spinnerMentors.setText("${groups.mentors!!.name} ${groups.mentors!!.surname}")
            spinnerTimes.setText(groups.times)
            spinnerDays.setText(groups.days)
            spinnerTimes.setAdapter(arrayAdapterTimes)
            spinnerDays.setAdapter(arrayAdapterDays)
            spinnerMentors.setAdapter(arrayAdapterMentors)
            spinnerMentors.onItemClickListener = object : AdapterView.OnItemClickListener {
                override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    MyObject.mentors = arrayListMentors[position]
                }
            }
            tvCancel.setOnClickListener {
                dialog.cancel()
            }

            tvSave.setOnClickListener {
                if (edtGroupsName.text.toString().trim()
                        .isNotEmpty() && binding.spinnerMentors.text.toString().trim()
                        .isNotEmpty() && binding.spinnerTimes.text.toString().trim().isNotEmpty()
                    && binding.spinnerDays.text.toString().trim().isNotEmpty()
                ) {
                    val groupID = groups.id
                    val groupsName = binding.edtGroupsName.text.toString().trim()
                    val groupsMentor = MyObject.mentors
                    val groupsTime = binding.spinnerTimes.text.toString().trim()
                    val groupDay = binding.spinnerDays.text.toString().trim()
                    val groupCourses = MyObject.courses
                    val open = groups.open
                    val groupsAdd =
                        Groups(
                            groupID,
                            groupsName,
                            groupsMentor,
                            groupsTime,
                            groupDay,
                            groupCourses,
                            open
                        )
                    myDBHelper.updateGroups(groupsAdd, activity)
                    dialog.cancel()
                }
            }
            alertDialog.setView(binding.root)
            dialog = alertDialog.create()
            dialog.window!!.attributes.windowAnimations = R.style.MyAnimation
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
    }

    private fun loadData(groups: Groups) {
        binding = DialogEditGroupsBinding.inflate(activity.layoutInflater)
        myDBHelper = MyDBHelper(activity)
        arrayListMentors = ArrayList()
        arrayListMentorsString = ArrayList()
        MyObject.mentors = groups.mentors!!

        arrayListTime.add("10:00 - 12:00")
        arrayListTime.add("12:00 - 14:00")
        arrayListTime.add("14:00 - 16:00")
        arrayListTime.add("16:00 - 18:00")
        arrayListTime.add("18:00 - 20:00")
        arrayListDay.add("Duyshanba/Chorshanba/Juma")
        arrayListDay.add("Seshanba/Payshanba/Shanba")
        arrayListMentors = myDBHelper.getAllMentorsByID(MyObject.mentors.id!!)
        for (i in 0 until arrayListMentors.size) {
            arrayListMentorsString.add("${arrayListMentors[i].name!!} ${arrayListMentors[i].surname!!}")
        }
        arrayAdapterTimes = ArrayAdapter(activity, R.layout.item_spinner, arrayListTime)
        arrayAdapterDays = ArrayAdapter(activity, R.layout.item_spinner, arrayListDay)
        arrayAdapterTimes = ArrayAdapter(activity, R.layout.item_spinner, arrayListMentorsString)
    }
}
