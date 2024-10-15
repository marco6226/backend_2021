/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.scm.mailSLFacade;

/**
 *
 * @author Luis
 */
import java.util.LinkedList;
import java.util.Queue;

public class EmailQueue {
    private Queue<EmailTask> queue = new LinkedList<>();

    public synchronized void enqueue(EmailTask task) {
        queue.offer(task);
        notify(); // Notifica a un hilo en espera
    }

    public synchronized EmailTask dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // Espera hasta que haya un elemento en la cola
        }
        return queue.poll();
    }
}
