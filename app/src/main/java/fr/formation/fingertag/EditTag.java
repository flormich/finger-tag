package fr.formation.fingertag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class EditTag extends AppCompatImageView implements View.OnTouchListener {

    private int tagColor;
    private int tagWeight;
    private Bitmap findTag;
    private int bkgColor;
    Paint paint;
    Path path;

    private OnOutListener onOutListener;

    public OnOutListener getOnOutListener() {
        return onOutListener;
    }

    public void setOnOutListener(OnOutListener onOutListener) {
        this.onOutListener = onOutListener;
    }

    public EditTag(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    //ce constructeur est nécessaire car utilisé par l'inflater
    //inflater = programme qui construit l'écran
    public EditTag(Context context, AttributeSet attrs){
        super(context, attrs);

        setOnTouchListener(this);

        //Définir l'objet paint
        paint = new Paint();
        //paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        //Définir l'objet path
        path = new Path();

        setTagColor(attrs.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "tagColor", tagColor));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    public void setBkgColor(int bkgColor) {
        this.bkgColor = bkgColor;
    }
    public int getBkgColor() {
        return bkgColor;
    }

    public void setTagColor(int tagColor) {
        this.tagColor = tagColor;
        paint.setColor(tagColor);
        invalidate();
    }
    public int getTagColor() {
        return tagColor;
    };

    public void setTagWeight(int tagWeight) {
        this.tagWeight = tagWeight;
        paint.setStrokeWidth(tagWeight);
        invalidate();
    }
    public int getTagWeight() {
        return tagWeight;
    }

    public void setFindTag(Bitmap findTag) {
        setImageBitmap(findTag);
        this.findTag = findTag;
    }
    public Bitmap getFindTag() {
        buildDrawingCache();
        Bitmap bitmap = getDrawingCache();
        findTag = bitmap;
        return findTag;
    }

    public void clear(){
        path.reset();
        invalidate();
    }

    public void saveJpg(String fichier)throws Exception {
        buildDrawingCache();
        Bitmap bitmap = getDrawingCache();
        FileOutputStream stream = new FileOutputStream(fichier);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 98, stream);
        stream.close();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            path.moveTo(event.getX(), event.getY());
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            path.lineTo(event.getX(), event.getY());
        }
        if (event.getX()<10 || event.getX()>getWidth() -10) {
            if(onOutListener != null) {onOutListener.onOut(this);}
        }
        if (event.getY()<10 || event.getY()>getHeight()-10) {
            if (onOutListener != null) {onOutListener.onOut(this);}
        }
        invalidate();
        return true;

    /*
    //Ou avec le onTouch ci dessous
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                paint.getColor();
                path.lineTo(event.getX(), event.getY());
                break;
        }
        invalidate();
        return true;
        //}
    */
    }

    public interface OnOutListener {
        void onOut(View view);
    }
}
