package in.androidmate.mauryasumit.wikidata.ui.main;


import android.app.Activity;

import in.androidmate.mauryasumit.wikidata.models.Wikiresponse;


public class MainPresenter implements MainContractorInterface.MainPresenterInterface,MainContractorInterface.OnFinishedListener,MainContractorInterface.OnErrorListener {

    MainContractorInterface.MainViewInterface viewInterface;
    private MainContractorInterface mainContractorInterface;



    public MainPresenter(MainContractorInterface.MainViewInterface viewInterface, MainContractorInterface mainContractorInterface) {
        this.viewInterface = viewInterface;
        this.mainContractorInterface = mainContractorInterface;
    }


    @Override
    public void onButtonClick(android.support.v7.widget.SearchView searchview, Activity activity) {

        mainContractorInterface.getWikiResponse(this,this,searchview,activity, viewInterface);


    }

    @Override
    public void onFinished(Wikiresponse wikiresponse) {

        if(wikiresponse!=null)
        {
        viewInterface.displayWikidata(wikiresponse);

        }

    }

    @Override
    public void onError(String error) {

        viewInterface.displayError(error);
    }
}
