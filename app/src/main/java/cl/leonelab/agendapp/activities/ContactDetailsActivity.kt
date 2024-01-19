package cl.leonelab.agendapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import cl.leonelab.agendapp.R
import java.sql.RowId

class ContactDetailsActivity : AppCompatActivity() {

    private lateinit var txtContactId: TextView
    private lateinit var txtContactName: TextView
    private lateinit var txtContactTel: TextView
    private lateinit var txtContactEmail: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)

        initView()
        setValuesToViews()
    }

    private fun initView() {
        txtContactId = findViewById(R.id.txtContactId)
        txtContactName = findViewById(R.id.txtContactName)
        txtContactTel = findViewById(R.id.txtContactTel)
        txtContactEmail = findViewById(R.id.txtContactEmail)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews(){
        txtContactId.text = intent.getStringExtra("contactId")
        txtContactName .text = intent.getStringExtra("contactName")
        txtContactTel.text = intent.getStringExtra("contactTel")
        txtContactEmail.text = intent.getStringExtra("contactEmail")
    }
}
