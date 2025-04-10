package com.example.proyectobilleteradigital;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import DAO.UsuarioDAO;
import Models.Usuario;

public class LoginActivity extends AppCompatActivity {
    private EditText txt_usuario;
    private EditText txt_contraseña;
    private Button btn_ingresar;
    private ImageView btn_ojo;
    private boolean mostrarContraseña = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txt_usuario = findViewById(R.id.txt_usuario);
        txt_contraseña = findViewById(R.id.txt_contraseña);
        btn_ingresar = findViewById(R.id.btn_ingresar);
        btn_ojo = findViewById(R.id.btn_ojo);

        btn_ingresar.setOnClickListener(v -> validarUsuario());
        btn_ojo.setOnClickListener(v -> mostrar());
    }

    private void mostrar() {
        if (!mostrarContraseña) {
            txt_contraseña.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            btn_ojo.setImageResource(R.drawable.ic_ojo_hide);
        }
        else {
            txt_contraseña.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            btn_ojo.setImageResource(R.drawable.ic_ojo_show);
        }
        mostrarContraseña = !mostrarContraseña;
        txt_contraseña.setSelection(txt_contraseña.getText().length());
    }

    private void validarUsuario() {
        if (control()) {
            validacion();
        }
    }

    private void validacion() {
        /*String nickname = String.valueOf(txt_usuario.getText());
        String password = String.valueOf(txt_contraseña.getText());

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.Cargar(this);
        Usuario encontrado = usuarioDAO.Validar(nickname, password, this);

        if (encontrado != null) {
            startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
            finish();
        }
        else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }*/

        startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
        finish();
    }

    private boolean control() {
        if (txt_usuario.getText().toString().isEmpty()) {
            txt_usuario.setError("Ingrese su nombre o email");
            txt_usuario.requestFocus();
            return false;
        }
        if (txt_contraseña.getText().toString().isEmpty()) {
            txt_contraseña.setError("Ingrese la contraseña");
            return false;
        }
        return true;
    }
}