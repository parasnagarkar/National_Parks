package com.example.national_parks.data;

import com.example.national_parks.model.Park;

import java.util.List;

public interface AsyncResponse {
    void processPark(List<Park> parks);
}
