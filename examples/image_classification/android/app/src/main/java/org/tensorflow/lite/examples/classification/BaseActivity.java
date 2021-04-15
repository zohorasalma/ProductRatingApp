package org.tensorflow.lite.examples.classification;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity
{

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");

        ProgressDialog mProgressDialogCancelable = new ProgressDialog(this);
        mProgressDialogCancelable.setIndeterminate(true);
        mProgressDialogCancelable.setMessage("Request is being processed");
    }

    public void showProgressDialog()
    {

        if (mProgressDialog != null && !mProgressDialog.isShowing()) mProgressDialog.show();
    }

    public void hideProgressDialog()
    {

        if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.dismiss();
    }

    public void showToastMessage(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}