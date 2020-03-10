package pl.pandaonice.panda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProjectActivity extends AppCompatActivity implements PandaListFragment.OnImageClickListener {

    public static final String HEAD_INDEX = "headIndex";
    public static final String BODY_INDEX = "bodyIndex";
    public static final String LEG_INDEX = "legIndex";
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

    }

    public void onImageSelected(int position, int bodyPartNumber) {

        switch (bodyPartNumber) {
            case 0:
                Toast.makeText(this, "Head position: " + position, Toast.LENGTH_SHORT).show();
                headIndex = position;
                break;
            case 1:
                Toast.makeText(this, "Body position: " + position, Toast.LENGTH_SHORT).show();
                bodyIndex = position;
                break;
            case 2:
                Toast.makeText(this, "Legs position: " + position, Toast.LENGTH_SHORT).show();
                legIndex = position;
                break;
            default:
                break;
        }

        Bundle b = new Bundle();
        b.putInt(HEAD_INDEX, headIndex);
        b.putInt(BODY_INDEX, bodyIndex);
        b.putInt(LEG_INDEX, legIndex);

        final Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtras(b);

        Button showButton = findViewById(R.id.show);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

}
