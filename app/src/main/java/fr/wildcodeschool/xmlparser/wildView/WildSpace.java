package fr.wildcodeschool.xmlparser.wildView;

import android.content.Context;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;

import fr.wildcodeschool.xmlparser.LayoutManager;

public class WildSpace extends View {

    public WildSpace (Context pCtx){
        super(pCtx);
    }

    /**
     *
     * @param pParser
     */
    public void parseXmlNode(XmlPullParser pParser) {
        HashMap<String, String> items;
        items = LayoutManager.setLayoutParams(this, pParser);
    }
}



