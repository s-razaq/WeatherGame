package com.sample;

import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class NumberClass extends AbstractWheelTextAdapter {
    	
        private int values[] = new int [100];
        
        /**
         * Constructor
         */
        protected NumberClass(Context context) {
            super(context, R.layout.wheel_picker_layout, NO_RESOURCE);
          //fill array
            for (int i = 0; i <= 99; i++){
    	        	values[i] = (-50) + i;
    	        }
            setItemTextResource(R.id.country_name);
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


