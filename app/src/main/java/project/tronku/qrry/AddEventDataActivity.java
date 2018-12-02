package project.tronku.qrry;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEventDataActivity extends AppCompatActivity {

    private EditText eventNameEdit, eventDataEdit;
    private Button addButton;
    private String eventName, eventData;
    private View view;
    private String[] emailIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_data);
        view = findViewById(android.R.id.content);

        eventDataEdit = findViewById(R.id.eventData);
        eventNameEdit = findViewById(R.id.eventName);
        addButton = findViewById(R.id.add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventData = eventDataEdit.getText().toString();
                eventName = eventNameEdit.getText().toString();

                if(eventName.length()==0 && eventData.length()==0){
                    Snackbar snackbar = Snackbar.make(view, "Enter details!", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.show();
                }
                else if(eventName.length()==0){
                    Snackbar snackbar = Snackbar.make(view, "Enter event name!", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.show();
                }
                else if(eventData.length()==0){
                    Snackbar snackbar = Snackbar.make(view, "Enter event data!", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.show();
                }
                else{
                    getEmailStrings(eventData);
                }
            }
        });
    }

    private void getEmailStrings(String eventData) {
        emailIds = eventData.split("\\r?\\n");
        Toast.makeText(AddEventDataActivity.this, emailIds[0], Toast.LENGTH_SHORT).show();
    }
}
