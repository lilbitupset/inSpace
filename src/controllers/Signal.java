/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author Logan
 */
import java.util.HashSet;
import java.util.function.Consumer;

public class Signal<E> {

    class Connection{

        private Signal<E> signal;
        private Consumer<E> func;

        public Connection(Signal<E> s, Consumer<E> func){
            this.signal = s;
            this.func = func;
        }

        public void Disconnect(){
            if (this.signal != null){
                this.signal.connections.remove(this);
                this.signal = null;
                this.func = null;
            }
        }
    }

    private HashSet<Connection> connections;

    public Signal(){
        this.connections = new HashSet<>();
    }

    public void Fire(E x){
        for (Connection c: this.connections){
            c.func.accept(x);
        }
    }
    public void Fire(){
        for (Connection c: this.connections){
            c.func.accept(null);
        }
    }

    public Connection Connect(Consumer<E> func){
        Connection c = new Connection(this, func);
        this.connections.add(c);
        return c;
    }
}

/* EXAMPLES
    // In Keyboard for example
     Signal<int> s = new Signal<>();
     Keyboard.KeyPressed = s;
    //On keyPress
     s.Fire(KeyCode)

    //In some other code
     Connection c = Keyboard.KeyPressed.Connect(KeyCode -> {
        System.out.println("KeyCode: "+KeyCode);
     });
*/