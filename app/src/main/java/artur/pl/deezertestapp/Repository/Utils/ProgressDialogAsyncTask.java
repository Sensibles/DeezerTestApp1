package artur.pl.deezertestapp.Repository.Utils;

import android.app.Application;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import artur.pl.deezertestapp.DeezerTestApp;

/**
 * Created by artur on 23.12.2017.
 */

public abstract class ProgressDialogAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    protected ProgressDialog progressDialog;
    protected DeezerTestApp application;
    protected String msgText;


    public ProgressDialogAsyncTask(DeezerTestApp application, String msgText) {
        super();
        this.application = application;
        this.msgText = msgText;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(
                (application)
                        .getCurrentActivity()
        );
        progressDialog.setMessage(msgText);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Result result) {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
