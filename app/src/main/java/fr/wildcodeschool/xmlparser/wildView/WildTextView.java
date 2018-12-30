package fr.wildcodeschool.xmlparser.wildView;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.wildcodeschool.xmlparser.LayoutManager;
import fr.wildcodeschool.xmlparser.MainActivity;

public class WildTextView extends AppCompatTextView {
    // Log TAG definition
    private static final String TAG = "WildTextView";

    public WildTextView(Context pCtx){
        super(pCtx);
    }

    /**
     *
     * @param pParser
     */
    public void parseXmlNode(XmlPullParser pParser) {
        HashMap<String, String> items;
        items = LayoutManager.setLayoutParams(this, pParser);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {

            // This is a loop on a HashMap without lambda expression
            for (HashMap.Entry<String, String> entry : items.entrySet()) {
                this.setAttribute(entry.getKey(), entry.getValue());
            }
        } else {
            // This is a loop on a HashMap with lambda expression
            items.forEach((key, value)->this.setAttribute(key, value));
        }
    }

    /**
     * Populate the view with the attribute value
     * @param key The key of xml attribute
     * @param value The value of xml attribute
     */
    private void setAttribute(String key, String value) {

        ViewBuilder textViewBuilder = new ViewBuilder(this);

        int paddingV = 0;
        int paddingH = 0;

        switch (key) {
            case "text":
                textViewBuilder.setText(value);
                break;
            case "background":
                textViewBuilder.setBackgroundColor(value);
                break;
            case "textColor":
                textViewBuilder.setTextColor(value);
                break;
            case "textSize":
                textViewBuilder.setTextSize(value);
                break;
            case "paddingHorizontal":
            case "paddingVertical":
                Pattern pattern = Pattern.compile("([0-9]*)([a-z]*)");
                Matcher matcher = pattern.matcher(value);
                if (matcher.find())
                {
                    int px = convertToPixel(matcher.group(1), matcher.group(2));
                    if (key.equals("paddingVertical")) {
                        paddingV = px;
                    } else {
                        paddingH = px;
                    }
                }
                break;
            case "id":
                /* Nothing to do */
                break;
            default:
                Log.i(TAG, "Unknown Attribute ["+key+"]");
                break;
        }

        this.setPadding(paddingH, paddingV, paddingH, paddingV);

    }

    /**
     * Convert from unit to pixel
     * @param value Entry value to convert
     * @param unit Unit of entry value
     * @return The converted numerical value to pixel unit
     */
    static private int convertToPixel(String value, String unit) {
        DisplayMetrics displayMetric = MainActivity.getAppContext().getResources().getDisplayMetrics();

        int pixel = 0;
        try {
            int num = Integer.valueOf(value);
            switch (unit) {
                case "px":
                    pixel = num;
                    break;
                case "dp":
                    pixel = Math.round(num * (displayMetric.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    break;
                case "sp":
                    pixel = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, num, displayMetric);
                    break;
                default:
                    break;
            }
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
        }

        return pixel;
    }
}
