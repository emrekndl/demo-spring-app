package com.example.demo.finalexam.profanityfilter;


import lombok.Data;

@Data
public class ProfanityFilterResponse {

    private String original;
    private String censored;
    private boolean hasProfanity;   
}