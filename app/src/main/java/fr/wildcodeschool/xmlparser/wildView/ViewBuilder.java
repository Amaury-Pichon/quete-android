package fr.wildcodeschool.xmlparser.wildView;

import android.graphics.Color;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.wildcodeschool.xmlparser.MainActivity;


public class ViewBuilder {

    private static final String TAGEMS = "WildEditText";

    private TextView mView;

    public ViewBuilder (TextView pView){
        mView = pView;
    }

    public void setText(String text){
        mView.setText(text);
    }

    public void setGravity(String gravity){
        switch (gravity){
            case "right":
                mView.setGravity(Gravity.RIGHT);
                break;
            case "center_horizontal":
                mView.setGravity(Gravity.CENTER_HORIZONTAL);
                break;
            case "left":
                mView.setGravity(Gravity.LEFT);
                break;
        }
    }

    public void setBackgroundColor(String value){
        mView.setBackgroundColor(Color.parseColor(value));
    }

    public void setTextColor(String value){
        mView.setTextColor(Color.parseColor(value));
    }

    public void setTextSize(String size){
        Pattern patternSize = Pattern.compile("([0-9]*)([a-z]*)");
        Matcher matcherSize = patternSize.matcher(size);
        if (matcherSize.find()) {
            String valueSize = matcherSize.group(1);
            String unitSize = matcherSize.group(2);
            switch (unitSize) {
                case "px":
                    mView.setTextSize(TypedValue.COMPLEX_UNIT_PX, Integer.valueOf(valueSize));
                    break;
                case "dp":
                    mView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, Integer.valueOf(valueSize));
                    break;
                case "sp":
                    mView.setTextSize(TypedValue.COMPLEX_UNIT_SP, Integer.valueOf(valueSize));
                    break;
                default:
                    break;
            }
        }
    }

    public void setHint(String hint){
        mView.setHint(hint);
    }

    public void setEms(String ems){
        try {
            mView.setEms(Integer.valueOf(ems));
        } catch (NullPointerException e) {
            Log.e(TAGEMS, e.getMessage());
        }
    }


    /**
     *
     * @param pInputType
     */
    public void setInputType(String pInputType) {
        switch (pInputType) {
            case "textPersonName":
                mView.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                break;
            default:
                // Log it
                break;
        }
    }

    public TextView build(){
        return mView;
    }
}
