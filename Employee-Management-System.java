import java.util.*;
import java.io.*;
import java.time.*;

interface Employee{
    int calculateSalary(int salary); //abstract method calculateSalary()
    void addEmployee(); //abstract method addEmployee()
}

//abstarct class AddEmployee as it doesn't override all abstract method of Employee
abstract class AddEmployee implements Employee{ 
    protected static Scanner scanner = new Scanner(System.in);
    static String name, type, date;
    static int id, salary;
    static boolean flag = true;
    static LocalDate dob;
    
    //abstract method addEmployee defined
    public void addEmployee(){
        try{
            
            while(flag){
                System.out.print("Enter First Name:\t");
                String first_name = scanner.next();
                
                System.out.print("Enter Last Name:\t");
                String last_name = scanner.next();
                
                name = first_name+" "+last_name;
                
                System.out.print("Enter ID:\t");
                id = scanner.nextInt();
                
                System.out.print("Enter DOB(YYYY-MM-DD):\t");
                date = scanner.next();
                dob = LocalDate.parse(date);
                
                System.out.print("Enter Employee Type(Part Time/Full Time):\t");
                type = scanner.next();
                
                if(type.equalsIgnoreCase("part")){
                    System.out.print("Enter Salary/hour:\t");
                    salary = scanner.nextInt();

                    addEmployee(name,id,dob,type,salary);

                    System.out.print("\nAdd More?(Y/N):\t");
                    String yes_no = scanner.next();

                    if(yes_no.equalsIgnoreCase("y")) flag = true;
                    else flag = false;
                }
                
                else if(type.equalsIgnoreCase("full")){
                    System.out.print("Enter Salary/month:\t");
                    salary = scanner.nextInt();

                    addEmployee(name,id,dob,type,salary);

                    System.out.print("\nAdd More?(Y/N):\t");
                    String yes_no = scanner.next();

                    if(yes_no.equalsIgnoreCase("y")) flag = true;
                    else flag = false;
                }
                else throw new InputMismatchException("\nWrong Employee Type. Please Try Again.\n\n\n"); //throws Exception
                       
            }
            
        }catch(DateTimeException d){ //catches DateTimeException caused by wrong dob input
            System.out.println("\nWrong DOB. Please Try Again.\n\n\n");
            addEmployee();
        }catch(InputMismatchException t){ //catches InputMismacth Exception caused by wrong type input
            System.out.println(t);
            
        }catch(Exception e){ //catches all other exceptions
            System.out.println("\nSome Error Occurred. Please Try Again.\n\n\n");
            addEmployee();
        }
        
    }
    
    //overloaded method addEmployee()
    static void addEmployee(String name, int id, LocalDate dob, String type, int salary)throws Exception{
            String filename = id+".txt";
            FileWriter file = new FileWriter(filename);
            
            file.write(salary+"\n");
            file.write(name+"\n");
            file.write(dob+"\n");
            file.write(type+"\n");
            
            file.close();
            
            System.out.println("\n\nAdded Sucessfully.");
    }
}

//extends abstract class AddEmployee
class FullTimeEmployee extends AddEmployee{
    static FullTimeEmployee fte = new FullTimeEmployee();
    
    //concrete class, all leftover abstract methods need to be defined
    public int calculateSalary(int salary){
        try{
            System.out.print("\n\nEnter the value and period(days/months/years)(Eg: 2 months):\t");
            int value = scanner.nextInt();
            String period = scanner.next();

            if(period.equalsIgnoreCase("days")){
                int sal = (salary/31)*value; //calculate salary for days
                return sal;
            }
            
            else if(period.equalsIgnoreCase("months")){
                int sal = salary*value; //calculate salary for months
                return sal;
            }
            
            else if(period.equalsIgnoreCase("years")){
                int sal = (salary*12)*value; //calculate salary for years
                return sal;
            }
            
            else throw new Exception();
            
        }catch(Exception e){ //catches any Exception
            System.out.println("\nWrong Input. Please Try Again.\n\n");
            fte.calculateSalary(salary);
            return 0;
        }
            
    }
}

