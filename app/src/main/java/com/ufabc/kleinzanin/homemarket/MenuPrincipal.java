package com.ufabc.kleinzanin.homemarket;

    import android.app.Fragment;
    import android.app.FragmentManager;
    import android.content.Intent;
    import android.content.res.Configuration;
    import android.content.res.TypedArray;
    import android.os.Bundle;
    import android.support.v4.app.ActionBarDrawerToggle;
    import android.support.v4.widget.DrawerLayout;
    import android.support.v7.app.ActionBarActivity;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ListView;

    import com.ufabc.kleinzanin.homemarket.adapter.NavDrawerListAdapter;
    import com.ufabc.kleinzanin.homemarket.model.NavDrawerItem;

    import java.util.ArrayList;


public class MenuPrincipal extends ActionBarActivity {

        private DrawerLayout mDrawerLayout;
        private ListView mDrawerList;
        private ActionBarDrawerToggle mDrawerToggle;

        // nav drawer title
        private CharSequence mDrawerTitle;

        // used to store app title
        private CharSequence mTitle;

        // slide menu items
        private String[] navMenuTitles;
        private TypedArray navMenuIcons;

        private ArrayList<NavDrawerItem> navDrawerItems;
        private NavDrawerListAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_menu_principal);


            createMenu(savedInstanceState);

        }

        /**
         * Slide menu item click listener
         * */
        private class SlideMenuClickListener implements
                ListView.OnItemClickListener {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // display view for selected nav drawer item
                displayView(position);
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        };


    private void initProdutos() {
        startActivity((new Intent(this,Produto.class)));
    }

    private void initReceitas() {
        startActivity((new Intent(this,ReceitasMain.class)));
    }

    private void initDespensa(){
        startActivity((new Intent(this,Despensa.class)));
    }

    private void initMercados() {
        startActivity((new Intent(this,MercadoMain.class)));
    }

    private void initCompras() {
        startActivity((new Intent(this,ListadeCompras.class)));
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // toggle nav drawer on selecting action bar app icon/title
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
            // Handle action bar actions click
            switch (item.getItemId()) {
                case R.id.action_settings:
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        /* *
         * Called when invalidateOptionsMenu() is triggered
         */
        @Override
        public boolean onPrepareOptionsMenu(Menu menu) {
            // if nav drawer is opened, hide the action items
            boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
            menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
            return super.onPrepareOptionsMenu(menu);
        }

        /**
         * Diplaying fragment view for selected nav drawer list item
         * */
        private void displayView(int position) {
            // update the main content by replacing fragments
            Fragment fragment = null;
            switch (position) {
               case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    initProdutos();
                    break;
                case 2:
                    initDespensa();
                    break;
                case 3:
                    initReceitas();
                    break;
                case 4:
                    fragment = new ReceitasDetailFragment();
                    break;
                case 5:
                    initCompras();
                    break;
                case 6:
                    initCompras();
                    break;
                case 7:
                    fragment = new MercadoDetailFragment();
                    break;
                case 8:
                    fragment = new MercadoListFragment();
                    break;
                case 9:
                    fragment = new VariacaoPrecoFragment();
                    break;
                case 10:
                    fragment = new ConfigFragment();
                    break;
                default:
                    break;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();

                // update selected item and title, then close the drawer
                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                setTitle(navMenuTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                // error in creating fragment
                Log.e("MainActivity", "Error in creating fragment");
            }
        }

        @Override
        public void setTitle(CharSequence title) {
            mTitle = title;
            getSupportActionBar().setTitle(mTitle);
        }

        /**
         * When using the ActionBarDrawerToggle, you must call it during
         * onPostCreate() and onConfigurationChanged()...
         */

        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            // Sync the toggle state after onRestoreInstanceState has occurred.
            mDrawerToggle.syncState();
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            // Pass any configuration change to the drawer toggls
            mDrawerToggle.onConfigurationChanged(newConfig);
        }

        public void createMenu(Bundle savedInstanceState){
            // load slide menu items
            navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

            // nav drawer icons from resources
            navMenuIcons = getResources()
                    .obtainTypedArray(R.array.nav_drawer_icons);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

            navDrawerItems = new ArrayList<NavDrawerItem>();

            // adding nav drawer items to array
            // Home
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
            // Produto
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
            // Editar Produto
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],11));
            // Receitas
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(2, -1)));
            // Receitas Possiveis
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], 11));
            // Compras
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(3, -1)));
            // Lista de Compras
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], 11));
            // Localizar Mercados
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], 11));
            // Merc. Cadastrados
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], 11));
            // Variação de Preços
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], 11));
            // Config
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[11], navMenuIcons.getResourceId(4, -1)));

            // Recycle the typed array
            navMenuIcons.recycle();

            mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

            // setting the nav drawer list adapter
            adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
            mDrawerList.setAdapter(adapter);

            // enabling action bar app icon and behaving it as toggle button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.drawable.ic_drawer, //nav menu toggle icon
                    R.string.app_name, // nav drawer open - description for accessibility
                    R.string.app_name // nav drawer close - description for accessibility
            ) {
                public void onDrawerClosed(View view) {
                    getSupportActionBar().setTitle(mTitle);
                    // calling onPrepareOptionsMenu() to show action bar icons
                    invalidateOptionsMenu();
                }

                public void onDrawerOpened(View drawerView) {
                    getSupportActionBar().setTitle(mDrawerTitle);
                    // calling onPrepareOptionsMenu() to hide action bar icons
                    invalidateOptionsMenu();
                }


            };
            mDrawerLayout.setDrawerListener(mDrawerToggle);

            if (savedInstanceState == null) {
                // on first time display view for first nav item
                displayView(0);
            }
        }
}
