package com.example.restaurant

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurant.modelo.CuentaMesa
import com.example.restaurant.modelo.ItemMenu
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // Declaración de variables
    private lateinit var etCantidadPastel: EditText
    private lateinit var etCantidadCazuela: EditText
    private lateinit var switchPropina: Switch
    private lateinit var tvTotalComida: TextView
    private lateinit var tvValorPropina: TextView
    private lateinit var tvTotalFinal: TextView

    // Instancia de nuestra clase principal de lógica
    private lateinit var cuentaMesa: CuentaMesa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Inicializar la lógica (Modelo)
        cuentaMesa = CuentaMesa(1)
        // Agregamos los items con cantidad 0 al inicio
        val pastel = ItemMenu("Pastel de Choclo", "12000")
        val cazuela = ItemMenu("Cazuela", "10000")

        cuentaMesa.agregarItem(pastel, 0) // Índice 0
        cuentaMesa.agregarItem(cazuela, 0) // Índice 1

        // 2. Vincular las vistas del XML
        etCantidadPastel = findViewById(R.id.etCantidadPastel)
        etCantidadCazuela = findViewById(R.id.etCantidadCazuela)
        switchPropina = findViewById(R.id.switchPropina)
        tvTotalComida = findViewById(R.id.tvTotalComida)
        tvValorPropina = findViewById(R.id.tvValorPropina)
        tvTotalFinal = findViewById(R.id.tvTotalFinal)

        // 3. Configurar Evento del Switch (Propina)
        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            actualizarTotales()
        }

        // 4. Configurar Evento de Texto (EditTexts)
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Convertimos el texto a número. Si está vacío, usamos 0.
                val cantPastel = etCantidadPastel.text.toString().toIntOrNull() ?: 0
                val cantCazuela = etCantidadCazuela.text.toString().toIntOrNull() ?: 0

                // Actualizamos el modelo
                cuentaMesa.actualizarCantidad(0, cantPastel)
                cuentaMesa.actualizarCantidad(1, cantCazuela)

                // Refrescamos la pantalla
                actualizarTotales()
            }
        }

        etCantidadPastel.addTextChangedListener(textWatcher)
        etCantidadCazuela.addTextChangedListener(textWatcher)

        // Primera actualización inicial
        actualizarTotales()
    }

    // Función para mostrar los montos formateados como Pesos Chilenos
    private fun actualizarTotales() {
        val formatoCL = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

        tvTotalComida.text = "Comida: ${formatoCL.format(cuentaMesa.calcularTotalSinPropina())}"
        tvValorPropina.text = "Propina: ${formatoCL.format(cuentaMesa.calcularPropina())}"
        tvTotalFinal.text = "TOTAL: ${formatoCL.format(cuentaMesa.calcularTotalConPropina())}"
    }
}