package com.example.instaclone.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.instaclone.R;

public class ConfirmPasswordDialog extends DialogFragment {



    public interface OnPasswordConfirmListener {
        public void onPasswordConfirm(String password);
    }
    private OnPasswordConfirmListener onPasswordConfirmListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_password, container, false);

        final EditText mPassword = view.findViewById(R.id.confirm_password);

        TextView confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mPassword.getText().toString();
                if (!password.isEmpty()) {
                    onPasswordConfirmListener.onPasswordConfirm(password);
                    getDialog().dismiss();
                } else {
                    Toast.makeText(getContext(), "please input password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onPasswordConfirmListener = (OnPasswordConfirmListener) context;
        } catch (ClassCastException e) {
            e.getMessage();
        }
    }
}
