package fr.wildcodeschool.xmlparser;

import android.content.Context;
import android.util.Xml;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import fr.wildcodeschool.xmlparser.wildView.WildButton;
import fr.wildcodeschool.xmlparser.wildView.WildCheckBox;
import fr.wildcodeschool.xmlparser.wildView.WildEditText;
import fr.wildcodeschool.xmlparser.wildView.WildLinearLayout;
import fr.wildcodeschool.xmlparser.wildView.WildSpace;
import fr.wildcodeschool.xmlparser.wildView.WildTextView;

public class Inflater {
  // Activity context
  private Context ctx;

  // Constructor should only contains initialisation
  Inflater(Context ctx) {
    this.ctx = ctx;
  }

  /**
   * This method parse the xml layout to populate the activity screen
   * @param pParent Parent layout
   * @throws IOException
   * @throws XmlPullParserException
   */
  public void inflate(ViewGroup pParent) throws IOException, XmlPullParserException {
    // Store the parent
    ViewGroup lParentView = pParent;

    // INFO WCS - Here is how to keep a file from the assets directory
    InputStream lXmlStream = this.ctx.getAssets().open("content_assets.xml");

    // XML parser initialization
    XmlPullParser parser = Xml.newPullParser();
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
    parser.setInput(lXmlStream, null);

    // Loop on XML nodes
    int eventType = parser.getEventType();
    while (eventType != XmlPullParser.END_DOCUMENT) {
      if(eventType == XmlPullParser.START_TAG) {
        switch (parser.getName()) {
          case "LinearLayout":
            WildLinearLayout lLayout = new WildLinearLayout(ctx);
            lLayout.parseXmlNode(parser);
            lParentView.addView(lLayout);
            lParentView = lLayout;
            break;
          case "EditText":
            WildEditText lEditText = new WildEditText(ctx);
            lEditText.parseXmlNode(parser);
            lParentView.addView(lEditText);
            break;
          case "Button":
            WildButton lButton = new WildButton(ctx);
            lButton.parseXmlNode(parser);
            lParentView.addView(lButton);
            break;
          case "TextView":
            // TODO - Add WildTextView
            WildTextView lTextView = new WildTextView(ctx);
            lTextView.parseXmlNode(parser);
            lParentView.addView(lTextView);
            break;
          case "CheckBox":
            // TODO - Add WildCheckBox
            WildCheckBox lCheckBox = new WildCheckBox(ctx);
            lCheckBox.parseXmlNode(parser);
            lParentView.addView(lCheckBox);
            break;
          case "Space":
            // TODO - Add WildSpace
            WildSpace lSpace = new WildSpace(ctx);
            lSpace.parseXmlNode(parser);
            lParentView.addView(lSpace);
            break;
          default:
            break;
        }
      }
      else if (eventType == XmlPullParser.END_TAG) {
        switch (parser.getName()) {
          case "LinearLayout":
            lParentView = (ViewGroup)lParentView.getParent();
            break;
          default:
            break;
        }
      }
      parser.next();
      eventType = parser.getEventType();
    }
  }
}
