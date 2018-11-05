package fr.formation.fingertag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements EditTag.OnOutListener {

    EditTag etTag;
    private int tagColor;

    MediaPlayer son;
    int[] sons = {R.raw.cri, R.raw.cretin};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTag = findViewById(R.id.editTag);
        etTag.setOnOutListener(this);
    }

    public void gtag(View view) {
        etTag.setTagColor(Color.GREEN);
    }

    public void rtag(View view) {
        etTag.setTagColor(Color.RED);
    }

    public void btag(View view) {
        etTag.setTagColor(Color.BLUE);
    }

    public void maz(View view) {
        etTag.clear();
        colorClear();
    }

    public void vibrate(){
        Vibrator vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if (vib.hasVibrator()) {
            int tpsvib = 100;
            vib.vibrate(tpsvib);
        }
    }

    public void colorEror (){
        etTag.setBackgroundColor(Color.GRAY);
        etTag.setTagColor(Color.rgb(255,0,255));
    }

    public void colorClear() {
        etTag.setBackgroundColor(Color.WHITE);
        etTag.setTagColor(Color.BLACK);
    }

    @Override
    public void onOut(View view) {
        vibrate();
        colorEror();
        //son = MediaPlayer.create(this, sons[1]);
        //son.start();
    }

    public void saveJpeg(View view) {
        try {
            String filepath = this.getFilesDir().getPath()+ "signature.jpg";
            etTag.saveJpg(filepath);
            Toast.makeText(this, "Signature bien enregistré", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btLoadSign(View view) {
        etTag.setFindTag(etTag.getFindTag());
    }
}
