package com.example.notanamo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtGrade1, edtGrade2, edtGrade3, edtGrade4, edtNumberAbsences;
    private Button btnCalculate;
    private AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtGrade1 = findViewById(R.id.edt_first_grade);
        edtGrade2 = findViewById(R.id.edt_second_grade);
        edtGrade3 = findViewById(R.id.edt_third_grade);
        edtGrade4 = findViewById(R.id.edt_fourth_grade);
        edtNumberAbsences = findViewById(R.id.edt_number_of_absences);
        btnCalculate = findViewById(R.id.btn_calculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFields()) {
                    return;
                }

                double grade1 = Double.parseDouble(edtGrade1.getText().toString());
                double grade2 = Double.parseDouble(edtGrade2.getText().toString());
                double grade3 = Double.parseDouble(edtGrade3.getText().toString());
                double grade4 = Double.parseDouble(edtGrade4.getText().toString());
                int absence = Integer.parseInt(edtNumberAbsences.getText().toString());

                double average = (grade1 + grade2 + grade3 + grade4) / 4;

                LayoutInflater inflater = getLayoutInflater();
                View layout;

                if (average >= 5 && absence <= 20) {
                    layout = inflater.inflate(R.layout.alert_aprovedd, null);
                    TextView txtMessage = layout.findViewById(R.id.txt_result);
                    txtMessage.setText(R.string.alert_message_aprovedd);
                    showDialog(layout);
                } else {
                    layout = inflater.inflate(R.layout.alert_failed, null);
                    TextView txtMensagem = layout.findViewById(R.id.txt_result);

                    if (absence > 20) {
                        txtMensagem.setText(R.string.alert_failed_due_to_absences);
                    } else {
                        txtMensagem.setText(R.string.alert_failed_due_to_grades);
                    }

                    showDialog(layout);
                }
            }
        });
    }

    private boolean validateFields() {
        boolean valido = true;

        String s1 = edtGrade1.getText().toString().trim();
        String s2 = edtGrade2.getText().toString().trim();
        String s3 = edtGrade3.getText().toString().trim();
        String s4 = edtGrade4.getText().toString().trim();
        String numberAbsences = edtNumberAbsences.getText().toString().trim();

        if (s1.isEmpty()) {
            edtGrade1.setError(getString(R.string.error_fill_grade_1));
            valido = false;
        }

        if (s2.isEmpty()) {
            edtGrade2.setError(getString(R.string.error_fill_grade_2));
            valido = false;
        }

        if (s3.isEmpty()) {
            edtGrade3.setError(getString(R.string.error_fill_grade_3));
            valido = false;
        }

        if (s4.isEmpty()) {
            edtGrade4.setError(getString(R.string.error_fill_grade_4));
            valido = false;
        }

        if (numberAbsences.isEmpty()) {
            edtNumberAbsences.setError(getString(R.string.error_fill_absences));
            valido = false;
        }


        if (!valido) return false;

        double n1 = Double.parseDouble(s1);
        double n2 = Double.parseDouble(s2);
        double n3 = Double.parseDouble(s3);
        double n4 = Double.parseDouble(s4);
        int absences = Integer.parseInt(numberAbsences);

        if (n1 < 0 || n1 > 10) {
            edtGrade1.setError(getString(R.string.error_invalid_grade));
            valido = false;
        }

        if (n2 < 0 || n2 > 10) {
            edtGrade2.setError(getString(R.string.error_invalid_grade));
            valido = false;
        }

        if (n3 < 0 || n3 > 10) {
            edtGrade3.setError(getString(R.string.error_invalid_grade));
            valido = false;
        }

        if (n4 < 0 || n4 > 10) {
            edtGrade4.setError(getString(R.string.error_invalid_grade));
            valido = false;
        }

        if (absences < 0) {
            edtNumberAbsences.setError(getString(R.string.error_negative_absences));
            valido = false;
        }

        return valido;
    }

    private void showDialog(View layout) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        alert = builder.create();
        alert.show();
    }
}
