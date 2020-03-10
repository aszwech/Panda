package pl.pandaonice.panda;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import pl.pandaonice.panda.data.PandaImages;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            BodyPartFragment headFragment = new BodyPartFragment();
            headFragment.setImageIds(PandaImages.getHeads());
            int headIndex = getIntent().getIntExtra("headIndex", 0);
            headFragment.setListIndex(headIndex);
            fragmentManager.beginTransaction()
                    .add(R.id.head, headFragment)
                    .commit();

            BodyPartFragment bodyFragment = new BodyPartFragment();
            bodyFragment.setImageIds(PandaImages.getBodies());
            int bodyIndex = getIntent().getIntExtra("bodyIndex", 0);
            bodyFragment.setListIndex(bodyIndex);
            fragmentManager.beginTransaction()
                    .add(R.id.body, bodyFragment)
                    .commit();

            BodyPartFragment legFragment = new BodyPartFragment();
            legFragment.setImageIds(PandaImages.getLegs());
            int legIndex = getIntent().getIntExtra("legIndex", 0);
            legFragment.setListIndex(legIndex);
            fragmentManager.beginTransaction()
                    .add(R.id.legs, legFragment)
                    .commit();

        }

        Button sharingButton = findViewById(R.id.share);
        sharingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenShotAndShare();
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Button shaB = findViewById(R.id.share);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            shaB.setEnabled(false);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            shaB.setEnabled(true);
        }
    }

    private void takeScreenShotAndShare() {

        String sharePath = "no";
        Date now = new Date();
        try {
            View v1 = findViewById(R.id.result);
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            String mPath = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + now.getTime() + ".jpg";
            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            sharePath = imageFile.getPath();

        } catch (Throwable e) {
            e.printStackTrace();
        }
        share(sharePath);
    }

    private void share(String sharePath) {
        File imageFile = new File(sharePath);
        Uri screenshotUri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", imageFile);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sharingIntent.setType(getString(R.string.type_jpg));
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.content_body));
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)));
    }
}
