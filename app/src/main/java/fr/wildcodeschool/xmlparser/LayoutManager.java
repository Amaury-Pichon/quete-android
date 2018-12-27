package fr.wildcodeschool.xmlparser;

import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LayoutManager {
  // Log TAG definition
  private static final String TAG = "LayoutManager";

  /**
   * Manage the layout attribute
   * @param pView Graphical element to update
   * @param pParser The Xml node containing the attributes
   * @return The attributes not treated by the method stored in a HashMap.
   */
  static public HashMap<String, String> setLayoutParams(View pView, XmlPullParser pParser) {
    HashMap<String, String> nodeAttr = new HashMap<>();

    int width = LinearLayout.LayoutParams.MATCH_PARENT;
    int height = LinearLayout.LayoutParams.MATCH_PARENT;
    int gravity = Gravity.NO_GRAVITY;
    float weight = 0.f;
    int marginH = 0;
    int marginV = 0;

    String key, value;
    int lCount = pParser.getAttributeCount();
    for (int lIndex = 0; lIndex < lCount; lIndex++) {
      key = pParser.getAttributeName(lIndex);
      value = pParser.getAttributeValue(lIndex);

      if (key.startsWith("android:")) {
        key = key.substring(8);
        switch (key) {
          case "layout_width":
            if ("wrap_content".equals(value)) {
              width = LinearLayout.LayoutParams.WRAP_CONTENT;
            }
            break;
          case "layout_height":
            if ("wrap_content".equals(value)) {
              height = LinearLayout.LayoutParams.WRAP_CONTENT;
            }
            break;
          case "layout_marginHorizontal":
          case "layout_marginVertical":
            Pattern pattern = Pattern.compile("([0-9]*)([a-z]*)");
            Matcher matcher = pattern.matcher(value);
            if (matcher.find())
            {
              int px = convertToPixel(matcher.group(1), matcher.group(2));
              if (key.equals("layout_marginVertical")) {
                marginV = px;
              } else {
                marginH = px;
              }
            }
            break;
          case "layout_weight":
            try {
              weight = Float.valueOf(value);
            } catch (NullPointerException e) {
              Log.e(TAG, e.getMessage());
            }
            break;
          case "layout_gravity":
            // TODO - Add gravity
            switch (value){
              case "right":
                gravity = Gravity.RIGHT;
                break;
              case "center_horizontal":
                gravity = Gravity.CENTER_HORIZONTAL;
                break;
              case "left":
                gravity = Gravity.LEFT;
                break;
              }
            break;
          default:
            nodeAttr.put(key, value);
            break;
        }
      }
    }

    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
    layoutParams.setMargins(marginH, marginV, marginH, marginV);
    layoutParams.weight = weight;
    layoutParams.gravity = gravity;
    pView.setLayoutParams(layoutParams);

    return nodeAttr;
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
