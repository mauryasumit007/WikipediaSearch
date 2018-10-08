package in.androidmate.mauryasumit.wikidata.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.androidmate.mauryasumit.wikidata.Others.ItemClickListener;
import in.androidmate.mauryasumit.wikidata.R;
import in.androidmate.mauryasumit.wikidata.adapters.WikiAdapter;
import in.androidmate.mauryasumit.wikidata.models.Wikiresponse;
import in.androidmate.mauryasumit.wikidata.ui.web.WikiDetail;

public class MainActivity extends AppCompatActivity implements MainContractorInterface.MainViewInterface,ItemClickListener {

    @BindView(R.id.recyclerViewWiki)
    RecyclerView recyclerViewWiki;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.label)
    TextView label;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String TAG = "MainActivity";

    WikiAdapter adapter;
    MainPresenter mainPresenter;
    Wikiresponse wikiresponse;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupMVP();
        setupViews();
    }



    private void setupMVP()
    {
        mainPresenter = new MainPresenter(this,new MainModel());
    }

    private void setupViews(){
        setSupportActionBar(toolbar);
        recyclerViewWiki.setLayoutManager(new LinearLayoutManager(this));
    }



    @Override
    public void showToast(String str) {
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        label.setVisibility(View.VISIBLE);
        recyclerViewWiki.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        label.setVisibility(View.GONE);
        recyclerViewWiki.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayWikidata(Wikiresponse wikiResponse) {


        if(wikiResponse!=null && wikiResponse.getQuery()!=null) {

            hideProgressBar();

            wikiresponse = wikiResponse;
            adapter = new WikiAdapter(wikiResponse.getQuery().getPages(), MainActivity.this);
            recyclerViewWiki.setAdapter(adapter);
            adapter.setClickListener(this);

        }else{
            showProgressBar();
            Log.d(TAG,"No response");
            Toast.makeText(MainActivity.this,"This content not available on Wikipedia",Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public void displayError(String e) {

        showToast(e);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Enter Topic to search..");
        mainPresenter.onButtonClick(searchView,MainActivity.this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_search){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position) {

            Intent moveToWebview= new Intent(MainActivity.this, WikiDetail.class);
            moveToWebview.putExtra("WIki_Title",wikiresponse.getQuery().getPages().get(position).getTitle());
            startActivity(moveToWebview);

    }
}
