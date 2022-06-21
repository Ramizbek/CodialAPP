package ramizbek.aliyev.codialapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ramizbek.aliyev.codialapp.R
import ramizbek.aliyev.codialapp.databinding.ItemStudentsBinding
import ramizbek.aliyev.codialapp.models.Students

class StudentsAdapter(
    var context: Context,
    val arrayList: ArrayList<Students>,
    var rvClickStudents: RVClickStudents
) : RecyclerView.Adapter<StudentsAdapter.VH>() {
    inner class VH(var itemView: ItemStudentsBinding) : RecyclerView.ViewHolder(itemView.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(students: Students) {
            itemView.findViewById<TextView>(R.id.tv_name).text =
                "${students.name} ${students.surname}"
            itemView.findViewById<TextView>(R.id.tv_date).text = students.day
            itemView.findViewById<CardView>(R.id.card_edit).setOnClickListener {
                rvClickStudents.editStudents(students)
            }
            itemView.findViewById<CardView>(R.id.card_delete).setOnClickListener {
                rvClickStudents.deleteStudents(students)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemStudentsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(arrayList[position])
    }

    override fun getItemCount(): Int = arrayList.size

    interface RVClickStudents {
        fun editStudents(students: Students) {}
        fun deleteStudents(students: Students) {}
    }
}