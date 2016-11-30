package com.kevin.im.widget.chat.plugin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.kevin.im.R;
import com.kevin.im.widget.chat.emo.EmoView;
import com.kevin.im.widget.chat.emo.OnEmoClickListener;

/**
 * Created by zhangchao_a on 2016/11/29.
 */

public class ChatPluginView extends RelativeLayout implements View.OnClickListener,OnEmoClickListener {

    private EditText mChatEdt;
    private ImageButton mChatEmoBtn;
    private EmoView mChatEmoView;
    private Button mChatSendBtn;
    private int mEmoSize;
    private InputMethodManager mKeyboardManager;
    private OnPluginListener listener;

    public ChatPluginView(Context context) {
        super(context);
        initView();
    }

    public ChatPluginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public ChatPluginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_chat_plugin,this);
        mChatEdt=(EditText)findViewById(R.id.mChatEdt);
        mChatEmoBtn=(ImageButton)findViewById(R.id.mChatEmoBtn);
        mChatEmoBtn.setOnClickListener(this);
        mChatSendBtn=(Button)findViewById(R.id.mChatSendBtn);
        mChatSendBtn.setOnClickListener(this);
        mChatEmoView=(EmoView)findViewById(R.id.mChatEmoView);
        mChatEmoView.initData(this);
        float density=getResources().getDisplayMetrics().density;
        mEmoSize=(int)(25*density);
    }

    public void initData(InputMethodManager manager){
        this.mKeyboardManager = manager;
    }

    public interface  OnPluginListener{
        void onSendMsg(CharSequence content);
        void onSendEmo(String content);
    }

    public void setOnPluginListener(OnPluginListener listener)
    {
        this.listener=listener;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.mChatSendBtn:
                if (mChatEdt.getText().toString()!=null)
                {
                    listener.onSendMsg(mChatEdt.getText());
                }
                break;
            case R.id.mChatEmoBtn:
                if (mChatEmoView.getVisibility()==GONE){
                    mKeyboardManager.hideSoftInputFromInputMethod(mChatEdt.getWindowToken(),0);
                    mChatEmoView.setVisibility(VISIBLE);
                    mChatEmoBtn.setBackgroundResource(R.drawable.sns_shoot_keyboard_icon_normal);
                }else
                {
                    mChatEmoView.setVisibility(GONE);
                    mChatEmoBtn.setBackgroundResource(R.drawable.sns_shoot_emotion_icon_normal);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onCustomEmoClick(String group_id,String emo_name) {
        listener.onSendEmo(group_id+":"+emo_name);
    }

    @Override
    public void onDeleteButtonClick() {
        mChatEdt.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_DEL));
        mChatEdt.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_DEL));
    }

    @Override
    public void onEmoSendClick() {

    }

    @Override
    public void onNormalEmoClick(String emo, int resId) {
        Editable editable=mChatEdt.getText();
        int index=mChatEdt.getSelectionEnd();
        emo="["+emo+"]";
        SpannableStringBuilder builder=new SpannableStringBuilder(emo);
        Drawable d=getResources().getDrawable(resId);
        d.setBounds(0,0,mEmoSize,mEmoSize);
        ImageSpan span=new ImageSpan(d);
        builder.setSpan(span,0,emo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (index<mChatEdt.length()){
            editable.insert(index,builder);
        }else
        {
            editable.append(builder);
        }
        mChatEdt.setSelection(index+emo.length());

    }
}
