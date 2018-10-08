package in.androidmate.mauryasumit.wikidata.ui.main;

import android.app.Activity;
import android.support.v7.widget.SearchView;
import android.widget.ProgressBar;

import in.androidmate.mauryasumit.wikidata.models.Wikiresponse;

public interface MainContractorInterface {


    // Network request interface

    interface OnFinishedListener {
        void onFinished(Wikiresponse wikiresponse);
    }

    // Error listener interface

    interface OnErrorListener {
        void onError(String error);
    }

    // Main Action interface

     interface MainPresenterInterface {

        void onButtonClick(SearchView searchview, Activity activity);
    }


    // VIew interface

     interface MainViewInterface {

        void showToast(String s);
        void showProgressBar();
        void hideProgressBar();
        void displayWikidata(Wikiresponse wikiresponse);
        void displayError(String s);
    }

    // Method to get response and send to presenter

    void getWikiResponse(OnFinishedListener listener,OnErrorListener errorListener, SearchView searchView, Activity activity, MainContractorInterface.MainViewInterface mvi);

}
