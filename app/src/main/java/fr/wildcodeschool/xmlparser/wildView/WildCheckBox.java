package fr.wildcodeschool.xmlparser.wildView;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;

import fr.wildcodeschool.xmlparser.LayoutManager;

public class WildCheckBox extends AppCompatCheckBox {

    // Log TAG definition
    private static final String TAG = "WildCheckBox";

    public WildCheckBox(Context pCtx){
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

        ViewBuilder checkBoxBuilder = new ViewBuilder(this);

        switch (key) {
            case "text":
                checkBoxBuilder.setText(value);
                break;
            case "id":
                /* Nothing to do */
                break;
            default:
                Log.i(TAG, "Unknown Attribute ["+key+"]");
                break;
        }
    }
}
