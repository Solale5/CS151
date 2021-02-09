import java.text.DateFormatSymbols;
        import java.time.LocalDate;
        import java.util.*;
        





public class practice {


    public static void sop(Object x){
        System.out.println(x);
    }
    public static void sopl(Object x){
        System.out.print(x);
    }
    public static void main(String[] args) {
        LocalDate cal = LocalDate.now();
        printCalendar(cal);
        Scanner sc = new Scanner(System.in);
        
        while (sc.hasNextLine())
                {
                        String input = sc.nextLine();
                        if (input.equals("p"))
                        {
                                cal = cal.minusMonths(1); // LocalDateTime is immutable
                                printCalendar(cal);
                        }
                        else if (input.equals("n"))
                        {
                                cal = cal.plusMonths(1); // LocalDateTime is immutable
                                printCalendar(cal);
                                
                        }
                }
    }
    public static void printCalendar(LocalDate todaydate)
    {  
        
        
        //store local date
        
        // LocalDate todaydate = LocalDate.of(2022,02, 25);
        int dayNum = todaydate.getDayOfMonth();
        
        Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
        ht.put("SUNDAY", 0);
        ht.put("MONDAY", 1);
        ht.put("TUESDAY", 2);
        ht.put("WEDNESDAY", 3);
        ht.put("THURSDAY", 4);
        ht.put("FRIDAY", 5);
        ht.put("SATURDAY", 6);

// gets first day of the month as a date variable
        LocalDate first = todaydate.withDayOfMonth(1);

//prints the first day of the month code ex 1 => Monday 2=> tuesday
        sop(ht.get(first.getDayOfWeek()+""));

//gets last day of the month
        
        int res= todaydate.lengthOfMonth();
        

        //gets the day of the week that falls on the first of the month (ex feb 2021 first day is monday) and returns it as a code
        int firstdayofmonthcode = ht.get(first.getDayOfWeek()+"");


        sop(todaydate.getMonth() + " " +todaydate.getDayOfMonth()+ " "+ todaydate.getYear());
        //gets date abbreviations
        DateFormatSymbols dfs = new DateFormatSymbols();
        // stores abbreviations in an array
        String[] days = dfs.getShortWeekdays();
        //prints days
        for (String string : days) {
            sopl(string + " ");
        }
        //counter for days
        int l =0;
        sop("");
        //spaces array based on what the first day of the month is
        String[] spaces = {" ","     ","         ", "             ", "                 ", "                     ","                         "};
        //loop responsible for printing the first row
        for (int i = 0; i < 7-firstdayofmonthcode; i++) {
            if( i == 0)
                sopl(spaces[firstdayofmonthcode]+"");

            l+=1;
            sopl(l + "   ");
        }
        
        sop("");
        //loop responsible for printing the remaining rows
        for (int i = 8-firstdayofmonthcode; i <= res; i++) {
           
            if(i - (8-firstdayofmonthcode)== 7 ||i - (8-firstdayofmonthcode)==  14|| i - (8-firstdayofmonthcode)== 21 ||i - (8-firstdayofmonthcode)==  28){
                sop( "");
            }

            


            if( i == 8-firstdayofmonthcode){
                sopl(" "+ i + "   ");
            } else if(i == dayNum){
                sopl("["+ i+ "]"+ " ");
            }
            else if(i==9 || i == 10)
            {
                sopl(i + "  ");
            }else if(i >10){
                sopl(i + "  ");

            }else{
                sopl(i + "   ");
            }



        }
        sop("\n"+"loading is not implemented" );
        
        
    }
}
