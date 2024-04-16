package com.example.gestionspeleo
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var mailEditText: EditText
    private lateinit var nomEditText: EditText
    private lateinit var licenceEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialisation des vues
        mailEditText = findViewById(R.id.mailEditText)
        nomEditText = findViewById(R.id.nomEditText)
        licenceEditText = findViewById(R.id.licenceEditText)
        loginButton = findViewById(R.id.loginButton)

        // Ajout d'un écouteur de clic sur le bouton de connexion
        loginButton.setOnClickListener {
            val mail = mailEditText.text.toString()
            val nom = nomEditText.text.toString()
            val licence = licenceEditText.text.toString()

            // Vérification des champs non vides
            if (mail.isNotEmpty() && nom.isNotEmpty()) {
                val countnom=countElements(this,"utilisateurs", "nom", nom)
                val countmail=countElements(this, "utilisateurs","mail", mail)
                val countlicence=countElements(this, "utilisateurs","licence", licence)
                if (countnom>0){
                    showToast(this, "Le nom existe deja")
                }
                if (countlicence>0){
                    showToast(this, "La licence existe deja")
                }
                if (countmail>0){
                    showToast(this, "Le mail existe deja")
                }
                if (countnom==0 && countmail==0 && countlicence==0){
                    // Enregistrement des identifiants localement
                    val sharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("mail", mail)
                    editor.putString("nom", nom)
                    editor.putString("licence", licence)
                    editor.apply()
                    if (addToDatabase(this, "utilisateurs", listOf("nom", "licence", "mail"), listOf(nom, licence, mail))) {
                        finish()
                        showToast(this, "ok")
                } else {
                    showToast(this, "erreur d'enregistrement")
                }

            } else {
                // Affichage d'un message d'erreur si des champs sont vides
                showToast(this, "Un petit effort ^^")
            }
        }
    }
}}