package view.console.commands;

import controller.Controller;
import model.exceptions.InterpreterException;

import java.io.IOException;

public class RunExample extends Command {
    private Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {

            controller.allStep();
        } catch (InterpreterException | IOException | InterruptedException e) {
            System.out.println(e);;
        }
    }
}
