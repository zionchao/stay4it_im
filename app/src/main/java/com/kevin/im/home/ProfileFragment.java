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
import com.kevin.im.widget.row.NormalRowDescriptor;
import com.kevin.im.widget.row.OnRowChangeListener;
import com.kevin.im.widget.row.ProfileRowDescriptor;
import com.kevin.im.widget.row.RowActionEnum;

import java.util.ArrayList;

/**
 * Created by zhangchao_a on 2016/11/23.
 */

public class ProfileFragment extends BaseFragment implements OnRowChangeListener {

    private ContainerView mProfileContainerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,null);
        mProfileContainerView=(ContainerView) view.findViewById(R.id.mProfileContainerView);
        initView();
        return view;
    }

    private void initView() {
        ArrayList<GroupDescriptor> groupDescriptors = new ArrayList<GroupDescriptor>();

        ArrayList<BaseRowDescriptor> descriptors0 = new ArrayList<BaseRowDescriptor>();
        descriptors0.add(new ProfileRowDescriptor("", "Kevin", "WeChat ID: kevin_zhchao", RowActionEnum.WECHAT_ID));
        GroupDescriptor descriptor0 = new GroupDescriptor(descriptors0);

        groupDescriptors.add(descriptor0);

        ArrayList<BaseRowDescriptor> descriptors1 = new ArrayList<BaseRowDescriptor>();
        descriptors1.add(new NormalRowDescriptor(R.drawable.more_my_album, "My Posts", RowActionEnum.MY_POSTS));
        descriptors1.add(new NormalRowDescriptor(R.drawable.more_my_favorite, "Favorite Messages", RowActionEnum.FAVORITE_MSG));
        descriptors1.add(new NormalRowDescriptor(R.drawable.more_my_bank_card, "My Bank Cards", RowActionEnum.MY_BANK_CARD));
        GroupDescriptor descriptor1 = new GroupDescriptor(descriptors1);

        groupDescriptors.add(descriptor1);

        ArrayList<BaseRowDescriptor> descriptors2 = new ArrayList<BaseRowDescriptor>();
        descriptors2.add(new NormalRowDescriptor(R.drawable.more_emoji_store, "Sticker Gallery", RowActionEnum.STICKER_GALLERY));
        GroupDescriptor descriptor2 = new GroupDescriptor(descriptors2);

        groupDescriptors.add(descriptor2);

        ArrayList<BaseRowDescriptor> descriptors3 = new ArrayList<BaseRowDescriptor>();
        descriptors3.add(new NormalRowDescriptor(R.drawable.more_setting, "Settings", RowActionEnum.SETTINGS));
        GroupDescriptor descriptor3 = new GroupDescriptor(descriptors3);

        groupDescriptors.add(descriptor3);

        mProfileContainerView.initData(groupDescriptors, this);
        mProfileContainerView.notifyDataChanged();
    }

    @Override
    public void onRowChanged(RowActionEnum action) {
        Toast.makeText(getActivity(), "row click on:" + action.name(), Toast.LENGTH_SHORT).show();
    }
}
