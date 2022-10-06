package com.company;
import java.util.Scanner;
class Account{
    int balance = 0;
    int output = 10000;
    int withdraw;
    public synchronized void TopUp(){
        while(balance >= output){
            try{
                wait();
            }
            catch (Exception ex){
                ex.getMessage();
            }
        }
        balance += 1000;
        System.out.println("Пополнение баланса на 1000р");
        System.out.println("Остаток баланcа: " + balance);
        notify();
    }
    public synchronized void WithD(){
        while(balance < output){
            try {
                wait();
            }
            catch (Exception ex){
                ex.getMessage();
            }
        }
        System.out.println("Введите сумму вывода: ");
        Scanner in = new Scanner(System.in);
        withdraw = in.nextInt();
        balance -= withdraw;
        if(balance < 0){
            System.out.println("На балансе недостаточно средств, необходимо пополнить счет!");
            System.exit(1);
        }
        System.out.println("Списание средств " + withdraw);
        System.out.println("Остаток баланса: " + balance);
        notify();
    }
}

class WithD implements Runnable{
    Account account;
    WithD(Account account){
        this.account=account;
    }
    public void run(){
        while (true) {
            account.TopUp();
        }
    }
}
class TopUp implements Runnable{
    Account account;
    TopUp(Account account){
        this.account=account;
    }
    public void run(){
        while (true) {
            account.WithD();
        }
    }
}
public class Main {

    public static void main(String[] args) {
        Account account = new Account();
        TopUp refill = new TopUp(account);
        WithD output = new WithD(account);
        new Thread(refill).start();
        new Thread(output).start();
    }
}
