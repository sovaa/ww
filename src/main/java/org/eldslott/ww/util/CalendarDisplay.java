package org.eldslott.ww.util;

import java.io.Serializable;

public class CalendarDisplay implements Serializable {
    private static final long serialVersionUID = -1473309751110176532L;
    
    private Calendar day;
    private Calendar month;
    private Calendar season;
    private Calendar year;
    
    private String dateTh;
    private String displayString;
    
    private int date;
    
    private String[] days = { "Morndas" , "Tirdas", "Middas", "Turdas",
        					  "Fredas", "Loredas", "Sundas"};
    
    private String[] months = { "Morning Star", "Sun's Dawn", "First Seed", "Rain's Hand",
        						"Second Seed", "Midyear", "Sun's Height", "Last Seed", "Hearthfire",
        						"Frostfall", "Sun's Dusk", "Evening Star"};
    
    private String[] seasons = { "Winter", "Spring", "Summer", "Fall"};
    
    public CalendarDisplay() {
        day = new Calendar(7);
        month = new Calendar(12);
        season = new Calendar(4);
        year = new Calendar(2012);
        setDate(5,29,0,0,1012);
        updateDisplay();
    }
    
    public String tickFuture(int days) {
        Integer tdate = date;
        
        Calendar tday = day.copy();
        Calendar tmonth = month.copy();
        Calendar tseason = season.copy();
        Calendar tyear = year.copy();
        
        for (int i = 0; i < days; i++) {
            tdate = tick(tdate, tday, tmonth, tseason, tyear);
        }
        
        String future = updateDisplay(tdate, "", tday, tmonth, tseason, tyear);
        
        return future;
    }

    public void tick() {
        tick(date, day, month, season, year);
    }
    
    private Integer tick(Integer date, Calendar day, Calendar month, Calendar season, Calendar year) {
        if (date < 30) {
        	date++;
        }
        else {
        	date = 1;
        }
        
        day.increment();
        
        if(date == 1) {
        	month.increment();
        	
        	if(month.getValue() == 3 || month.getValue() == 6 || month.getValue() == 9 || month.getValue() == 12) {
        		season.increment();
        		
        		if(season.getValue() == 0) {
        			year.increment();
        		}
        	}
        }
        
        return date;
    }
    
    public void setDate(int day, int date, int month, int season, int year) {
        this.day.setValue(day);
        this.date = date;
        this.month.setValue(month);
        this.season.setValue(season);
        this.year.setValue(year);
    }
    
    public String getDate() {
        return displayString;
    }
    
    private boolean endsIn(int i, int j) {
        if (i == j) {
            return true;
        }
        
        String s = String.valueOf(i);
        
        if (s == null || s.trim().length() == 0) {
            return false;
        }
        
        int l = s.length();
        
        if (l < 2) {
            return false;
        }
        
        String t = s.substring(l - 1);
        
        if (String.valueOf(j).equals(t)) {
            return true;
        }
        
        return false;
    }

    public String updateDisplay() {
        return updateDisplay(date, dateTh, day, month, season, year);
    }
    
    private String updateDisplay(int date, String dateTh, Calendar day, Calendar month, Calendar season, Calendar year) {
        if (endsIn(date, 1)) {
        	dateTh = date + "st";
        }
        else if (endsIn(date, 2)) {
        	dateTh = date + "nd";
        }
        else if (endsIn(date, 3)) {
        	dateTh = date + "rd";
        }
        else {
        	dateTh = date + "th";
        }
        
        return days[day.getValue()] + " " +
        	   dateTh + " of " +
        	   months[month.getValue()] + ", " +
    		   seasons[season.getValue()] + " in the year " +
    		   year.getDisplayValue();
    }
}
