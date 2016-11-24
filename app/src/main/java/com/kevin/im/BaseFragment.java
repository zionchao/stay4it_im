package com.kevin.im;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by zhangchao_a on 2016/11/23.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
    }
}
