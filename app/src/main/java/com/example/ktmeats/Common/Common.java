package com.example.ktmeats.Common;

import com.example.ktmeats.Model.User;

public class Common {
    public static User currentUser;

    public static final String DELETE = "Delete";

    public static String convertStatusCode (String status){
        if(status.equals("0"))
            return "Order Placed";
        else if(status.equals("1"))
            return "On making";
        else
            return "Shipped";
    }

}
