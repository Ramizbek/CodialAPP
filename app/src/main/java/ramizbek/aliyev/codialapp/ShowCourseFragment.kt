package ramizbek.aliyev.codialapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import android.view.ViewGroup
import android.widget.Toast
import ramizbek.aliyev.codialapp.databinding.DialogDeleteBinding
import ramizbek.aliyev.codialapp.databinding.FragmentShowCourseBinding
import ramizbek.aliyev.codialapp.db.MyDBHelper
import ramizbek.aliyev.codialapp.utils.MyObject.courses

class ShowCourseFragment : Fragment() {
    private lateinit var binding: FragmentShowCourseBinding
    lateinit var bindingDelete: DialogDeleteBinding
    lateinit var myDBHelper: MyDBHelper
    lateinit var dialogDelete: AlertDialog
    private var booleanAntiBag = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentShowCourseBinding.inflate(layoutInflater)
        myDBHelper = MyDBHelper(requireActivity())
        binding.tvAllCourseName.text = courses.name
        binding.tvDescription.text = courses.description
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imageDelete.setOnClickListener {
            if (booleanAntiBag) {
                buildDeleteDialog()
                booleanAntiBag = false
            }
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun buildDeleteDialog() {
        val alertDialog = AlertDialog.Builder(requireActivity())
        bindingDelete = DialogDeleteBinding.inflate(requireActivity().layoutInflater)

        bindingDelete.tvDescription.text =
            "Do you want to delete this course? , if you delete a course, the mentors, groups, and students associated with it will also be deleted!"

        bindingDelete.tvCancel.setOnClickListener {
            dialogDelete.cancel()
        }

        bindingDelete.tvDelete.setOnClickListener {
            val boolean = myDBHelper.deleteCourses(courses)
            if (boolean) {
                Toast.makeText(activity, "Successfully Delete!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Failed to Delete", Toast.LENGTH_SHORT).show()
            }
            dialogDelete.cancel()

            findNavController().popBackStack()
        }

        alertDialog.setOnCancelListener {
            booleanAntiBag = true
        }
        alertDialog.setView(bindingDelete.root)
        dialogDelete = alertDialog.create()
        dialogDelete.window!!.attributes.windowAnimations = R.style.MyAnimation
        dialogDelete.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogDelete.show()

    }

}