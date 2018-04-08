/*
 * Copyright (C) 2014 Bluetooth Connection Template
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.parkseunghyeon.embedded.fragments;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.parkseunghyeon.embedded.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExampleFragment extends Fragment implements View.OnClickListener {

    private Context mContext = null;
    private IFragmentListener mFragmentListener = null;
    private Handler mActivityHandler = null;

    TextView mTextChat;
    EditText mEditChat;
    Button mBtnSend;

    String totalStr = "";
    String rfid = "";
    Boolean flag = true;

    public ExampleFragment(Context c, IFragmentListener l, Handler h) {
        mContext = c;
        mFragmentListener = l;
        mActivityHandler = h;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);

        mTextChat = (TextView) rootView.findViewById(R.id.text_chat);
        mTextChat.setMaxLines(1000);
        mTextChat.setVerticalScrollBarEnabled(true);
        mTextChat.setMovementMethod(new ScrollingMovementMethod());

        mEditChat = (EditText) rootView.findViewById(R.id.edit_chat);
        mEditChat.setOnEditorActionListener(mWriteListener);

        mBtnSend = (Button) rootView.findViewById(R.id.button_send);
        mBtnSend.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send:
                String message = mEditChat.getText().toString();
                if (message != null && message.length() > 0)
                    sendMessage(message);
                break;
        }
    }


    // The action listener for the EditText widget, to listen for the return key
    private TextView.OnEditorActionListener mWriteListener =
            new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                    // If the action is a key-up event on the return key, send the message
                    if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                        String message = view.getText().toString();
                        if (message != null && message.length() > 0)
                            sendMessage(message);
                    }
                    return true;
                }
            };

    // Sends user message to remote
    private void sendMessage(String message) {
        if (message == null || message.length() < 1)
            return;
        // send to remote
        if (mFragmentListener != null)
            mFragmentListener.OnFragmentCallback(IFragmentListener.CALLBACK_SEND_MESSAGE, 0, 0, message, null, null);
        else
            return;
        // show on text view
        if (mTextChat != null) {
            mTextChat.append("\nSend: ");
            mTextChat.append(message);
            int scrollamout = mTextChat.getLayout().getLineTop(mTextChat.getLineCount()) - mTextChat.getHeight();
            if (scrollamout > mTextChat.getHeight())
                mTextChat.scrollTo(0, scrollamout);
        }
        mEditChat.setText("");
    }

    private static final int NEW_LINE_INTERVAL = 1000;
    private long mLastReceivedTime = 0L;

    // Show messages from remote
    public void showMessage(String message) {

        if (message != null && message.length() > 0) {
            long current = System.currentTimeMillis();

            /*if (current - mLastReceivedTime > NEW_LINE_INTERVAL) {
                mTextChat.append("\nRcv: ");
            }
            */
//    		mTextChat.append(message);

            totalStr += message;
            totalStr.trim();

            int index = totalStr.lastIndexOf('.');

            // 현재시간을 msec 으로 구한다.
            long now = System.currentTimeMillis();
            // 현재시간을 date 변수에 저장한다.
            Date date = new Date(now);
            // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            // nowDate 변수에 값을 저장한다.
            String formatDate = sdfNow.format(date);

            if (index != -1) {
                switch (totalStr.charAt(index - 1)) {
                    case 'M':
                        if (totalStr.charAt(index - 2) == '0') {
                            mTextChat.append(formatDate + "문 열림\n");
                        }
                        if (totalStr.charAt(index - 2) == '1') {
                            mTextChat.append(formatDate + "문 닫힘\n");
                        }
                        break;
                    case 'R':
                        if (totalStr.charAt(index - 2) == '0')
                            mTextChat.append(formatDate + "사람 있음\n");
                        break;
                    case 'D':
                        if (flag) {
                            rfid = totalStr.substring(index - 5, index - 1);
                            flag = false;
                        } else {
                            if (rfid.equals(totalStr.substring(index - 5, index - 1))) {
                                mTextChat.append(formatDate + "사용자 허가\n");
                            } else {
                                mTextChat.append(formatDate + "사용자 미허가\n");
                            }
                        }

//                        mTextChat.append(totalStr.substring(index - 5, index - 1));

                        break;
                }
//    			mTextChat.append(String.valueOf(totalStr.charAt(index-1)));
            }

            int scrollamout = mTextChat.getLayout().getLineTop(mTextChat.getLineCount()) - mTextChat.getHeight();
            if (scrollamout > mTextChat.getHeight())
                mTextChat.scrollTo(0, scrollamout);


            mLastReceivedTime = current;
        }
    }

}
