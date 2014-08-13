package com.example.labolsadecompras;

import java.util.ArrayList;
import java.util.List;

import service.TimerService;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class MainActivity extends Activity
implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	//   private SearchView searchView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startService(new Intent(this, TimerService.class));

		mNavigationDrawerFragment = (NavigationDrawerFragment)                
				getFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(
				R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}


	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		get_categories(position);
	}

	private void get_categories(final int position){
		List<String> list= new ArrayList<String>();

		switch(position){
		case 0: //INICIO
			break;
		case 1: //HOMBRES
		case 2: //MUJERES
			list.add(getString(R.string.categ_section1));
			list.add(getString(R.string.categ_section2));
			list.add(getString(R.string.categ_section3));
			break;
		case 3: //INFANTILES
		case 4: //BEBES
			list.add(getString(R.string.categ_section4));
			list.add(getString(R.string.categ_section5));
			break;
		}
		start_category_fragment(list,position);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id){
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case R.id.action_login:
			startActivity(new Intent(this, LoginActivity.class));
			return true;
		case R.id.action_logout:
			SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
			editor
			.remove("username")
			.remove("password")
			.remove("authenticationtoken")
			.commit();
			invalidateOptionsMenu();
			return true;
		case R.id.action_order:
			startActivity(new Intent(this,OrderListActivity.class));
			return true;
			/*
        	case R.id.search_bar:
        		getActionBar().setDisplayHomeAsUpEnabled(false);
        		getActionBar().setDisplayShowTitleEnabled(false);
        		getActionBar().setDisplayShowHomeEnabled(false);
        		return true;
			 */
		}
		return super.onOptionsItemSelected(item);
	}

	public void start_category_fragment(List<String> list,int position){
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
		.replace(R.id.container, PlaceholderFragment.newInstance(list,position + 1))
		.commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.app_name);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		case 4:
			mTitle = getString(R.string.title_section4);
			break;
		case 5:
			mTitle = getString(R.string.title_section5);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.main, menu);

			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
			if(sp.getString("authenticationtoken", null) == null){
				menu.findItem(R.id.action_logout).setVisible(false);
				menu.findItem(R.id.action_order).setVisible(false);
			}else{
				menu.findItem(R.id.action_login).setVisible(false);
			}

			ActionBar actionBar = getActionBar(); 
			actionBar.setCustomView(R.layout.search_bar); 

			actionBar.setDisplayShowCustomEnabled(true);
			SearchView searchview = (SearchView) actionBar.getCustomView().findViewById(R.id.search_bar);
			TextView textview = (TextView) actionBar.getCustomView().findViewById(R.id.title_bar);
			textview.setText(mTitle);

			searchview.setOnQueryTextListener(new OnQueryTextListener(){
				@Override
				public boolean onQueryTextChange(String arg0) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean onQueryTextSubmit(String query) {
					Intent intent = new Intent(MainActivity.this, SearchActivity.class);
					intent.putExtra("SEARCH", query);
					startActivity(intent);
					return false;
				}
			});
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	protected void onResume(){
		super.onResume();
		invalidateOptionsMenu();
	}

	/*
    public boolean onPrepareOptionsMenu(Menu menu){
    	menu.clear();
    	if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
    		getMenuInflater().inflate(R.menu.main, menu);
    		restoreActionBar(mTitle.toString());
    		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
    		if(sp.getString("authenticationtoken", null) == null){
    			menu.findItem(R.id.action_logout).setVisible(false);
    		}else{
    			menu.findItem(R.id.action_login).setVisible(false);
    		}
            return true;
        }
        return super.onPrepareOptionsMenu(menu);
    }
	 */

	public void restoreActionBar(String s) {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(s);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	@SuppressLint("ValidFragment")
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_GENDER_NUMBER = "gender_number";
		private static List<String> categories=null;

		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 * @param list 
		 */
		public static PlaceholderFragment newInstance(List<String> list, int gender) {
			PlaceholderFragment fragment = new PlaceholderFragment(list);
			Bundle args = new Bundle();
			args.putInt(ARG_GENDER_NUMBER, gender);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment(){

		}
		public PlaceholderFragment(List<String> categories) {
			setCategories(categories);
		}


		public static void setCategories(List<String> categories) {
			PlaceholderFragment.categories = categories;

		}
		public static List<String> getCategories() {
			return categories;
		}

		//SECCION DE CADA ITEM DEL MENU
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getCategories());

			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			ListView catlistView = (ListView) rootView.findViewById(R.id.section_label);
			catlistView.setAdapter(adapter);

			catlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent= new Intent(getActivity(),SubcategoryActivity.class);

					//position es de la categoria, empieza en 0
					intent.putExtra("CATEGORY",position);

					//numero de genero, empieza en 1: inicio,hombres,mujeres,infantiles,bebes
					intent.putExtra("GENDER", getArguments().getInt(ARG_GENDER_NUMBER));

					//string de lo clickeado
					Object item=parent.getItemAtPosition(position);
					String subtitle= item.toString();
					intent.putExtra("TITLE", getGender(getArguments().getInt(ARG_GENDER_NUMBER)));
					intent.putExtra("SUBTITLE",subtitle);

					startActivity(intent);
				}
			});

			return rootView;
		}

		private String getGender(int gender){
			switch(gender){
			case 1://TO:DO inicio
				break;
			case 2:
				return getString(R.string.title_section2);
			case 3:
				return getString(R.string.title_section3);
			case 4:
				return getString(R.string.title_section4);
			case 5:
				return getString(R.string.title_section5);
			}
			return ""; 
		}
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(
					getArguments().getInt(ARG_GENDER_NUMBER));
		}
	}
}