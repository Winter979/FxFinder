/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxfinder;

/**
 *
 * @author zazu
 */
public class InvalidSearchException extends Exception {

    /**
     * Creates a new instance of <code>InvalidSearchException</code> without
     * detail message.
     */
    public InvalidSearchException() {
    }

    /**
     * Constructs an instance of <code>InvalidSearchException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidSearchException(String msg) {
        super(msg);
    }
}
