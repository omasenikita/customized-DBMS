/*
===============================================================================
   File Name    : FileDBMS.java
   Author       : Nikita omase
   Description  : 
       This project simulates a small Database Management System (DBMS)
       using Java. It supports basic SQL-like operations on Employee records
       such as Insert, Select, Delete, Update, Backup/Restore, Sorting, 
       Aggregate functions, and Exporting data to CSV.

       Data is stored in memory using a LinkedList and persisted using 
       Serialization. Provides a console menu for interaction.

    Date         : 10/06/2024
    
===============================================================================
*/

import java.io.*;
import java.util.*;

// Employee class (acts as a row in the Employee table)
class Employee implements Serializable {
    public int EmpID;
    public String EmpName;
    public int EmpAge;
    public String EmpAddress;
    public int EmpSalary;

    private static int Counter;

    // Static block initializes the Employee ID counter
    static {
        Counter = 1;
    }

    // Constructor to initialize Employee record
    public Employee(String name, int age, String address, int salary) {
        this.EmpID = Counter++;
        this.EmpName = name;
        this.EmpAge = age;
        this.EmpAddress = address;
        this.EmpSalary = salary;
    }

    // Display Employee info
    public void DisplayInformation() {
        System.out.println(toString());
    }

    // Convert Employee info to String
    public String toString() {
        return "ID : " + this.EmpID + 
               " | Name : " + this.EmpName + 
               " | Age : " + this.EmpAge + 
               " | Address : " + this.EmpAddress + 
               " | Salary : " + this.EmpSalary;
    }
}

// DBMS class (acts like a mini database)
class MarvellousDBMS implements Serializable {
    private LinkedList<Employee> Table;

    // Constructor
    public MarvellousDBMS() {
        System.out.println("Marvellous DBMS started successfully...");
        Table = new LinkedList<>();
    }

    // Insert a new record into Employee table
    public void InsertIntoTable(String name, int age, String address, int salary) {
        Employee eobj = new Employee(name, age, address, salary);
        Table.add(eobj);
        System.out.println("✅ New record inserted successfully.");
    }

