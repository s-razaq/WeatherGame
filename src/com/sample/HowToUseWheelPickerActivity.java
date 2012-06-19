package com.sample;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class HowToUseWheelPickerActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheel_picker);

        final WheelView temValue = (WheelView) findViewById(R.id.value);
        temValue.setViewAdapter(new NumberAdapter(this));
        temValue.setCurrentItem(3);
        
	}
	
	/**
     * Adapter for numbers
     */
    public class NumberAdapter extends AbstractWheelTextAdapter {
        // Countries names
    	
        private int values[] = new int [100];
        
        /**
         * Constructor
         */
        protected NumberAdapter(Context context) {
            super(context, R.layout.wheel_picker_layout, NO_RESOURCE);
            setItemTextResource(R.id.value);
          //fill array
            for (int i = 0; i <= 99; i++){
    	        	values[i] = (-50) + i;
    	        }
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }
        
        @Override
        public int getItemsCount() {
            return values.length;
        }
        
        @Override
        protected CharSequence getItemText(int index) {
            return values[index]+"";
        }
    }

}
