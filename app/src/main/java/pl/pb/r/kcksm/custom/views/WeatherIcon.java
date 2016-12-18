package pl.pb.r.kcksm.custom.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import pl.pb.r.kcksm.managers.FontCache;

/**
 * Created by Radosław Naruszewicz on 2016-12-05.
 */

public class WeatherIcon extends TextView{
        public WeatherIcon(Context context) {
            super(context);

            applyCustomFont(context);
        }

        public WeatherIcon(Context context, AttributeSet attrs) {
            super(context, attrs);

            applyCustomFont(context);
        }

        public WeatherIcon(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);

            applyCustomFont(context);
        }

        private void applyCustomFont(Context context) {

            Typeface customFont = Typeface.createFromAsset(context.getAssets(), "fonts/weathericons.ttf");//FontCache.getTypeface("SourceSansPro-Regular.ttf", context);
            setTypeface(customFont);
        }
}
