package com.farflights.appointments.appointments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


//Todo By Tuesday @11AM
    //Todo Booking
    //Todo upcoming
    //Todo edit and cancel reservation
    //Todo decide if it's reservation or appointment and stick to a consistent word

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Final variables of Node names. In case they change in the php
    static final String KEY_ID = "ID";
    static final String KEY_NAME = "Name";
    static final String KEY_NUMBER = "Number";
    static final String KEY_LINE1 = "Line_1";
    static final String KEY_PIC = "Picture";
    static final String KEY_PNAME = "PName";
    static final String KEY_COST = "Cost";
    static final String KEY_PRIORITY = "Priority";
    static final String KEY_RATING ="Rating";
    static final String KEY_DESCRIPTION = "Description";
    static final String KEY_FAVOURITED = "Favourited";

    static final String KEY_BOOKED_TIME ="Time";
    static final String KEY_RESERVATION_ID = "ReservationID";
    static final String KEY_PRIORITY_LEVEL = "BookedPriority";
    static final String KEY_BUMPED = "Bumped";

    static final String KEY_TAG_A = "Booking";
    static final String KEY_TAG_B = "business"; //The tag for the parent nodes of business in the xml



    static final String KEY_USER_ID = "UserIDKey";
    static final String KEY_TOKEN = "loginTokenKey";
    static final String KEY_DETAILS = "UserDetails";

    static final String KEY_FNAME = "FirstName";
    static final String KEY_LNAME = "LastName";
    static final String KEY_EMAIL = "email";

    SharedPreferences sharedPrefs;


    private NodeList nodeList;
    RecyclerView recyclerViewForFavorSearch;
    String page;

    TextView tv;

    ArrayList<Business> businessObjArrayList = new ArrayList<Business>();//ArrayList of businesses for Favourite/Search functions
    ArrayList<Booking>  bookingObjArrayList = new ArrayList<Booking>(); //ArrayList of bookings for upcoming



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        makeNav();

        tv = (TextView) findViewById(R.id.sampleText);
        recyclerViewForFavorSearch = (RecyclerView) findViewById(R.id.list);




    }

    protected boolean loggedinCheck(){

        sharedPrefs = getSharedPreferences(KEY_DETAILS, Context.MODE_PRIVATE);

        if(!(sharedPrefs.contains(KEY_USER_ID))||!(sharedPrefs.contains(KEY_TOKEN))){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        setContentView(R.layout.activity_main);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_upcoming) {//Navigates to upcoming
            //setContentView(R.layout.activity_main);
            Intent intent = new Intent(this, Upcoming.class);
            startActivity(intent);
        } else if (id == R.id.nav_browse) {//Navigates to Browse
            Intent intent = new Intent(this, Browse.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {//Navigates to Search
            Intent intent = new Intent(this, Search.class);
            startActivity(intent);
        }    else if (id == R.id.nav_favourites) {//Navigates to Favourites
            Intent intent = new Intent(this, Favourites.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {//Navigates to Settings
            Intent intent = new Intent(this, SettingsMenu.class);
            startActivity(intent);
        }else if(id==R.id.nav_logout){//Navigates to logout
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    // DownloadAndParseXML AsyncTask
    protected class DownloadAndParseXML extends AsyncTask<String, Void, Void> {


        String pParams[];
        @Override
        protected Void doInBackground(String... inParams) {
            pParams = inParams;
            try {
                URL url = new URL(pParams[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                // Download the XML file
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
                // Locate the Tag Name
                Log.d("DownloadXML", "Above pParams check");
                Log.d("pParam[1]", pParams[1]);
                if(pParams[1].equals("Favourites")||pParams.equals("Search")||pParams[1].equals("Browse")) {
                    Log.d("Inside pParams", "Favourites, Search and Browse");
                    nodeList = doc.getElementsByTagName(KEY_TAG_B);
                }else if(pParams[1].equals("Upcoming")){
                    nodeList = doc.getElementsByTagName(KEY_TAG_A);
                }

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;

        }//End of doInBackground

        @Override
        protected void onPostExecute(Void args) {
            if(pParams[1].equals("Favourites")||pParams[1].equals("Search")||pParams[1].equals("Browse")) {
                //Clear the businessArrL arrayList
                businessObjArrayList.clear();
                Log.d("onPostExecute", "After busAL Clear");
                for (int temp = 0; temp < nodeList.getLength(); temp++) {//Loops through all businessObj nodes
                    Log.d("Inside businessObj loop", "Here");
                    Node nNode = nodeList.item(temp);
                    businessObjArrayList.add(getBusinessFromBusinessNode(nNode));//Adds the node info to the array list
                }

            }else if(pParams[1].equals("Upcoming")){
                //clear the booking array list
                bookingObjArrayList.clear();
                if(nodeList!=null) {
                    for (int temp = 0; temp < nodeList.getLength(); temp++) {//Loops through all Booking nodes
                        Node nNode = nodeList.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element tempElement = (Element) nNode;
                            Booking tempBooking = new Booking();

                            Log.d("Test", getNode(KEY_BOOKED_TIME, tempElement));

                            tempBooking.setPriorityLevel(Integer.valueOf(getNode(KEY_PRIORITY_LEVEL, tempElement)));
                            tempBooking.setTime(getNode(KEY_BOOKED_TIME, tempElement));
                            tempBooking.setReservationID(Integer.valueOf(getNode(KEY_RESERVATION_ID, tempElement)));
                            tempBooking.setBumped(Boolean.parseBoolean(getNode(KEY_BUMPED, tempElement)));

                            Node businessNode = tempElement.getElementsByTagName(KEY_TAG_B).item(0);//Should only be one business so item 0 is it
                            tempBooking.setBusinessObj(getBusinessFromBusinessNode(businessNode));


                            bookingObjArrayList.add(tempBooking);
                        }

                    }
                }

            }
            page = pParams[1];
           updateView();

        }//End of onPostExcecute method

        private Business getBusinessFromBusinessNode(Node inNode) {
            if (inNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) inNode;
                NodeList priorities = eElement.getElementsByTagName(KEY_PRIORITY);

                Business tempB = new Business();//Create a new temporary businessObj object

                //Gets information from xml and inserts into the temporary businessObj object
                tempB.setId(getNode(KEY_ID, eElement));

                for (int i = 0; i < priorities.getLength(); i++) {//Loops through all priority child nodes
                    Node tNode = priorities.item(i);
                    if (tNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element ele = (Element) tNode;

                        tempB.addPriority(getNode(KEY_PNAME, ele), Double.parseDouble(getNode(KEY_COST, ele)));
                    }
                }
                Log.d("Name", getNode(KEY_NAME, eElement));
                tempB.setName(getNode(KEY_NAME, eElement));
                tempB.setAddrNumber(getNode(KEY_NUMBER, eElement));
                tempB.setAddrLine1(getNode(KEY_LINE1, eElement));
                tempB.setPic(getNode(KEY_PIC, eElement));
                tempB.setRating(Double.valueOf(getNode(KEY_RATING, eElement))); //Sample fix later
                tempB.setDescription(getNode(KEY_DESCRIPTION, eElement));
                tempB.setFavourited(Boolean.parseBoolean(getNode(KEY_FAVOURITED, eElement)));

                return tempB;
            }
            return null;
        }



        // getNode function
        private String getNode(String sTag, Element eElement) {
            NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                    .getChildNodes();
            Node nValue = (Node) nlList.item(0);
            return nValue.getNodeValue();
        }


    }



    //Updates the view with the businessArrL info;
    protected void updateView(){
        if(page.equals("Favourites")||page.equals("Search")||page.equals("Browse")){
            Log.d("FSB", page);
            if(!businessObjArrayList.isEmpty()) {//Checks to make sure there are businesses in the search result

                initRecyclerCardViews();

            }else{
                tv.setText(R.string.no_favourites);
            }
        }else if(page.equals("Upcoming")){
            if(!bookingObjArrayList.isEmpty()) {//Checks to make sure there are businesses in the search result
                Log.d("Testing", "InitRecyclerCardViews runs");
                initRecyclerCardViews();

            }else{
                tv.setText(R.string.no_upcoming);
            }

        }
    }

    protected void makeNav(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView navName = (TextView) findViewById(R.id.navbar_name);
        TextView navEmail = (TextView) findViewById(R.id.navbar_email);

        sharedPrefs = getSharedPreferences(KEY_DETAILS, Context.MODE_PRIVATE);
        Log.d("Huh?", "wat");
        String name= sharedPrefs.getString(KEY_FNAME, null)+" "+sharedPrefs.getString(KEY_LNAME, null);

        //navName.setText(name);


    }


    private void initRecyclerCardViews(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerAdapter adapter = new RecyclerAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        Log.d("Further Test", "End of initRecyclerView");
    }



    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private Context context;
        private final View.OnClickListener mOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPos = recyclerViewForFavorSearch.getChildLayoutPosition(v);

                if (page.equals("Favourites") || page.equals("Search")||page.equals("Browse")){
                    // Starting Single Business intent
                    Intent in = new Intent(getApplicationContext(), SingleBusinessActivity.class);

                    in.putExtra("businessFrom", businessObjArrayList.get(itemPos));
                    startActivity(in);
                }else if(page.equals("Upcoming")){
                    //Starting Single Booking intent
                    Intent in = new Intent(getApplicationContext(), SingleBookingActivity.class);

                    in.putExtra("bookingFrom", bookingObjArrayList.get(itemPos));
                    startActivity(in);

                }
            }
        };


        public RecyclerAdapter(Context context) {
            this.context = context;

        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (page.equals("Favourites") || page.equals("Search")||page.equals("Browse")) {
                Picasso.with(context).load(businessObjArrayList.get(i).getPic()).resize(80, 80).into(viewHolder.pic);
                viewHolder.name.setText(businessObjArrayList.get(i).getName());
                viewHolder.number.setText(businessObjArrayList.get(i).getNumandLine1());
            }else if(page.equals("Upcoming")){
                Picasso.with(context).load(bookingObjArrayList.get(i).getBusinessObj().getPic()).resize(80, 80).into(viewHolder.pic);
                viewHolder.name.setText(bookingObjArrayList.get(i).getBusinessObj().getName());
                viewHolder.number.setText(bookingObjArrayList.get(i).getBusinessObj().getNumandLine1());
                viewHolder.date.setText(bookingObjArrayList.get(i).getTime());

                if(bookingObjArrayList.get(i).isBumped()){
                    ((CardView) viewHolder.itemView).setCardBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
                }
            }

        }

        @Override
        public int getItemCount() {
            if (page.equals("Favourites") || page.equals("Search")||page.equals("Browse")) {

                return businessObjArrayList.size();
            }else if(page.equals("Upcoming")){
                return bookingObjArrayList.size();
            }

            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView name; TextView number; TextView date;
            ImageView pic;
            public ViewHolder(View view) {
                super(view);

                name = (TextView)view.findViewById(R.id.businessName);
                pic = (ImageView)view.findViewById(R.id.picture);
                number = (TextView)view.findViewById(R.id.number);
                date = (TextView)view.findViewById(R.id.date);
                view.setOnClickListener(mOnclickListener);

            }
        }
    }//End of Recycler Adapter


    protected void logout(){
        Intent intent = new Intent(this, Logout.class);
        startActivity(intent);
    }


}
