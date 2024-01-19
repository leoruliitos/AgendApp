package cl.leonelab.agendapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.leonelab.agendapp.R
import cl.leonelab.agendapp.models.ContactModel

class ContactAdapter(private val contactList: ArrayList<ContactModel>) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentContact = contactList[position]
        holder.tvContactName.text = currentContact.contactName
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
    class ViewHolder(itemView : View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvContactName : TextView = itemView.findViewById(R.id.tvContactName)

        init{
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }

        }
    }

}