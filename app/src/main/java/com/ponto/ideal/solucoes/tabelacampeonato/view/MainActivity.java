package com.ponto.ideal.solucoes.tabelacampeonato.view;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ponto.ideal.solucoes.tabelacampeonato.R;
import com.ponto.ideal.solucoes.tabelacampeonato.helper.TabelaAplication;
import com.ponto.ideal.solucoes.tabelacampeonato.model.Usuarios;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.Campeonatos.Sortear_Jogos;
import com.ponto.ideal.solucoes.tabelacampeonato.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.tabelacampeonato.util.util;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static NavController navController;
    DrawerLayout drawer;
    boolean doubleBackToExitPressedOnce=false;
    public static Toolbar toolbar;
    Context context;
    Handler handler;
    public static NavigationView navigationView;
    StorageReference storageReference;
    public static Menu menu1;

    public static Usuarios usuLog = new Usuarios();
    String uidLog;

    int teste;

    int destinationId;
    ConstraintLayout clmain;
    public static FloatingActionButton fab;

    private FirebaseAuth autenticacao;
    public static HomeViewModel homeViewModel;
    public static boolean isrunning=false;
    public static boolean sortear=true;

    public static String IDLIGA;
    private String name;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        clmain = findViewById(R.id.clmain);
        context = this;

        isrunning=true;

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        uidLog = FirebaseAuth.getInstance().getUid();

        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();

        TabelaAplication aplication = (TabelaAplication) getApplication();
        getApplication().registerActivityLifecycleCallbacks(aplication);

        fab.setVisibility(View.INVISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });





        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.getMenu().clear();
        if(usuLog.getStatus() ==100){
            navigationView.inflateMenu(R.menu.visitante_drawer);
        }else {
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                destinationId = destination.getId();

                switch (destinationId) {
                    case R.id.nav_home:
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        toolbar.setNavigationIcon(R.drawable.trofeu_bco);
                        toolbar.setTitle("Tabela Campeonato");
                      //  SPJOG = false;
                        break;
                    case R.id.criar_Liga:
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        toolbar.setNavigationIcon(R.drawable.liga_share_bco);
                        toolbar.setTitle("Liga Compartilhada");
                        break;
                    case R.id.convidar_para_Liga:
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        toolbar.setNavigationIcon(R.drawable.email_bco);
                        toolbar.setTitle("Convidar para Liga");
                        break;
                    case R.id.info_Liga:
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        toolbar.setNavigationIcon(R.drawable.escudo_branca_icon);
                        toolbar.setTitle("Info Liga");
                        break;
                    case R.id.inserir_Jogador:
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        toolbar.setNavigationIcon(R.drawable.ic_person_add_white_24dp);
                        toolbar.setTitle("Inserir jogador");
                        break;
                    case R.id.alterar_Foto_Visitante:
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        toolbar.setNavigationIcon(R.drawable.ic_person_add_white_24dp);
                        toolbar.setTitle("Alterar dados jogador");
                        break;
                    case R.id.novo_Campeonato:
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        toolbar.setNavigationIcon(R.drawable.trofeu_bco);
                        toolbar.setTitle("Novo Campeonato");
                        break;
                    case R.id.inscrever_Jogadores:
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        toolbar.setNavigationIcon(R.drawable.ic_person_add_white_24dp);
                        toolbar.setTitle("Inscrever Jogadores");
                        break;
                    case R.id.sortear_Jogos:
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        toolbar.setNavigationIcon(R.drawable.trofeu_bco);
                        toolbar.setTitle("Sortear Jogadores");
                        break;
                    case R.id.campeonato:
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        toolbar.setNavigationIcon(R.drawable.trofeu_bco);
                        toolbar.setTitle("Campeonato");
                        break;


                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int mMenu = item.getItemId();

                switch (mMenu) {
                    case R.id.nav_criar_liga:
                        drawer.closeDrawer(GravityCompat.START);
                        navController.navigate(R.id.action_nav_home_to_criar_Liga);
                        break;

                    case R.id.estatisticas:
                        drawer.closeDrawer(GravityCompat.START);



                    // navController.navigate(R.id.action_nav_home_to_estatisticas);
                        break;

//                    case R.id.nav_cadastro:
//                        drawer.closeDrawer(GravityCompat.START);
//                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
//                        Intent intent = new Intent(MainActivity.this, Login.class);
//                        intent.putExtra("cadastroativo",true);
//                        startActivity(intent, options.toBundle());
//                        finish();
//                        break;

                    case R.id.nav_home:
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_deslogar:
                        drawer.closeDrawer(GravityCompat.START);
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        autenticacao = FirebaseAuth.getInstance();
                        autenticacao.signOut();
                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(getString(R.string.default_web_client_id))
                                .requestProfile()
                                .requestEmail()
                                .build();
                        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
                        if(mGoogleSignInClient!=null)mGoogleSignInClient.revokeAccess();
                        Intent intent2 = new Intent(MainActivity.this,Login.class);
                        startActivity(intent2);
                        finish();
                        break;

                    case R.id.nav_convite_liga:
                        drawer.closeDrawer(GravityCompat.START);
                          convidarliga();
                        break;

                    case R.id.nav_sair:
                        if (doubleBackToExitPressedOnce) {
                            finish();
                        }
                        doubleBackToExitPressedOnce = true;
                        Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                doubleBackToExitPressedOnce = false;
                            }
                        }, 2000);
                        break;

                    case R.id.sincronizar:
                        drawer.closeDrawer(GravityCompat.START);

                        break;
                    case R.id.nav_ver_convites:
                        drawer.closeDrawer(GravityCompat.START);
                        navController.navigate(R.id.action_nav_home_to_lista_Convites);
                        break;
                }
                return false;
            }
        });
        if (uidLog != null) {

            FirebaseFirestore.getInstance().collection("/usuarios")
                .document(uidLog)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        Usuarios nusu = value.toObject(Usuarios.class);
                        if(nusu==null){
                            autenticacao.signOut();
                            Intent intent = new Intent(MainActivity.this,Login.class);
                            startActivity(intent);
                            finish();
                        }else {
                            usuLog = nusu;
                            name = nusu.getApelidousu();
                            homeViewModel.setUsulog(nusu);
                            HomeViewModel.setUsuLogado(nusu);
                            carregaNav();
                        }
                    }
                });

            updateToken();

