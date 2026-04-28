package model;

import java.util.LinkedList;
import java.util.Queue;

public class ShoppingCart {
    // LinkedList implements the Queue interface in Java
    private Queue<Album> cartQueue;
    private double totalCost;

    public ShoppingCart() {
        this.cartQueue = new LinkedList<>();
        this.totalCost = 0.0;
    }

    // Add an item to the back of the line
    public void addItem(Album album) {
        cartQueue.add(album);
        // Assuming we add a getPrice() to Album later, we'd add it here.
        // totalCost += album.getPrice(); 
        System.out.println("Added to cart: " + album.getAlbumTitle());
    }

    // Peek at the next item without removing it
    public Album peekNextItem() {
        return cartQueue.peek();
    }

    // Process the checkout, removing items from the front of the line
    public void processCheckout() {
        System.out.println("--- Starting Checkout Process ---");
        
        if (cartQueue.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        // The poll() method retrieves and removes the head of the queue
        while (!cartQueue.isEmpty()) {
            Album currentItem = cartQueue.poll(); 
            System.out.println("Processing transaction for: " + currentItem.getAlbumTitle() + " (" + currentItem.getFormat() + ")");
            
            // Call the DAO to INSERT a TransactionItem and UPDATE the inventory quantity?
        }
        
        System.out.println("--- Checkout Complete ---");
        totalCost = 0.0; // Reset cart
    }

    public boolean isEmpty() {
        return cartQueue.isEmpty();
    }
    
    public int getCartSize() {
        return cartQueue.size();
    }
}