package dhcc.cn.com.layoutinflater;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.view.View;

/**
 * 2017/8/20 11
 */
public class SkinLayoutInflaterFactory implements LayoutInflaterFactory {
    private BaseActivity mBaseActivity;

    public SkinLayoutInflaterFactory(BaseActivity baseActivity) {
        mBaseActivity = baseActivity;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = mBaseActivity.onCreateView(parent, name, context, attrs);
        parseViewAttrs(view,name,context,attrs);
        return view;
    }

    private void parseViewAttrs(View view, String name, Context context, AttributeSet attrs) {
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attributeName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);

        }
    }
}
