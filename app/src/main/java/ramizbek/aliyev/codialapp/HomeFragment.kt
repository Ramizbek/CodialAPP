package ramizbek.aliyev.codialapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import ramizbek.aliyev.codialapp.databinding.FragmentHomeBinding
import ramizbek.aliyev.codialapp.utils.MyObject
import ramizbek.aliyev.codialapp.utils.MyObject.booleanAddCourses
import ramizbek.aliyev.codialapp.utils.MyObject.navigationID
import ramizbek.aliyev.codialapp.utils.MyObject.tvAllCourseNames

class HomeFragment : Fragment() {
    //Binding
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        smsDialog1(container!!.context)
        binding = FragmentHomeBinding.inflate(layoutInflater)

        requireActivity().window.statusBarColor = Color.parseColor("#000014")
        setOnClick()

        return binding.root
    }

    private fun setOnClick() {
        binding.cardCourses.setOnClickListener {
            fragmentNavigation(
                "List Of All Courses",
                true,
                R.id.showCourseFragment
            )
        }
        binding.cardGroups.setOnClickListener {
            fragmentNavigation(
                "All Groups",
                false,
                R.id.groupsFragment
            )
        }
        binding.cardMentors.setOnClickListener {
            fragmentNavigation(
                "All Mentors",
                false,
                R.id.mentorsFragment
            )
        }

        binding.tvCodial.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://instagram.com/codialuz?igshid=YmMyMTA2M2Y=")
            )
            startActivity(intent)
        }

    }

    private fun fragmentNavigation(string: String, boolean: Boolean, navigationId: Int) {
        tvAllCourseNames = string
        booleanAddCourses = boolean
        navigationID = navigationId
        findNavController().navigate(R.id.courseFragment)


    }

    companion object dialogShow {
        lateinit var dialog: AlertDialog
        fun smsDialog(context: Context) {
            val alertDialog = AlertDialog.Builder(context, R.style.NewDialog)


            dialog = alertDialog.create()

            val dialogView =
                LayoutInflater.from(context).inflate(R.layout.main_dialog, null, false)
            dialog.setView(dialogView)

            dialog.show()
            SmsDialog().start()
        }

        fun dialogCancel() {
            dialog.cancel()
        }
    }
}

lateinit var dialog: AlertDialog
fun smsDialog1(context: Context) {
    val alertDialog = AlertDialog.Builder(context, R.style.NewDialog)


    dialog = alertDialog.create()

    val dialogView =
        LayoutInflater.from(context).inflate(R.layout.main_dialog, null, false)
    dialog.setView(dialogView)

    dialog.show()
    SmsDialog().start()

}

fun dialogCancel() {
    dialog.cancel()
}


class SmsDialog() : Thread() {
    override fun run() {
        super.run()
        sleep(2000)
        dialogCancel()
    }
}