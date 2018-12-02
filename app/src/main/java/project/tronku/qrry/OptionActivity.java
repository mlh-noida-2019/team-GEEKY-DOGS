package project.tronku.qrry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class OptionActivity extends AppCompatActivity {

    private ImageView addEvent, scanCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        addEvent = findViewById(R.id.addEvent);
        scanCode = findViewById(R.id.scanCode);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionActivity.this, AddEventDataActivity.class));
            }
        });

        scanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionActivity.this, ScanCodeActivity.class));
            }
        });
    }
}
