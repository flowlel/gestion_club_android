package com.example.gestionspeleo

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.DelicateCoroutinesApi
import java.sql.DriverManager
import java.sql.SQLException

@OptIn(DelicateCoroutinesApi::class)
fun countElements(context : Context, table: String, colonne: String, value: String): Int {
    val urlString = BuildConfig.DATABASE_URL
    val query = "SELECT COUNT(*) FROM ? WHERE ? = ?"
    try {
        DriverManager.getConnection(urlString).use { connection ->
            connection.prepareStatement(query).use { statement ->
                statement.setString(1, table)
                statement.setString(2, colonne)
                statement.setString(3, value)
                val resultSet = statement.executeQuery()
                // Récupération du résultat de la requête
                resultSet.next()
                val count = resultSet.getInt(1)
                return count
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
        showToast(context = context, message = "Erreur lors du count")
        return -1
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun addToDatabase(context : Context, table: String, colonnes: List<String>, valeurs: List<String>):Boolean {
    val urlString = BuildConfig.DATABASE_URL
    val query = "INSERT INTO ? (?) VALUES (?)"
    try {
        DriverManager.getConnection(urlString).use { connection ->
            connection.prepareStatement(query).use { statement ->
                statement.setString(1, table)
                statement.setString(2, colonnes.joinToString(separator = ","))
                statement.setString(3, valeurs.map { "'$it'" }.joinToString(separator = ","))
                val rowsAffected = statement.executeUpdate()
                if (rowsAffected > 0) {
                    // La requête INSERT a été exécutée avec succès
                    println("La requête INSERT a été exécutée avec succès. $rowsAffected lignes ont été insérées.")
                    return true
                } else {
                    // Aucune ligne n'a été insérée, il y a peut-être eu une erreur
                    println("Aucune ligne n'a été insérée. Il y a peut-être eu une erreur.")
                    return false
                }
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
        showToast(context = context, message = "Erreur lors de l'insertion")
        return false
    }
}

fun showToast(context: Context, message: String) {
    val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    toast.show()
}