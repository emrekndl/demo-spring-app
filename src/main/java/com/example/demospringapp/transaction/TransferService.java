package com.example.demospringapp.transaction;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demospringapp.ICommand;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransferService implements ICommand<TransferDTO, String> {

    private IBankAccountRepository bankAccountRepository;

    public TransferService(IBankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public ResponseEntity<String> execute(TransferDTO transfer) {
        Optional<BankAccount> fromAccount = bankAccountRepository.findById(transfer.getFromUser());
        Optional<BankAccount> toAccount = bankAccountRepository.findById(transfer.getToUser());
        
        if (fromAccount.isEmpty() || toAccount.isEmpty()) {
            // this can be a custom exception
            throw new RuntimeException("User not found");
        }
        
        BankAccount from = fromAccount.get();
        BankAccount to = toAccount.get();
        
        // add & deduct        
        add(to, transfer.getAmount()); 
        // have added new money but not checked if enough the transfer
        System.out.println("After adding, before deducting: ");
        System.out.println(bankAccountRepository.findById(to.getName())); // better with logging
        deduct(from, transfer.getAmount());
        
        // never called Repository.save()

        return ResponseEntity.ok("Success");

    }
    
    private void deduct(BankAccount bankAccount, double amount) {
        if (bankAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds, not enough money!");
        }
        bankAccount.setBalance(bankAccount.getBalance() - amount);
    }
    
    private void add(BankAccount bankAccount, double amount) {
        bankAccount.setBalance(bankAccount.getBalance() + amount);
    }

}