//extends abstract class AddEmployee
class PartTimeEmployee extends AddEmployee{
    static PartTimeEmployee pte = new PartTimeEmployee();
    
    //concrete class, all leftover abstract methods need to be defined
    public int calculateSalary(int salary){
        try{
            System.out.print("\n\nEnter the value and period(hours/days/months)(Eg: 2 months):\t");
            int value = scanner.nextInt();
            String period = scanner.next();

            if(period.equalsIgnoreCase("hours")){
                int sal = salary*value; //calculate salary for hours
                return sal;
            }
            
            else if(period.equalsIgnoreCase("days")){
                int sal = (salary*8)*value; //calculate salary for days
                return sal;
            }
            
            else if(period.equalsIgnoreCase("months")){
                int sal = (salary*8*31)*value; //calculate salary for months
                return sal;
            }
            
            else throw new Exception();
            
        }catch(Exception e){ //catches any Exception
            System.out.println("\nWrong Input. Please Try Again.\n\n");
            pte.calculateSalary(salary);
            return 0;
        }
    }
}

//class with main method
class EmployeeManagementSystem {
    static FullTimeEmployee fte = new FullTimeEmployee(); //creates object of FullTimeEmployee class
    static PartTimeEmployee pte = new PartTimeEmployee(); //creates object of PartTimeEmployee class
    static int salary_detail;
    static Scanner scanner = new Scanner(System.in); //creates object of Scanner

    //main method
    public static void main(String [] ak){
        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        
        System.out.println("\n\n\t\t\t\tWelcome To Vision IT Solutions\n\n\n");
        
        menuList();
    }
    
    //method with menu options
    static void menuList(){
        try{
            System.out.println("1. Add Employee");
            System.out.println("2. Check Employee");
            System.out.println("3. Exit\n\n");
            
            int choice = scanner.nextInt();
            
            if(choice == 1){
                fte.addEmployee(); //call addEmployee method of interface
                menuList();
            } 
            else if(choice == 2) checkEmployee();
            else if(choice == 3) System.exit(0); //exits the program
            else throw new Exception(); //throws an Exception
            
        }catch(InputMismatchException d){ //catches InputMismatchException
            scanner.nextLine();
            System.out.println("Error! Wrong Input, Please Try Again!!\n\n\n");
            menuList();
        }catch(Exception e){ //catches all other Exception
            System.out.println("Error! Wrong Input, Please Try Again!!\n\n\n");
            menuList();
        }
    }
    
    static void checkEmployee(){
        try{
            System.out.print("\n\nEnter Employee ID:\t");
            int id = scanner.nextInt();
            
            String filename = id+".txt";
            File file = new File(filename); //opens file with respet to entered id
            Scanner read = new Scanner(file);
            
            //reads the data in file
            String salary = read.nextLine();
            String name = read.nextLine();
            String dob = read.nextLine();
            String type = read.nextLine();
            
            System.out.println("\nName:\t"+name);
            System.out.println("DOB:\t"+dob);
            System.out.println("Type:\t"+(type.equalsIgnoreCase("part")?"Part Time":"Full Time"));
            System.out.println("Salary:\tRs. "+salary);
            
            salaryCheck(salary,type);
            
        }catch(Exception e){ //Exception if there is no employee present with entered id
            System.out.println("\nError! Wrong Input. Please Try Again!!\n\n");
            checkEmployee();
        }
    }
    
    static void salaryCheck(String salary, String type){
        int sal = Integer.parseInt(salary);
        int choice = 0;
        
        choice = (type.equalsIgnoreCase("full")?1:2);
        
        if(choice == 1){
            salary_detail = fte.calculateSalary(sal); //calls calculateSalary() method of FullTimeEmployee class
        }
        
        else if(choice == 2){
            salary_detail = pte.calculateSalary(sal); //calls calculateSalary() method of PartTimeEmployee class
        }
        
        System.out.println("\nSalary:\tRs. "+salary_detail+"\n\n"); //prints calculated salary
        menuList();
    }
}
