package com.example.tablayout.Services;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tablayout.R;
import com.example.tablayout.Database.WordHelper;
import com.example.tablayout.Prefs.AppPreference;
import com.example.tablayout.model.WordModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

class LoadDataAsync extends AsyncTask<Void, Integer, Boolean> {

    private final String TAG = LoadDataAsync.class.getSimpleName();
    private final WordHelper wordHelper;
    private final AppPreference appPreference;
    private final WeakReference<LoadDataCallBack> weakCallback;
    private final WeakReference<Resources> weakResources;

    LoadDataAsync(WordHelper wordHelper, AppPreference appPreference, LoadDataCallBack callback, Resources resources) {
        this.wordHelper = wordHelper;
        this.appPreference = appPreference;
        this.weakCallback = new WeakReference<>(callback);
        this.weakResources = new WeakReference<>(resources);
    }

    /***
     * Persiapan sebelum proses dimulai
     * Berjalan di Main Thread
     */
    @Override
    protected void onPreExecute() {
        weakCallback.get().onPreLoad();
    }

    /***
     * Proses background terjadi di method doInBackground
     * @param voids
     * @return berupa boolean insertSucces
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean firstRun = appPreference.getFirstRun();
        double maxprogress = 100;
        if (firstRun){
            ArrayList<WordModel> wordModels = preLoadRaw();
            ArrayList<WordModel> wordModelsBetawi = preLoadRawBetawi();

            wordHelper.open();

            double progress = 0;
            publishProgress((int) progress);
            Double progressMaxInsert = 50.0;
            Double progressDiff = (progressMaxInsert - progress) / wordModels.size();
            Double progressDiffBetawi = (progressMaxInsert - progress) / wordModelsBetawi.size();

            boolean isInsertSuccess;
            try {
                wordHelper.beginTransaction();
                //looping data kamus indo
                for (WordModel model : wordModels){
                    if (isCancelled()){
                        break;
                    } else {
                        wordHelper.insertTransaction(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                }

                //looping data kamus betawi
                for (WordModel model : wordModelsBetawi){
                    if (isCancelled()){
                        break;
                    }else {
                        wordHelper.insertTransactionBetawi(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                }

                //Jika posisinya di cancel ketika pertama kali aplikasi dibuka. maka posisinya isCancelled itu true
                if (isCancelled()){
                    //maka disini tidak di proses datanya
                    isInsertSuccess = false;
                    appPreference.setAppFirstRun(true);
                    weakCallback.get().onLoadCancel();
                }else {
                    wordHelper.setTransactionSuccess();
                    isInsertSuccess = true;

                    appPreference.setAppFirstRun(false);
                }
            } catch (Exception e){
                Log.e(TAG, "doInBackground: Exception");
                isInsertSuccess = false;
            } finally {
                wordHelper.endTransaction();
            }
            wordHelper.close();

            publishProgress((int) maxprogress);

            return isInsertSuccess;
        } else {
            try {
                synchronized (this){
                    this.wait(2000);

                    publishProgress(50);

                    this.wait(2000);
                    publishProgress((int) maxprogress);

                    return true;
                }
            } catch (Exception e){
                return false;
            }
        }
    }

    /***
     * Method ini digunakan untuk Update Progress nya
     * @param values digunakan sepagai argumen dalam callback pada metode onProgressupdate
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        weakCallback.get().onProgressUpdate(values[0]);
    }

    /***
     * Setelah proses selesai
     * Berjalan di Main Thread
     * @param result jika result true akan memanggil callback onLoadSuccess
     */
    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            //jika resul true akan memanggil call back onLoadSuccess
            weakCallback.get().onLoadSuccess();
        } else {
            weakCallback.get().onLoadFailed();
        }

    }

    /***
     * Parsing raw data text berupa data menjadi array Kamus Indonesia
     * @return array model dari semua kata
     */
    private ArrayList<WordModel> preLoadRaw() {
        ArrayList<WordModel> wordModels = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            //Memanggil file raw indonesia betawi
            Resources res = weakResources.get();
            InputStream raw_dict = res.openRawResource(R.raw.indonesia_betawi);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            while ((line = reader.readLine()) != null) {
                String[] splitstr = line.split("\t");

                WordModel wordModel = new WordModel(splitstr[0], splitstr[1]);
                wordModels.add(wordModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordModels;
        }

    private ArrayList<WordModel> preLoadRawBetawi() {
        ArrayList<WordModel> wordModels = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = weakResources.get();
            InputStream raw_dict = res.openRawResource(R.raw.indonesia_betawi);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            while ((line = reader.readLine()) != null) {
                String[] splitstr = line.split("\t");

                WordModel wordModel = new WordModel(splitstr[0], splitstr[1]);
                wordModels.add(wordModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordModels;
    }

}
