package com.example.basestructure.ui.dashboard

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.basestructure.R
import com.example.basestructure.model.entities.User

class DashboardAdapter(
    private var users: List<User>
) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
   // private val users = mutableListOf<User>()
    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        var name: TextView? = row.findViewById(R.id.nameUser)
        var id: TextView? = row.findViewById(R.id.idUser)
        var status: TextView? = row.findViewById(R.id.statusUser)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.items, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        setItemDetails(user, holder)

    }

    override fun getItemCount(): Int {
       return users.size

    }

    private fun setItemDetails(user: User, viewHolder: ViewHolder) {
        viewHolder.name?.text = user.name
        viewHolder.id?.text = user.id.toString()
        viewHolder.status?.text = user.status.toString()

    }

}