    // Select all records
    public void SelectStarFrom() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("Data from Employee table");
        System.out.println("--------------------------------------------------");
        for (Employee eref : Table) {
            System.out.println(eref);
        }
        System.out.println("--------------------------------------------------\n");
    }

    // Take a backup (Serialization)
    public void TakeBackup() {
        try {
            FileOutputStream fos = new FileOutputStream("MarvellousDBMS.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            System.out.println("✅ Database backup stored successfully.");
        } catch (Exception eobj) {
            System.out.println("❌ Exception occurred during backup...");
        }
    }

    // Restore a backup
    public static MarvellousDBMS RestoreBackup(String path) {
        try {
            MarvellousDBMS ret = null;
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ret = (MarvellousDBMS) ois.readObject();
            System.out.println("✅ Database restored successfully.");
            return ret;
        } catch (Exception eobj) {
            System.out.println("❌ Exception occurred while restoring backup...");
            return null;
        }
    }

    // Select by ID
    public void SelectSpecificID(int id) {
        for (Employee eref : Table) {
            if (eref.EmpID == id) {
                System.out.println(eref);
                return;
            }
        }
        System.out.println("No record found with ID = " + id);
    }

    // Select by Name
    public void SelectSpecificName(String name) {
        boolean found = false;
        for (Employee eref : Table) {
            if (name.equalsIgnoreCase(eref.EmpName)) {
                System.out.println(eref);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No record found with Name = " + name);
        }
    }

    // Delete by ID
    public void DeleteSpecificID(int id) {
        Iterator<Employee> it = Table.iterator();
        while (it.hasNext()) {
            Employee e = it.next();
            if (e.EmpID == id) {
                it.remove();
                System.out.println("✅ Record deleted successfully.");
                return;
            }
        }
        System.out.println("No record found with ID = " + id);
    }

    // Update salary by ID
    public void UpdateSalaryByID(int id, int newSalary) {
        for (Employee e : Table) {
            if (e.EmpID == id) {
                e.EmpSalary = newSalary;
                System.out.println("✅ Salary updated for ID " + id);
                return;
            }
        }
        System.out.println("No record found with ID = " + id);
    }

    // Sort by Salary
    public void SortBySalary() {
        Table.sort((e1, e2) -> e1.EmpSalary - e2.EmpSalary);
        System.out.println("✅ Employees sorted by salary.");
        SelectStarFrom();
    }

    // Aggregate functions
    public void CountEmployees() {
        System.out.println("Total employees: " + Table.size());
    }

    public void MaxSalary() {
        int max = Table.stream().mapToInt(e -> e.EmpSalary).max().orElse(0);
        System.out.println("Highest salary: " + max);
    }

    public void MinSalary() {
        int min = Table.stream().mapToInt(e -> e.EmpSalary).min().orElse(0);
        System.out.println("Lowest salary: " + min);
    }

    public void AverageSalary() {
        double avg = Table.stream().mapToInt(e -> e.EmpSalary).average().orElse(0);
        System.out.println("Average salary: " + avg);
    }

    // Search employees in age range
    public void SelectByAgeRange(int minAge, int maxAge) {
        boolean found = false;
        for (Employee e : Table) {
            if (e.EmpAge >= minAge && e.EmpAge <= maxAge) {
                System.out.println(e);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No employees found in given age range.");
        }
    }

    // Export table to CSV file
    public void ExportToCSV(String filename) {
        try (PrintWriter pw = new PrintWriter(new File(filename))) {
            pw.println("ID,Name,Age,Address,Salary");
            for (Employee e : Table) {
                pw.println(e.EmpID + "," + e.EmpName + "," + e.EmpAge + "," + e.EmpAddress + "," + e.EmpSalary);
            }
            System.out.println("✅ Data exported successfully to " + filename);
        } catch (Exception e) {
            System.out.println("❌ Error exporting data...");
        }
    }
}

// Main class with menu-driven interface
public class FileDBMS {
    public static void main(String[] args) throws Exception {
        MarvellousDBMS mobj = MarvellousDBMS.RestoreBackup("MarvellousDBMS.ser");
        if (mobj == null) {
            mobj = new MarvellousDBMS();
        }

        Scanner sobj = new Scanner(System.in);
        int iOption = 0;

        while (iOption != 20) {
            System.out.println("\n---------------- Marvellous DBMS -----------------");
            System.out.println("1  : Insert new employee");
            System.out.println("2  : Select * from employee");
            System.out.println("3  : Take backup");
            System.out.println("4  : Select by ID");
            System.out.println("5  : Select by Name");
            System.out.println("6  : Delete by ID");
            System.out.println("7  : Update salary by ID");
            System.out.println("8  : Sort employees by salary");
            System.out.println("9  : Show max salary");
            System.out.println("10 : Show min salary");
            System.out.println("11 : Show average salary");
            System.out.println("12 : Show total employees");
            System.out.println("13 : Search employees by age range");
            System.out.println("14 : Export data to CSV");
            System.out.println("20 : Exit DBMS");
            System.out.print("--------------------------------------------------\nEnter option: ");

            iOption = sobj.nextInt();
            sobj.nextLine(); // clear buffer

            switch (iOption) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sobj.nextLine();
                    System.out.print("Enter age: ");
                    int age = sobj.nextInt(); sobj.nextLine();
                    System.out.print("Enter address: ");
                    String address = sobj.nextLine();
                    System.out.print("Enter salary: ");
                    int salary = sobj.nextInt();
                    mobj.InsertIntoTable(name, age, address, salary);
                    break;

                case 2: mobj.SelectStarFrom(); break;
                case 3: mobj.TakeBackup(); break;
                case 4:
                    System.out.print("Enter ID: ");
                    mobj.SelectSpecificID(sobj.nextInt()); break;
                case 5:
                    System.out.print("Enter name: ");
                    mobj.SelectSpecificName(sobj.nextLine()); break;
                case 6:
                    System.out.print("Enter ID: ");
                    mobj.DeleteSpecificID(sobj.nextInt()); break;
                case 7:
                    System.out.print("Enter ID: ");
                    int id = sobj.nextInt();
                    System.out.print("Enter new salary: ");
                    int newSalary = sobj.nextInt();
                    mobj.UpdateSalaryByID(id, newSalary);
                    break;
                case 8: mobj.SortBySalary(); break;
                case 9: mobj.MaxSalary(); break;
                case 10: mobj.MinSalary(); break;
                case 11: mobj.AverageSalary(); break;
                case 12: mobj.CountEmployees(); break;
                case 13:
                    System.out.print("Enter min age: ");
                    int minAge = sobj.nextInt();
                    System.out.print("Enter max age: ");
                    int maxAge = sobj.nextInt();
                    mobj.SelectByAgeRange(minAge, maxAge);
                    break;
                case 14:
                    System.out.print("Enter filename (e.g., data.csv): ");
                    mobj.ExportToCSV(sobj.nextLine());
                    break;
                case 20:
                    System.out.println("Thank you for using Marvellous DBMS!");
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }
}
