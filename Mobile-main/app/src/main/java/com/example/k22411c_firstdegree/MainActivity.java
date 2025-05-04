package com.example.k22411c_firstdegree;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Khai báo các biến để quản lý các ô nhớ của các view:
    EditText edtCoefficientA;
    EditText edtCoefficientB;
    TextView txtResult;
    Button btnLan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Bật chế độ edge-to-edge
        setContentView(R.layout.activity_main); // Nạp giao diện từ file XML
        setupViews(); 
        setupWindowInsets(); 
    }
    
    // Khởi tạo các thành phần giao diện
    private void setupViews() {
        // Lấy các thành phần từ layout bằng ID
        edtCoefficientA = findViewById(R.id.edtCoefficientA);
        edtCoefficientB = findViewById(R.id.edtCoefficientB);
        txtResult = findViewById(R.id.txtResult);
        btnLan = findViewById(R.id.btnLan);
        
        // Thiết lập sự kiện khi nhấn nút chuyển đổi ngôn ngữ
        btnLan.setOnClickListener(v -> showLanguageDialog());
    }

    // Xử lý padding cho các cạnh màn hình
    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Hiển thị dialog chọn ngôn ngữ
    private void showLanguageDialog() {
        // Tên ngôn ngữ và mã ngôn ngữ tương ứng
        String[] languages = {"English", "Tiếng Việt", "Français", "Español"};
        String[] languageCodes = {"en", "vi", "fr", "es"};
        
        // Tạo và hiển thị dialog chọn ngôn ngữ
        new AlertDialog.Builder(this)
            .setTitle(R.string.title_select_language)
            .setItems(languages, (dialog, which) -> 
                showConfirmationDialog(languages[which], languageCodes[which]))
            .show();
    }

    // Hiển thị dialog xác nhận thay đổi ngôn ngữ
    private void showConfirmationDialog(String languageName, String languageCode) {
        new AlertDialog.Builder(this)
            .setTitle(R.string.title_confirm_language_change)
            .setMessage(getString(R.string.title_confirm_language_change_message, languageName))
            .setPositiveButton(android.R.string.yes, (dialog, which) -> changeLanguage(languageCode))
            .setNegativeButton(android.R.string.no, null)
            .show();
    }

    // Thay đổi ngôn ngữ của ứng dụng
    private void changeLanguage(String languageCode) {
        // Tạo locale mới từ mã ngôn ngữ
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        
        // Cập nhật cấu hình ngôn ngữ
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        
        // Khởi động lại activity để áp dụng ngôn ngữ mới
        recreate();
    }

    public void do_solution(View view) {
        // Lấy giá trị từ các ô nhập
        double a = Double.parseDouble(edtCoefficientA.getText().toString());
        double b = Double.parseDouble(edtCoefficientB.getText().toString());

        if (a==0 && b==0)
        {
            //txtResult.setText("Infinity!");
            txtResult.setText(getResources().getText(R.string.title_infinity));
        }
        else if(a==0 && b!=0)
        {
            //txtResult.setText("No Solution!");
            txtResult.setText(getResources().getText(R.string.title_no_solution));
        }
        else
        {
            double x=-b/a;
            txtResult.setText("x="+x);
        }
    }

    public void do_next(View view) {
        edtCoefficientA.setText("");
        edtCoefficientB.setText("");
        txtResult.setText("");
        //di chuyển con trỏ nhập liệu và HSA để nhập cho lệ
        edtCoefficientA.requestFocus();
    }

    public void do_exit(View view) {
        finish();
    }
}