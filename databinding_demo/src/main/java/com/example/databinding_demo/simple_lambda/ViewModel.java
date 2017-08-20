package com.example.databinding_demo.simple_lambda;

import android.content.Context;
import android.widget.Toast;

/**
 * 2017/8/18 12
 */
public class ViewModel {

    public void save(Context context, Student student) {
        Toast.makeText(context, student.toString(), Toast.LENGTH_SHORT).show();
    }

    public void canvas(Context context, Student student) {
        Toast.makeText(context, "" + student.toString(), Toast.LENGTH_SHORT).show();
    }
}
