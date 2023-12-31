package com.example.assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.dataclass.GitIssuesItem

class GitIssuesAdapter(private val originalList: List<GitIssuesItem>) :
    RecyclerView.Adapter<GitIssuesAdapter.ViewHolder>(), Filterable {

    private var filteredList: List<GitIssuesItem> = originalList.toList()

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString().trim().toLowerCase()

                filteredList = if (charString.isEmpty()) {
                    originalList
                } else {
                    originalList.filter {
                        it.title.contains(charString) || it.user.login.contains(charString) || it.created_at.contains(charString)
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as? List<GitIssuesItem> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val name = itemView.findViewById<TextView>(R.id.name)
        val image = itemView.findViewById<ImageView>(R.id.imageView)
        val date = itemView.findViewById<TextView>(R.id.state)
        val state_image = itemView.findViewById<ImageView>(R.id.imageView2)
        val closingdate = itemView.findViewById<TextView>(R.id.close_date)
        val openingdate = itemView.findViewById<TextView>(R.id.open_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(filteredList[position].user.avatar_url)
            .into(holder.image)

        val issue = filteredList[position]

        holder.apply {
            title.text = issue.title
            name.text = "by ${issue.user.login}"
            date.text = issue.state
            if(issue.state=="closed") {
                closingdate.text = "Closing date: ${issue.closed_at}"
            }else{
                closingdate.visibility = View.GONE
            }
            openingdate.text = "Opening date: ${issue.created_at}"

            if(issue.state=="closed"){
                state_image.setImageResource(R.drawable.closed)
            }

        }

    }

    override fun getItemCount(): Int {
        return filteredList.size
    }
}