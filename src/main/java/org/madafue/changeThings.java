package org.madafue;

import java.util.Map;

public class changeThings {

    public static void changeClickFacebook(Map<Object, Object> workingCopy, String newValue) {
        if (workingCopy == null) {
            System.out.println("Invalid data structure. Cannot modify.");
            return;
        }

        if (workingCopy.containsKey("clickfacebook")) {
            try {
                boolean newBooleanValue = Boolean.parseBoolean(newValue.toLowerCase());
                workingCopy.put("clickfacebook", newBooleanValue);
                System.out.println("'clickfacebook' updated to: " + newBooleanValue);
            } catch (Exception e) {
                System.out.println("Invalid value. Please enter 'true' or 'false'.");
            }
        } else {
            System.out.println("'clickfacebook' not found in the data.");
        }
    }

    public static void changeClickTwitter(Map<Object, Object> workingCopy, String newValue) {
        if (workingCopy == null) {
            System.out.println("Invalid data structure. Cannot modify.");
            return;
        }

        if (workingCopy.containsKey("clicktwitter")) {
            try {
                boolean newBooleanValue = Boolean.parseBoolean(newValue.toLowerCase());
                workingCopy.put("clicktwitter", newBooleanValue);
                System.out.println("'clicktwitter' updated to: " + newBooleanValue);
            } catch (Exception e) {
                System.out.println("Invalid value. Please enter 'true' or 'false'.");
            }
        } else {
            System.out.println("'clicktwitter' not found in the data.");
        }
    }

    public static void changeTips(Map<Object, Object> workingCopy, int newValue) {
        if (workingCopy == null) {
            System.out.println("Invalid data structure. Cannot modify.");
            return;
        }

        if (workingCopy.containsKey("tips")) {
            if (String.valueOf(newValue).length() >= 3) {
                workingCopy.put("tips", newValue);
                System.out.println("'tips' updated to: " + newValue);
            } else {
                System.out.println("Invalid value. Please enter a number with at least 3 digits.");
            }
        } else {
            System.out.println("'tips' not found in the data.");
        }
    }

    public static void changePlayerName(Map<Object, Object> workingCopy, String newName) {
        if (workingCopy == null) {
            System.out.println("Invalid data structure. Cannot modify.");
            return;
        }

        if (workingCopy.containsKey("playername")) {

            workingCopy.put("playername", newName);
            System.out.println("'playername' updated to: " + newName);

        } else {
            System.out.println("'playername' not found in the data.");
        }
    }

    public static void changeDay(Map<Object, Object> workingCopy, int newValue) {
        if(checkNull(workingCopy)) {
            if (workingCopy.containsKey("day")) {
                workingCopy.put("day", newValue);
                System.out.println("'day' updated to: " + newValue);

            } else {
                System.out.println("'day' not found in the data.");
            }
        }

    }

    private static boolean checkNull(Map<Object, Object> workingCopy){
        if (workingCopy == null) {
            System.out.println("Invalid data structure. Cannot modify.");
            return false;
        }
        return true;
    }

    public static void changeRank(Map<Object, Object> workingCopy, int newValue) {
        if(checkNull(workingCopy)) {
            if (workingCopy.containsKey("rank")) {
                workingCopy.put("rank", newValue);
                System.out.println("'rank' updated to: " + newValue);

            } else {
                System.out.println("'rank' not found in the data.");
            }
        }
    }
}
