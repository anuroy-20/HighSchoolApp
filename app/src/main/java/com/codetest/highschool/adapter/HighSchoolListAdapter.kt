package com.codetest.highschool.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codetest.highschool.R
import com.codetest.highschool.model.HighSchoolListItem

//Adapter class to display school list in custom ui--
class HighSchoolListAdapter(
    private val onClickListener: OnClickListener,
    private val mSchoolList: List<HighSchoolListItem>?,
) : RecyclerView.Adapter<HighSchoolListAdapter.SchoolViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        return SchoolViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_card,
                parent,
                false
            )
        )
    }

    //Getting list count
    override fun getItemCount(): Int {
        return mSchoolList?.size ?: 0
    }

    //Setting data by binding the custom ui for recycler view--
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.highSchoolName.text = mSchoolList?.get(position)?.school_name
        holder.totalStudents.text = "Total Students: " + mSchoolList?.get(position)?.total_students
        holder.satButton.setOnClickListener {
            mSchoolList?.get(position)?.school_name?.let { it1 -> onClickListener.onClick(it1) }
        }
    }

    //Click listener method call for button in custom ui
    class OnClickListener(val clickListener: (schoolName: String) -> Unit) {
        fun onClick(schoolName: String) = clickListener(schoolName)
    }


    class SchoolViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val highSchoolName: TextView = itemView.findViewById(R.id.high_school_name)
        val totalStudents: TextView = itemView.findViewById(R.id.school_data)
        val satButton: TextView = itemView.findViewById(R.id.sat_button)

    }

}
