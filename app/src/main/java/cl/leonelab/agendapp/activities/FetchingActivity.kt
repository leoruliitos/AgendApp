package cl.leonelab.agendapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.leonelab.agendapp.R
import cl.leonelab.agendapp.adapters.ContactAdapter
import cl.leonelab.agendapp.models.ContactModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingActivity : AppCompatActivity() {

    private lateinit var contactRecycleView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var contactList: ArrayList<ContactModel>
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        contactRecycleView = findViewById(R.id.rvContact)
        contactRecycleView.layoutManager = LinearLayoutManager(this)
        contactRecycleView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        contactList = arrayListOf<ContactModel>()

        getContactData()
    }

    private fun getContactData(){
        contactRecycleView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Contacs")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                contactList.clear()
                if (snapshot.exists()){
                    for (contactSnap in snapshot.children){
                        val contactData = contactSnap.getValue(ContactModel::class.java)
                        contactList.add(contactData!!)
                    }
                    val cAdapter = ContactAdapter(contactList)
                    contactRecycleView.adapter = cAdapter

                    cAdapter.setOnItemClickListener(object : ContactAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, ContactDetailsActivity::class.java)
                            //put extras
                            intent.putExtra("contactId", contactList[position].contactId)
                            intent.putExtra("contactName", contactList[position].contactName)
                            intent.putExtra("contactTel", contactList[position].contactTel)
                            intent.putExtra("contactEmail", contactList[position].contactEmail)
                            startActivity(intent)
                        }
                    })

                    contactRecycleView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}