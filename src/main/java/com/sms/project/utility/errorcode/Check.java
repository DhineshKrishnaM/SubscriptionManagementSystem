package com.sms.project.utility.errorcode;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Scanner;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
@AllArgsConstructor
@NoArgsConstructor
public class Check {
    private static final String SEPARATOR = ",";
    int id;
    String name;
//
    public static void main(String[] args) {
        String REGEX = "(\\w+)(:gt:|:gte:|:lt:|:lte:|:eq:|:sw:|:ew:|:lk:|:in:|:inl:|:inn:)(((\\w+|\\[((\\\"(\\w+)\\\")+|,)*\\])+)|(\\w+))"+SEPARATOR;
//String REGEX="(\\w+)(:gt:|:gte:|:lt:|:lte:|:eq:|:sw:|:ew:|:lk:|:in:|:inl:|:inn:)(((\\w+|\\[((\\\"(\\w+)\\\")+|,)*\\])+)|(\\w+ \\w+))"+SEPARATOR;
////        Pattern p = Pattern.compile(REGEX);//. represents single character
////        Matcher m = p.matcher("field:gt:10");
////        boolean b = m.matches();
////        System.out.println(b);
////
//        boolean b2= Pattern.compile(REGEX).matcher("field_mn:gt:man").matches();
//        System.out.println(b2);
////        boolean b3 = Pattern.matches("(\\[((\\\"(\\w+)\\\")+|,)*\\])", "word1 word2 [\"word3\",\"word4\",\"word5\"]");
////        System.out.println(b3);
//
//
////        List<String> li = Arrays.asList("prince","prince prince", "play", "people");
////        String ss = "prince prince";
////        for (int i = 0; i < li.size(); i++) {
////            Pattern pattern = Pattern.compile(REGEX);
////            Matcher matcher = pattern.matcher(li.get(i));
////            boolean matchFound = matcher.find();
////            if(matchFound == true){
////                System.out.println(li.get(i));
////            }
////            else{
////                continue;
////            }
////        }
//
//
//        // create a REGEX String
////            String REGEX = "(\\w+)(:gt:|:gte:|:lt:|:lte:|:eq:|:sw:|:ew:|:lk:|:in:|:inl:|:inn:)(((\\w+|\\[((\\\"(\\w+)\\\")+|,)*\\])+)|(\\w+ \\w+))" + SEPARATOR;
//
////
////                    String input = "test test";
            String input="mani_nam:gt:vijay vijay";
//                    String regex = "(\\w+)(:gt:|:gte:|:lt:|:lte:|:eq:|:sw:|:ew:|:lk:|:in:|:inl:|:inn:)(((\\w+|\\[((\\\"(\\w+)\\\")+|,)*\\])+)|(\\w+))";
                    Pattern pattern = Pattern.compile(REGEX);
                    Matcher matcher = pattern.matcher(input);
////
                    if (matcher.find()) {
                        System.out.println("Matched!");
                    } else {
                        System.out.println("Not matched!");
                    }
////                }
////            }
//
//
////        public class Main {
//
//        Check c = new Check(1, "test");
//        Check c1 = new Check(1, "test test");
//        Check c2 = new Check(1, "table");
//        List<Check> vv = Arrays.asList(c, c1, c2);
//        Scanner sc = new Scanner(System.in);
//        String s1 = sc.next();
//        String input = "field_name:sw:test";
//
//
//        String regex = "(\\w+)(:gt:|:gte:|:lt:|:lte:|:eq:|:sw:|:ew:|:lk:|:in:|:inl:|:inn:)(((\\w+|\\[((\"(\\w+)\")+|,)*\\])+)|(\\w+ \\w+))";
//        Pattern pattern = Pattern.compile(regex);
//
    }
}
//
//
//
//
//
//
