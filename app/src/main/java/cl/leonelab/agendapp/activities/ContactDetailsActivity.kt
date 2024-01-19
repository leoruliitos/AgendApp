package cl.leonelab.agendapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import cl.leonelab.agendapp.R
import cl.leonelab.agendapp.models.ContactModel
import com.google.firebase.database.FirebaseDatabase
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

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("contactId").toString(),
                intent.getStringExtra("contactName").toString(),
            )
        }
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

    private fun openUpdateDialog(
        contactId: String,
        contactName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val edtContactName = mDialogView.findViewById<EditText>(R.id.edtContactName)
        val edtContactTel = mDialogView.findViewById<EditText>(R.id.edtContactTel)
        val edtContactEmail = mDialogView.findViewById<EditText>(R.id.edtContactEmail)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        edtContactName.setText(intent.getStringExtra("contactName").toString())
        edtContactTel.setText(intent.getStringExtra("contactTel").toString())
        edtContactEmail.setText(intent.getStringExtra("contactEmail").toString())

        mDialog.setTitle("Actualizando el contacto $contactName")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateContactData(
                contactId,
                edtContactName.text.toString(),
                edtContactTel.text.toString(),
                edtContactEmail.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            //
            txtContactName.text = edtContactName.text.toString()
            txtContactTel.text = edtContactTel.text.toString()
            txtContactEmail.text = edtContactEmail.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateContactData(
        id:String,
        name: String,
        tel: String,
        email: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Contacs").child(id)
        val contactInfo = ContactModel(id, name, tel, email)
        dbRef.setValue(contactInfo)
    }
}
