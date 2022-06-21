package ramizbek.aliyev.codialapp.utils

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import ramizbek.aliyev.codialapp.R
import ramizbek.aliyev.codialapp.adapters.MentorsAdapter
import ramizbek.aliyev.codialapp.databinding.DialogDeleteBinding
import ramizbek.aliyev.codialapp.databinding.DialogEditMentorsBinding
import ramizbek.aliyev.codialapp.databinding.FragmentMentorsBinding
import ramizbek.aliyev.codialapp.db.MyDBHelper
import ramizbek.aliyev.codialapp.models.Mentors

class ReadMentors(private var activity: Activity, private var binding: FragmentMentorsBinding) {
    private lateinit var arrayListMentors: ArrayList<Mentors>
    private lateinit var mentorsAdapter: MentorsAdapter
    private lateinit var myDBHelper: MyDBHelper
    private lateinit var dialogEdit: AlertDialog
    private lateinit var dialogDelete: AlertDialog
    private lateinit var bindingEdit: DialogEditMentorsBinding
    private lateinit var bindingDelete: DialogDeleteBinding
    private var booleanAntiBug = true

    fun readMentors() {
        loadData()
        binding.recyclerMentors.adapter = mentorsAdapter
    }

    private fun loadData() {
        var arrayList = ArrayList<Mentors>()
        myDBHelper = MyDBHelper(activity)
        arrayListMentors = myDBHelper.readMentors()
        for (i in arrayListMentors) {
            if (i.courses!!.id == MyObject.courses.id) {
                arrayList.add(i)
            }
        }
        arrayListMentors = arrayList
        mentorsAdapter =
            MentorsAdapter(activity, arrayListMentors, object : MentorsAdapter.RVClickMentors {
                override fun onClickDelete(mentors: Mentors) {
                    if (booleanAntiBug) {
                        buildDialogDelete(mentors)
                        booleanAntiBug = false
                    }
                }

                override fun onClickEdit(mentors: Mentors) {
                    if (booleanAntiBug) {
                        buildDialogEdit(mentors)
                        booleanAntiBug = false
                    }
                }
            })
    }

    private fun buildDialogEdit(mentors: Mentors) {
        var alertDialog = AlertDialog.Builder(activity)
        bindingEdit = DialogEditMentorsBinding.inflate(activity.layoutInflater)
        bindingEdit.edtMentorsSurname.setText(mentors.surname)
        bindingEdit.edtMentorsName.setText(mentors.name)
        bindingEdit.edtMentorsPatronymic.setText(mentors.patronymic)
        bindingEdit.tvCancel.setOnClickListener {
            dialogEdit.cancel()
        }
        bindingEdit.tvSave.setOnClickListener {
            val surname = bindingEdit.edtMentorsSurname.text.toString().trim()
            val name = bindingEdit.edtMentorsName.text.toString().trim()
            val patronymic = bindingEdit.edtMentorsPatronymic.text.toString().trim()

            if (surname.isNotEmpty() && name.isNotEmpty() && patronymic.isNotEmpty()) {
                myDBHelper.updateMentors(
                    Mentors(
                        mentors.id,
                        surname,
                        name,
                        patronymic,
                        MyObject.courses
                    ), activity
                )
                readMentors()
                bindingEdit.edtMentorsSurname.text!!.clear()
                bindingEdit.edtMentorsName.text!!.clear()
                bindingEdit.edtMentorsPatronymic.text!!.clear()
                dialogEdit.cancel()
            }
        }
        alertDialog.setOnCancelListener {
            booleanAntiBug = true
        }

        alertDialog.setView(bindingEdit.root)
        dialogEdit = alertDialog.create()
        dialogEdit.window!!.attributes.windowAnimations = R.style.MyAnimation
        dialogEdit.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogEdit.show()
    }

    private fun buildDialogDelete(mentors: Mentors) {
        val alertDialog = AlertDialog.Builder(activity)
        bindingDelete = DialogDeleteBinding.inflate(activity.layoutInflater)
        bindingDelete.tvDescription.setOnClickListener {
            dialogDelete.cancel()
        }

        bindingDelete.tvDelete.setOnClickListener {
            val boolean = myDBHelper.deleteMentors(mentors)
            if (boolean) {
                Toast.makeText(activity, "Successfully deleted :)", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Failed to delete", Toast.LENGTH_SHORT).show()
            }
            dialogDelete.cancel()
            readMentors()
        }

        alertDialog.setOnCancelListener {
            booleanAntiBug = true
        }

        alertDialog.setView(bindingDelete.root)
        dialogDelete = alertDialog.create()
        dialogDelete.window!!.attributes.windowAnimations = R.style.MyAnimation
        dialogDelete.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogDelete.show()
    }
}