import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static void main(String args[]){
        //***************Create client objects************************************ */
        ArrayList<Client> clients = new ArrayList<>();

        clients.add(new Client("2000" + 1 , "Pasindu" , "Sinhalese" , "Doctor" , "Galle" , 21 , "Male") );
        clients.add(new Client("2000" + 2 , "Sankalpa" , "Sinhalese" , "Engineering" , "Galle" , 22 , "Male") );
        clients.add(new Client("2000" + 3 , "Imasha" , "Sinhalese" , "Doctor" , "Colombo" , 45 , "Female") );
        clients.add(new Client("2000" + 4 , "Resindu" , "Tamil" , "Teacher" , "Kany" , 35 , "Female") );
        //*************Create account objects of the client ************************** */
        clients.get(0).updateAccounts(new SavingsAccount(2000 + 1,"LKR","Galle",45000.00,"Saving Account",2.5));
        clients.get(0).updateAccounts(new CurrentAccount(2000 + 2,"LKR","Galle",45000.00,"Cuurent Account"));

        clients.get(1).updateAccounts(new SavingsAccount(2000 + 3,"LKR","Galle",50000.00,"Saving Account",2.5));
        clients.get(2).updateAccounts(new SavingsAccount(2000 + 4,"LKR","Galle",35000.00,"Saving Account",2.5));
        clients.get(3).updateAccounts(new SavingsAccount(2000 + 5 ,"LKR","Galle",35000.00,"Saving Account",2.5));
        clients.get(3).updateAccounts(new CurrentAccount(2000 + 6,"LKR","Kandy",15000.00,"Cuurent Account"));
        //****************Login atm  *********************** */
        ATM atm = new ATM(clients);
    
    }


}

//******************ATM Class************************************ */
class ATM{

    private ArrayList<Client> clients ; 
    private ArrayList<Transaction> transactions;
    // constructor of ATM class 
    public ATM(ArrayList<Client> clients) {
        this.clients = clients;
        this.transactions = new ArrayList<>(); // store transactons

        //***********************************************//
        System.out.println("Welcome!");
        Scanner scn = new Scanner(System.in);
        while (true){
        System.out.println("Enter Pin Code:");
        int pinNumber =  scn.nextInt(); // pincode start from 100
        //*********check pin code  ****************** */
        Client currentClient = checkPin(pinNumber);


        if (currentClient != null){
            //***********show all accounts of the client******/
            showAccounts(currentClient);
            break;

        }

        else{
            System.out.println("Your pin is wrong"); // show an error if the pin is wrong
        }

    }

    }
    

    public Client checkPin(int pinCode){
        for(Client client : clients  ){
            if ( client.getPinCode() == pinCode  ){
                return client;
            }
        }
        return null;
    }

    public void showAccounts(Client client) {
        int i = 1;
        for(BankAccount account : client.getClientAccounts())
            {
                System.out.println( i +" .   "+ account.toString());
                i ++;
            }
        
        while(true){
        System.out.println("Select your account(Enter number order):");
        Scanner sc2 = new Scanner(System.in);
        int accountType = sc2.nextInt();

        int index = accountType - 1 ; // select the correct bank account from the arrayList
        if (index < client.getClientAccounts().size()){
            showMainMenu(client.getClientAccounts().get(index)); 
            break;
        }
        else{
            System.out.println("Inavalid input.");
        }
        }

    }

    public void showMainMenu(BankAccount account) {
        while (true){
        System.out.println("Main Menu\n");
        System.out.println("1. View Balance\n");
        System.out.println("2. Withdraw money\n");
        System.out.println("3. Deposit money\n");
        System.out.println("4. Exit\n");
        
        System.out.println("Enter your operation: ");
        Scanner sc = new Scanner(System.in);
        int opt = sc.nextInt();

        if (opt == 1){
            viewBalance(account);
        }
        else if(opt == 2){
            System.out.println("Enter your amount: ");
            double  amountWithdraw = sc.nextInt();
            withdrawMoney(account , amountWithdraw);
        }
        else if(opt == 3){
            System.out.println("Enter your amount: ");
            double  amountDeposite = sc.nextInt();
            depositeMoney(account , amountDeposite);
            
        }
        else if(opt == 4){
            break;
        }
        else{
            System.out.println("Invalid number");
            break;
        }
    }
        
    }

    public void  depositeMoney(BankAccount account, double amountDeposite){
        String status = account.deposit(amountDeposite);
        Transaction transaction = new Deposit(account.getAccountNumber(), "2023/03/05", status, account, amountDeposite);
        transactions.add(transaction);
    }

    public void withdrawMoney(BankAccount account, double amountWithdraw){
        String status = account.withdrawal(amountWithdraw);
        Transaction transaction = new Withdrawal(account.getAccountNumber(), "2023/03/05", status, account, amountWithdraw);
        transactions.add(transaction);
    }

    public void viewBalance(BankAccount account){
        String status = account.balanceInquiries();
        Transaction transaction = new BalanceInquirie(account.getAccountNumber(), "2023/03/05", status, account);
        transactions.add(transaction);
    }



    


}

