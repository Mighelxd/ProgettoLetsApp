package com.example.utente.progettoletsapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import com.example.utente.progettoletsapp.Route;

/**
 * Created by Mai Thanh Hiep on 4/3/2016.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
