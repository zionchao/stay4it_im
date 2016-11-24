package com.kevin.im.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevin.im.BaseFragment;

/**
 * Created by zhangchao_a on 2016/11/23.
 */

public class ContactFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView=new TextView(getActivity().getApplicationContext());
        textView.setText("Contact");
        return textView;
    }
}
