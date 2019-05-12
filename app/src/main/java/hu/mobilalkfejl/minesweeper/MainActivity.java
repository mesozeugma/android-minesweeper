package hu.mobilalkfejl.minesweeper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Map> {
    private ImageView[][] tilesIV;
    private TextView statusView;
    private Map map;
    private int gameCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        statusView = findViewById(R.id.statusView);
        getTilesIV();
        gameCounter = 0;
        if(getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null,
                    this);
        }
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    private void getTilesIV() {
        tilesIV = new ImageView[Map.sizeX][Map.sizeY];
        for(int i = 0; i < Map.sizeX; i++){
            for(int j = 0; j< Map.sizeY; j++){
                int resID = getResources().getIdentifier("tile_" + i + "_" + j , "id", getPackageName());
                tilesIV[i][j] = (ImageView) findViewById(resID);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_start) {
            getSupportLoaderManager().restartLoader(0, null, this);
        }

        return super.onOptionsItemSelected(item);
    }

    public void tileClick(View view){
        if(map == null){
            return;
        }

        String[] renSplit = getResources().getResourceEntryName(view.getId()).split("_");
        int posX = Integer.parseInt(renSplit[1]);
        int posY = Integer.parseInt(renSplit[2]);

        if(map.remainingTiles() > 0){
            map.clickTile(posX, posY);
            updateTilesIV();
            updateGameStatus();
        }
    }

    public void updateTilesIV(){
        String[][] typeMap = map.getTypeMap();

        for(int i = 0; i < Map.sizeX; i++){
            for(int j = 0; j< Map.sizeY; j++){
                switch (typeMap[i][j]){
                    case "mine":
                        tilesIV[i][j].setImageResource(R.drawable.ic_minesweeper_mine);
                        break;
                    case "0":
                        tilesIV[i][j].setImageResource(R.drawable.ic_minesweeper_0);
                        break;
                    case "1":
                        tilesIV[i][j].setImageResource(R.drawable.ic_minesweeper_1);
                        break;
                    case "2":
                        tilesIV[i][j].setImageResource(R.drawable.ic_minesweeper_2);
                        break;
                    case "3":
                        tilesIV[i][j].setImageResource(R.drawable.ic_minesweeper_3);
                        break;
                    case "4":
                        tilesIV[i][j].setImageResource(R.drawable.ic_minesweeper_4);
                        break;
                    case "5":
                        tilesIV[i][j].setImageResource(R.drawable.ic_minesweeper_5);
                        break;
                    case "6":
                        tilesIV[i][j].setImageResource(R.drawable.ic_minesweeper_6);
                        break;
                    case "7":
                        tilesIV[i][j].setImageResource(R.drawable.ic_minesweeper_7);
                        break;
                    case "8":
                        tilesIV[i][j].setImageResource(R.drawable.ic_minesweeper_8);
                        break;
                    default:
                        tilesIV[i][j].setImageResource(R.drawable.ic_minesweeper_unknown);
                }
            }
        }
    }

    public void updateGameStatus(){
        switch(map.remainingTiles()){
            case 0:
                statusView.setText("You won");
                break;
            case -1:
                statusView.setText("Game over");
                break;
            default:
                statusView.setText("Remaining tiles: " + String.valueOf(map.remainingTiles()));
        }
    }

    @NonNull
    @Override
    public Loader<Map> onCreateLoader(int i, @Nullable Bundle bundle) {
        return  new StartGameLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Map> loader, Map map) {
        this.map = map;
        if(gameCounter > 0){
            updateTilesIV();
        }
        updateGameStatus();
        gameCounter++;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Map> loader) {

    }
}