//********************Client**************************************** */
class Client {
    private static int countAccounts = 0; // get the count of clients
    private String clientId ;
    private String clientName;
    private String nationality;
    private String occupation;
    private String address;
    private int age;
    private String gender;
    private int pinCode ;  
    private ArrayList<BankAccount> clientAccounts;
    private ArrayList<Loan> loans;
     
    
    public Client(String clientId, String clientName, String nationality, String occupation, String address, int age,
            String gender) {
        setClientId(clientId);
        setClientName(clientName);
        setNationality(nationality);
        setOccupation(occupation);
        setAddress(address);
        setAge(age);
        setGender(gender);
        this.pinCode = 100 + countAccounts++ ;  // generate pinCode from 100 
        clientAccounts = new ArrayList<>(); // stored all accounts as a arraylist
        loans = new ArrayList<>(); // stored all loans as a array list
    }


    public void updateAccounts(BankAccount account){
        clientAccounts.add(account);
    }

    
    public ArrayList<BankAccount> getClientAccounts() {
        return clientAccounts;
    }

    public void requestLoan(double loanAmount , double loanInterest , double loanDuration , String loanPaymentMethod){
        Loan loan = new Loan(loanAmount , loanInterest , loanDuration , loanPaymentMethod);
        loans.add(loan);
    }

    public  int getPinCode() {
        return pinCode;
    }

    public  void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    

     

}

///***********************BankAccount************************************* */
class BankAccount{
    private int accountNumber ;
    private String currency;
    private String branch ;
    private double balance;
    private String accountType; // get account type

    public BankAccount(int accountNumber, String currency, String branch, double balance,String accountType) {
        setAccountNumber(accountNumber);
        setCurrency(currency);
        setBranch(branch);
        setBalance(balance);
        setAccountType(accountType);
        
    }
    
    public int getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String deposit(double amount){
        this.balance += amount;
        System.out.println(amount);
        System.out.println("Deposited.This transaction has been completed.");
        return "Sucess";
    };
    public String withdrawal(double amount){
        if (this.balance >= amount){
            this.balance -= amount;
            System.out.println(amount);
            System.out.println("Withdraw have been completed.");
            return "Sucess";
        }else {
            System.out.println("Insufficient balance");
            return "Failure";
        }
    };
    public String balanceInquiries(){
        System.out.println("Balance: " + this.balance);
        return "Success";
    }

    /// this is the toSting method 
    public String toString() {
        return "Bank Account Type = "+getAccountType()+"   &   Bank Account Number =  " + getAccountNumber();
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    };
    
}

//*****************SavingsAccount class*********************************** */
class SavingsAccount extends BankAccount{ // extends to BankAccount
    private double interestRate ;

    public SavingsAccount(int accountNumber, String currency, String branch, double balance,String accountType, double interestRate) {
        super(accountNumber, currency, branch, balance , accountType);
        setInterestRate(interestRate);
    }




    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    


}


//******************CurrentAccount Class******************************************** */
class CurrentAccount extends BankAccount{ // this class is child class of BankAccount

    public CurrentAccount(int accountNumber, String currency, String branch, double balance,String accountType) {
        super(accountNumber, currency, branch, balance,accountType);
    }
    

}


//************Loan Class********************************* */
class Loan{
    private double loanAmount ;
    private double loanInterest;
    private double loanDuration;
    private String loanPaymentMethod;

    public Loan(double loanAmount, double loanInterest, double loanDuration, String loanPaymentMethod) {
        this.loanAmount = loanAmount;
        this.loanInterest = loanInterest;
        this.loanDuration = loanDuration;
        this.loanPaymentMethod = loanPaymentMethod;
    }


    public double getLoanAmount() {
        return loanAmount;
    }
    public double getLoanInterest() {
        return loanInterest;
    }
    public double getLoanDuration() {
        return loanDuration;
    }
    public String getLoanPaymentMethod() {
        return loanPaymentMethod;
    }

}

//***********Transaction Class****************************************** */
class Transaction{

    private static int transactionId = 0 ; // get a static variable as id 
    private int accountId ; 
    private String date;
    private String status;


    public Transaction( int accountId, String date, String status) {
        this.transactionId++;
        this.accountId = accountId;
        this.date = date;
        this.status = status;
    }


    public static int getTransactionId() {
        return transactionId;
    }


    public int getAccountId() {
        return accountId;
    }


    public String getDate() {
        return date;
    }


    public String getStatus() {
        return status;
    }




}


//**********Deposit Class************************************************** */
class Deposit extends Transaction{
    private BankAccount account;
    private double amount;


    public Deposit(int accountId, String date, String status, BankAccount account, double amount) {
        super(accountId, date, status);
        this.account = account;
        this.amount = amount;
       
        
    }


    public BankAccount getAccount() {
        return account;
    }


    public double getAmount() {
        return amount;
    }

    
    
} 


//*************************Withdrawal Classs************************************** */
class Withdrawal extends Transaction{
    private BankAccount account;
    private double amount;
    public Withdrawal(int accountId, String date, String status, BankAccount account, double amount) {
        super(accountId, date, status);
        this.account = account;
        this.amount = amount;
       
        
    }
    public BankAccount getAccount() {
        return account;
    }
    public double getAmount() {
        return amount;
    }

    
} 


//******************BalanceInquire Class************************************** */
class BalanceInquirie extends Transaction{
    private BankAccount account;
    
    public BalanceInquirie(int accountId, String date, String status, BankAccount account) {
        super(accountId, date, status);
        this.account = account;
        
    }

    public BankAccount getAccount() {
        return account;
    }

    
} 
