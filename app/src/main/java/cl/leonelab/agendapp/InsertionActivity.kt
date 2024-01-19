package cl.leonelab.agendapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtTel: EditText
    private lateinit var edtEmail: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        edtName = findViewById(R.id.edtName)
        edtTel = findViewById(R.id.edtTel)
        edtEmail = findViewById(R.id.edtEmail)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Contacs")

        btnSaveData.setOnClickListener{
            saveContactData()
        }
    }
    private fun saveContactData() {
        //Obteniendo los datos de la vista
        val contactName = edtName.text.toString()
        val contactTel = edtTel.text.toString()
        val contactEmail = edtEmail.text.toString()

        if (contactName.isEmpty()) {
            edtName.error = "Ingrese su nombre"
        }
        if (contactTel.isEmpty()) {
            edtTel.error = "Ingrese su Celular"
        }
        if (contactEmail.isEmpty()) {
            edtEmail.error = "Ingrese su email"
        }

        val contactId = dbRef.push().key!!
        val contact = ContactModel(contactId, contactName, contactTel, contactEmail)

        dbRef.child(contactId).setValue(contact)
            .addOnCompleteListener {
                Toast.makeText(this, "Contacto registrado correctamente...", Toast.LENGTH_LONG).show()
                edtName.text.clear()
                edtTel.text.clear()
                edtEmail.text.clear()

            }.addOnFailureListener{err->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}