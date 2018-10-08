package in.androidmate.mauryasumit.wikidata.ui.main;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.androidmate.mauryasumit.wikidata.models.Wikiresponse;
import in.androidmate.mauryasumit.wikidata.network.NetworkClient;
import in.androidmate.mauryasumit.wikidata.network.NetworkInterface;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainModel implements MainContractorInterface {
    Wikiresponse wikiresponse;


    @Override
    public void getWikiResponse(OnFinishedListener listener,OnErrorListener errorListener, SearchView searchView, Activity activity, MainContractorInterface.MainViewInterface mvi) {

        getObservableQuery(searchView,activity,mvi)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        if(s.equals("")){
                            return false;
                        }else{

                            Log.d("typed",s);

                            Map<String, String> data = new HashMap<>();
                            data.put("query", s);



                            return true;
                        }
                    }
                })
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap(new Function<String, Observable<Wikiresponse>>() {
                    @Override
                    public Observable<Wikiresponse> apply(@NonNull String s) throws Exception {
                        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                                .getWikiResults(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver(listener,errorListener));



    }

    private Observable<String> getObservableQuery(android.support.v7.widget.SearchView searchView, final Activity activity,final MainContractorInterface.MainViewInterface mvi){

        final PublishSubject<String> publishSubject = PublishSubject.create();

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                mvi.showProgressBar();

                publishSubject.onNext(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


/*Temporarily,I have Commented below code as currently it is creating thread intruption conflict with above publishsubject method and causing error in search sometimes(search while type and search after search button pressed) not always, otherwise it works fine too. */



            // publishSubject.onNext(newText);
                return true;
            }
        });

        return publishSubject;
    }



    public DisposableObserver<Wikiresponse> getObserver(final OnFinishedListener listener,final OnErrorListener errorListener){
        return new DisposableObserver<Wikiresponse>() {

            @Override
            public void onNext(@NonNull Wikiresponse query) {

                listener.onFinished(query);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("LOG","Error"+e);
                e.printStackTrace();

                errorListener.onError("Some Error Occured");

            }

            @Override
            public void onComplete() {
                Log.d("LOG","Completed");

            }
        };
    }






}
