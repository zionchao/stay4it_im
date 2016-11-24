package com.kevin.im.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kevin.im.BaseFragment;
import com.kevin.im.R;
import com.kevin.im.widget.row.BaseRowDescriptor;
import com.kevin.im.widget.row.ContainerView;
import com.kevin.im.widget.row.GroupDescriptor;
import com.kevin.im.widget.row.MomentsRowDescriptor;
import com.kevin.im.widget.row.NormalRowDescriptor;
import com.kevin.im.widget.row.OnRowChangeListener;
import com.kevin.im.widget.row.RowActionEnum;

import java.util.ArrayList;

/**
 * Created by zhangchao_a on 2016/11/23.
 */

public class MomentsFragment extends BaseFragment implements OnRowChangeListener {

    private ContainerView mMomentsContainerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_moments,null);
        mMomentsContainerView=(ContainerView)view.findViewById(R.id.mMomentsContainerView);
        initView();
        return view;
    }

    private void initView() {
        ArrayList<GroupDescriptor> groupDescriptors=new ArrayList<>();

        ArrayList<BaseRowDescriptor> descriptors0=new ArrayList<>();
        descriptors0.add(new MomentsRowDescriptor(R.drawable.more_my_album,"Moments","url", RowActionEnum.MOMENTS));
        GroupDescriptor descriptor0=new GroupDescriptor(descriptors0);

        groupDescriptors.add(descriptor0);

        ArrayList<BaseRowDescriptor> descriptors1=new ArrayList<>();
        descriptors1.add(new NormalRowDescriptor(R.drawable.more_my_album,"Scan QR Code",RowActionEnum.SCAN_QR));
        descriptors1.add(new NormalRowDescriptor(R.drawable.more_my_favorite,"Shake",RowActionEnum.SHAKE));
        GroupDescriptor descriptor1=new GroupDescriptor(descriptors1);

        groupDescriptors.add(descriptor1);

        ArrayList<BaseRowDescriptor> descriptors2 = new ArrayList<BaseRowDescriptor>();
        descriptors2.add(new NormalRowDescriptor(R.drawable.more_emoji_store, "People Nearby", RowActionEnum.NEARBY));
        descriptors2.add(new NormalRowDescriptor(R.drawable.more_my_bank_card, "Drift Bottle", RowActionEnum.DRIFT_BOTTLE));
        GroupDescriptor descriptor2 = new GroupDescriptor(descriptors2);

        groupDescriptors.add(descriptor2);

        ArrayList<BaseRowDescriptor> descriptors3 = new ArrayList<BaseRowDescriptor>();
        descriptors3.add(new NormalRowDescriptor(R.drawable.more_setting, "Shopping", RowActionEnum.SHOPPING));
        descriptors3.add(new NormalRowDescriptor(R.drawable.more_setting, "Games", RowActionEnum.GAMES));
        GroupDescriptor descriptor3 = new GroupDescriptor(descriptors3);

        groupDescriptors.add(descriptor3);

        mMomentsContainerView.initData(groupDescriptors, this);
        mMomentsContainerView.notifyDataChanged();

    }

    @Override
    public void onRowChanged(RowActionEnum action) {
        Toast.makeText(getActivity(),"row click on"+action.name(),Toast.LENGTH_LONG).show();
    }
}
