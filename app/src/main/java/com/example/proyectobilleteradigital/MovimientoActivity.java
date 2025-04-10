package com.example.proyectobilleteradigital;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.Date;

import Models.Movimiento;

public class MovimientoActivity extends AppCompatActivity {

    private MaterialButton btn_ingreso, btn_gasto;
    private TextView txt_signo, txt_disponible, txt_categoria;
    private EditText txt_monto, txt_descripcion;
    private Button btn_categoria, btn_cancelar, btn_confirmar;
    private boolean esIngreso = true;
    private double disponible = 53.30; // Simulación
    private final ArrayList<Movimiento> listaMovimientos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movimiento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_ingreso = findViewById(R.id.btn_ingreso);
        btn_gasto = findViewById(R.id.btn_gasto);
        txt_signo = findViewById(R.id.txt_signo);
        txt_disponible = findViewById(R.id.txt_disponible);
        txt_monto = findViewById(R.id.txt_monto);
        txt_descripcion = findViewById(R.id.txt_descripcion);
        btn_categoria = findViewById(R.id.btn_categoria);
        txt_categoria = findViewById(R.id.txt_categoria);
        btn_cancelar = findViewById(R.id.btn_cancelar);
        btn_confirmar = findViewById(R.id.btn_confirmar);

        mostrarDisponible();

        MaterialButtonToggleGroup toggleGroup = findViewById(R.id.toggleGroupTipo);
        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) cambiarTipoMovimiento(checkedId == R.id.btn_ingreso);
        });

        btn_categoria.setOnClickListener(v -> txt_categoria.setText("Alimentos")); // Simulación

        btn_cancelar.setOnClickListener(v -> limpiarCampos("Operación cancelada"));

        btn_confirmar.setOnClickListener(v -> registrarMovimiento());
    }

    private void cambiarTipoMovimiento(boolean ingreso) {
        esIngreso = ingreso;
        txt_signo.setText(ingreso ? "+" : "-");

        // Cambio de color de botones
        btn_ingreso.setBackgroundTintList(ContextCompat.getColorStateList(this,
                ingreso ? R.color.primarybtnColor : R.color.unselectbtnColor));
        btn_gasto.setBackgroundTintList(ContextCompat.getColorStateList(this,
                ingreso ? R.color.unselectbtnColor : R.color.primarybtnColor));
    }

    private void mostrarDisponible() {
        txt_disponible.setText(String.format("Disponible: S/ %.2f", disponible));
    }

    private void registrarMovimiento() {
        String montoTexto = txt_monto.getText().toString().trim();
        String descripcion = txt_descripcion.getText().toString().trim();
        String categoria = txt_categoria.getText().toString();

        if (montoTexto.isEmpty() || categoria.contains("seleccionada")) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double monto;
        try {
            monto = Double.parseDouble(montoTexto);
            if (monto <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (esIngreso) {
            disponible += monto;
            listaMovimientos.add(new Movimiento(monto, descripcion,  new Date("22/05/2000"), esIngreso, 0));
        } else {
            disponible -= monto;
            listaMovimientos.add(new Movimiento(monto, descripcion,  new Date("22/05/2000"), esIngreso, 0)); // Agregar a lista de gastos
        }

        mostrarDisponible();
        limpiarCampos("Movimiento registrado");

        // Enviar datos actualizados a la actividad anterior
        Intent intent = new Intent();
        intent.putExtra("nuevoSaldo", (float) disponible);
        intent.putExtra("nuevoPresupuesto", 500.0f); // Este valor puede ser dinámico si lo deseas
        setResult(RESULT_OK, intent);
        finish();
    }

    private void limpiarCampos(String mensaje) {
        txt_monto.setText("");
        txt_descripcion.setText("");
        txt_categoria.setText("[Categoría seleccionada]");
        txt_signo.setText(esIngreso ? "+" : "-");
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}