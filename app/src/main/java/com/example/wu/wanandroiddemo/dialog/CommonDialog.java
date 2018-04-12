package com.example.wu.wanandroiddemo.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wu.wanandroiddemo.R;
import com.example.wu.wanandroiddemo.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wu on 2018/3/28.
 */

public class CommonDialog extends DialogFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_negative)
    Button btnNegative;
    @BindView(R.id.btn_positive)
    Button btnPositive;
    Unbinder unbinder;
    private String title;
    private OnPositiveClickListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.dialog_style);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_common_tip, null);
        unbinder = ButterKnife.bind(this, view);
        tvTitle.setText(title);
        builder.setView(view)
                .setCancelable(true);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.positive();
                dismiss();
            }
        });
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public interface OnPositiveClickListener {
        void positive();
    }

    public void setOnPositiveClickListener(OnPositiveClickListener listener) {
        this.listener = listener;
    }


}