//            Usuarios_Controller uc = new Usuarios_Controller(getBaseContext());
//            usuLog = uc.usuatual(uidLog);
//            homeViewModel.setUsulog(usuLog);
//            name=usuLog.getApelidousu();

        }



    }

    private void carregaNav(){

        final ImageView imageViewNav = navigationView.getHeaderView(0).findViewById(R.id.imageViewnav);
        ImageView imgstar1 = navigationView.getHeaderView(0).findViewById(R.id.imgstar1);
        TextView txtnomenav = navigationView.getHeaderView(0).findViewById(R.id.txtnomenav);
        TextView txttipousu = navigationView.getHeaderView(0).findViewById(R.id.txttipousu);
        TextView txtdatacad = navigationView.getHeaderView(0).findViewById(R.id.txtdatacad);
        txtnomenav.setText(name);
        switch (usuLog.getStatus()){
            case 1: txttipousu.setText("Player");imgstar1.setVisibility(View.INVISIBLE);break;
            case 2: txttipousu.setText("Gold");imgstar1.setVisibility(View.VISIBLE);break;
        }

        String scomp = util.fdma(util.ld_X_long(usuLog.getTimestamp()));
        txtdatacad.setText(scomp);
        final int heigth = 300;
        final int width = 300;

            FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference storageReference = storage.getReferenceFromUrl(util.loadurl(uidLog));
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri.toString()).resize(width, heigth).centerCrop().into(imageViewNav);
                }
            });

    }

    private void convidarliga() {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void updateToken() {
        final String token = FirebaseInstanceId.getInstance().getToken();
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(uid != null){
            FirebaseFirestore.getInstance().collection("usuarios")
                    .document(uid)
                    .update("token", token).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
        }
    }

    @Override
    public void onBackPressed(){

     //   ABRIR_NOVO =false;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            switch (destinationId) {
                case R.id.nav_home:
                    if (doubleBackToExitPressedOnce) {
                        finish();
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);
                    break;
                case R.id.convidar_para_Liga:

                    navController.navigate(R.id.action_convidar_para_Liga_to_info_Liga);
                    break;

                case R.id.lista_Convites:
                    navController.navigate(R.id.action_lista_Convites_to_nav_home);
                    break;
                case R.id.info_Liga:
                    navController.navigate(R.id.action_info_Liga_to_nav_home);
                    break;
                case R.id.inserir_Jogador:
                    MainActivity.navController.navigate(R.id.action_inserir_Jogador_to_info_Liga);
                    break;
                case R.id.alterar_Foto_Visitante:
                    MainActivity.navController.navigate(R.id.action_alterar_Foto_Visitante_to_info_Liga);
                    break;
                case R.id.novo_Campeonato:
                    navController.navigate(R.id.action_novo_Campeonato_to_nav_home);
                    break;
                case R.id.inscrever_Jogadores:
                    navController.navigate(R.id.action_inscrever_Jogadores_to_novo_Campeonato);
                    break;
                case R.id.sortear_Jogos:
                    if(Sortear_Jogos.sorteia)return;
                    navController.navigate(R.id.action_sortear_Jogos_to_inscrever_Jogadores);
                    break;
                case R.id.campeonato:
                    sortear=false;
                    navController.navigate(R.id.action_campeonato_to_nav_home);
//                    navController.navigate(R.id.action_campeonato_to_sortear_Jogos);
                    break;
                default:
                    getSupportFragmentManager().popBackStack();
            }
        }

    }

    @Override
    protected void onDestroy() {
        isrunning=false;
        super.onDestroy();
    }
}