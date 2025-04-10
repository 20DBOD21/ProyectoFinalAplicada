package com.example.proyectobilleteradigital;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Adapters.MovimientoAdapter;
import DAO.CategoriaDAO;
import DAO.DbOpenHelper;
import DAO.MovimientoDAO;
import Models.Categoria;
import Models.ChartHelper;
import Models.Movimiento;

public class DashFragment extends Fragment {
    private DbOpenHelper dboh;
    private CategoriaDAO categoriaDAO;
    private MovimientoDAO movimientoDAO;
    private MovimientoAdapter movimientoAdapter;
    private ExecutorService executorService;
    private PieChart pie_dash;
    private TextView tv_balance, tv_presupuesto, tv_ingresos;
    private MaterialButton btn_dash_ingreso, btn_dash_gasto;
    private Button btn_dash_pdf;
    private RecyclerView rvDashMov;
    private boolean mostrarGastos = true;
    private List<Categoria> categoriaList;
    private List<Movimiento> movimientoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash, container, false);
        initialViews(view);

        /*dboh = new DbOpenHelper(requireContext(), 0);
        categoriaDAO = new CategoriaDAO();
        movimientoDAO = new MovimientoDAO();
        executorService = Executors.newSingleThreadExecutor(); */

        /*setupRecyclerView();
        setupPieChart();
        setupButtons();
        loadData();*/

        return view;
    }

    private void loadData() {
        categoriaDAO.Cargar(requireActivity());
        try {
            movimientoDAO.Cargar(requireActivity());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        movimientoAdapter.setCategoria(categoriaDAO.getCategoriaList());
        movimientoAdapter.setMovimientoList(movimientoDAO.getMovimientoList());
    }

    private void initialViews(View view) {
        tv_balance = view.findViewById(R.id.tv_balance);
        tv_presupuesto = view.findViewById(R.id.tv_presupuesto);
        tv_ingresos = view.findViewById(R.id.tv_ingresos);

        btn_dash_ingreso = view.findViewById(R.id.btn_dash_ingreso);
        btn_dash_gasto = view.findViewById(R.id.btn_dash_gasto);
        btn_dash_pdf = view.findViewById(R.id.btn_dash_pdf);

        pie_dash = view.findViewById(R.id.pie_dash);
    }

    private void setupRecyclerView() {
        movimientoAdapter = new MovimientoAdapter(requireContext());
        rvDashMov.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvDashMov.setAdapter(movimientoAdapter);
    }

    private void setupPieChart() {
        ChartHelper.setupPieChart(pie_dash);
    }

    private void setupButtons() {
        btn_dash_ingreso.setOnClickListener(v -> {
            mostrarGastos = false;
            updateChartData();
            highlightActiveButton();
        });

        btn_dash_gasto.setOnClickListener(v -> {
            mostrarGastos = true;
            updateChartData();
            highlightActiveButton();
        });

        btn_dash_pdf.setOnClickListener(v -> {
            return;
        });

        highlightActiveButton();
    }

    private void updateChartData() {
        if (movimientoList == null) {
            return;
        }

        double totalIngresos = 0;
        double totalGastos = 0;

        for (Movimiento movimiento : movimientoList) {
            if (movimiento.isEsGasto()) {
                totalGastos += movimiento.getMonto();
            }
            else {
                totalIngresos += movimiento.getMonto();
            }
        }
        double balance = totalIngresos - totalGastos;
        tv_balance.setText(String.format(Locale.getDefault(), "S/ %.2f", balance));
        tv_ingresos.setText(String.format(Locale.getDefault(), "S/ %.2f", totalIngresos));
        tv_presupuesto.setText("S/ 200.00");
    }

    private void highlightActiveButton() {
        btn_dash_gasto.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), mostrarGastos ? R.color.primarybtnColor : R.color.unselectbtnColor));
        btn_dash_ingreso.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), !mostrarGastos ? R.color.primarybtnColor : R.color.unselectbtnColor));
    }
}